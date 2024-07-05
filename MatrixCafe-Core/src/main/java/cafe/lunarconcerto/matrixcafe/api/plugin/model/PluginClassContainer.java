package cafe.lunarconcerto.matrixcafe.api.plugin.model;

import cafe.lunarconcerto.matrixcafe.api.application.ApplicationContext;
import cafe.lunarconcerto.matrixcafe.api.plugin.Plugin;
import cafe.lunarconcerto.matrixcafe.api.plugin.PluginClassLoader;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import com.google.inject.Injector;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Constructor;

/**
 * Plugin 主类容器, 存储其类信息、类实例等.
 */
@Getter
@Slf4j
public final class PluginClassContainer {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * 当该插件仅是一个class时, 该字段为 {@link PackageContents#EMPTY}
     */
    private final PackageContents parentPackage ;

    /**
     * 当该插件为纯class, 且不包含注解时, 该字段为 null.
     */
    private final @Nullable PluginInfo pluginInfo ;

    private final PluginClassLoader classLoader ;

    private final Class<?> pluginMainClass ;

    /**
     * 该容器尚未调用 instantiate 方法时, 该字段为 null
     */
    private @Nullable Object pluginInstance ;

    private PluginState state = PluginState.LOADED ;

    /**
     * 该插件是否是一个标准的 MatrixCafe 插件.
     * <p>
     * 判定标准为是否继承了 {@link Plugin} 基类.
     */
    private boolean standardPlugin = false ;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public PluginClassContainer(PluginInfo pluginInfo, PluginClassLoader classLoader) throws ClassNotFoundException {
        this(pluginInfo, classLoader, classLoader.loadClass(pluginInfo.classname()));
    }

    public PluginClassContainer(PluginInfo pluginInfo, PluginClassLoader classLoader, Class<?> pluginMainClass) {
        this(PackageContents.EMPTY, pluginInfo, classLoader, pluginMainClass);
    }

    public PluginClassContainer(PackageContents parentPackage , @Nullable PluginInfo pluginInfo,
                                PluginClassLoader classLoader, Class<?> pluginMainClass) {
        this.parentPackage = parentPackage;
        this.pluginInfo = pluginInfo;
        this.classLoader = classLoader;
        this.pluginMainClass = pluginMainClass;
    }


    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    /**
     * 实例化该容器所承载的插件主类.
     * @param applicationContext 当前应用程序上下文
     */
    public void instantiate(ApplicationContext applicationContext) {
        if (state == PluginState.ERROR || state == PluginState.DISABLE) {
            return;
        }

        try {
            Constructor<?> constructor = pluginMainClass.getConstructor(ApplicationContext.class);
            pluginInstance = constructor.newInstance(applicationContext);

            if (pluginInstance instanceof Plugin) {
                standardPlugin = true ;
            }
            return;
        } catch (ReflectiveOperationException e) {
            log.warn("类 {} 不存在可用的接收 ApplicationContext 的构造函数, MatrixCafe将尝试使用默认构造函数进行实例化. 虽然可以继续运行, 但是不推荐在生产环境这样做, 因为你将可能无法访问MatrixCafe的程序上下文.", pluginMainClass.getName());
        }

        try {
            Constructor<?> constructor = pluginMainClass.getConstructor();
            pluginInstance = constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            log.error("类 {} 不存在可用的空构造函数, 插件主类无法被实例化.", pluginMainClass.getName());
            changeState(PluginState.ERROR);
            return;
        }

        changeState(PluginState.READY);
    }

    public boolean enablePlugin() throws Exception {
        if (isInstantiated() && isStandardPlugin()) {
            getStandardPluginInstance().onEnable();
            return true;
        }else{
            return false;
        }
    }

    public boolean disablePlugin() throws Exception {
        if (isInstantiated() && isStandardPlugin()) {
            getStandardPluginInstance().onDisable();
            return true;
        }else{
            return false;
        }
    }

    public boolean startPlugin() throws Exception {
        if (isInstantiated() && isStandardPlugin()){
            ((Plugin)pluginInstance).onStart();
            changeState(PluginState.RUNNING);
            return true ;
        }else {
            return false ;
        }
    }

    public boolean closePlugin() throws Exception {
        if (isInstantiated() && isStandardPlugin()){
            ((Plugin)pluginInstance).onClose();
            changeState(PluginState.CLOSED);
            return true ;
        }else {
            return false ;
        }
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean injectPlugin(Injector injector){
        if (isInstantiated()){
            injector.injectMembers(pluginInstance);
            return true;
        }else {
            return false;
        }
    }


    public void errorPlugin(){
        if (!isInstanceAccessible()){
            return;
        }

        assert pluginInstance != null ;

        try {
            getStandardPluginInstance().onError(new PluginError());
        } catch (Exception e) {
            log.error("插件 {} 在处理异常时出现的异常: {}", getId(), e);
        }

        changeState(PluginState.ERROR);
    }

    public void addDependencyFile(File file){
        classLoader.addJarFile(file);
    }

    public Plugin getStandardPluginInstance() throws ClassCastException {
        return (Plugin) pluginInstance;
    }

    public boolean hasPluginInfo(){
        return pluginInfo != null ;
    }

    /**
     * 检查该插件版本是否满足于需求的插件版本.
     * @param requires 需求的插件版本, 一般情况下, 该参数由其他插件所给出.
     * @return 当插件不是一个标准的插件(继承了 Plugin 类)时, 返回 false, 然后检查版本号, 若版本号大于等于需求的版本号, 返回 true, 否则, 返回 false.
     */
    public boolean satisfiesTo(String requires) {
        return hasPluginInfo() && pluginInfo.satisfiesTo(requires);
    }

    public String @NotNull [] getDependencies() {
        return hasPluginInfo() ? pluginInfo.dependencies() : Strings.EMPTY_ARRAY ;
    }

    /**
     * 返回该插件实例是否可访问.
     * <p>
     * 可访问的实例有以下条件:
     * <ol>
     *     <li>
     *         是标准插件, 即继承了 {@link Plugin} 类
     *     </li>
     *     <li>
     *         主类已实例化
     *     </li>
     *     <li>
     *         存在{@link PluginInfo}, 即主类被 {@link cafe.lunarconcerto.matrixcafe.api.plugin.annotations.PluginInformation} 标注
     *     </li>
     * </ol>
     * @return 满足上述条件时, 返回 true
     */
    public boolean isInstanceAccessible(){
        return isStandardPlugin() && isInstantiated() && hasPluginInfo();
    }

    /**
     * 获取插件依赖的位于maven仓库的类库
     * @return 返回该插件依赖类库, 若无 {@link PluginInfo} 返回空数组
     */
    public String[] getLibraries() {
        return hasPluginInfo() ? pluginInfo.libraries() : Strings.EMPTY_ARRAY ;
    }

    /**
     * 获取该插件版本
     * @return 返回该插件在 {@link cafe.lunarconcerto.matrixcafe.api.plugin.annotations.PluginInformation} 中设定的版本, 若无 {@link PluginInfo} 则返回 undefined
     */
    @NotNull
    public String getVersion() {
        return hasPluginInfo() ? pluginInfo.version() : Strings.UNDEFINED ;
    }

    /**
     * 获取该插件id
     * @return 返回该插件在 {@link cafe.lunarconcerto.matrixcafe.api.plugin.annotations.PluginInformation} 中设定的id, 若无 {@link PluginInfo} 则返回主类的全类名
     */
    public String getId(){
        return hasPluginInfo() ? pluginInfo.id() : pluginMainClass.getName();
    }

    /**
     * 修改该插件的状态
     * @param state 目标状态
     */
    public void changeState(PluginState state) {
        this.state = state;
    }

    /**
     * @return 返回该插件是否是一个标准的插件, 标准的插件指的是继承了 {@link Plugin} 的插件类.
     */
    public boolean isStandardPlugin() {
        return standardPlugin;
    }

    /**
     * @return 返回该插件的插件类是否已经被实例化.
     */
    public boolean isInstantiated(){
        return pluginInstance != null ;
    }


}
