package cafe.lunarconcerto.matrixcafe.api.plugin;

import cafe.lunarconcerto.matrixcafe.api.plugin.annotations.PluginInformation;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PackageContents;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginClassContainer;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginInfo;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginPackageContainer;
import cafe.lunarconcerto.matrixcafe.api.plugin.processor.PluginProcessor;
import cafe.lunarconcerto.matrixcafe.api.util.Json;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Getter
@Slf4j
public class PluginLoader {

    protected PluginFileFinder finder ;

    /**
     * 存储已经加载的 jar 包容器.
     * <p>
     * 在调用 {@link PluginLoader#loadPackagePlugin(File)} 时会将新加载的 jar 包加入到该集合内.
     */
    protected List<PluginPackageContainer> pluginPackageContainers ;

    /**
     * 存储已经加载的纯 class 插件.
     * <p>
     * 在调用 {@link PluginLoader#loadClassPlugin(File)} 时会将新加载的 class 插件加入到该集合内.
     */
    protected List<PluginClassContainer> classPlugins ;

    /**
     * 所有已加载的插件主类容器的哈希映射.
     * <p>
     * 左值为插件ID, 右值为该ID对应的插件主类容器.
     */
    protected Map<String, PluginClassContainer> existPluginMap;

    /**
     * 所有已加载的插件主类容器的集合.
     */
    protected List<PluginClassContainer> existPluginList;

    public PluginLoader(@NotNull PluginFileFinder finder) {
        this.finder = finder;
        finder.find();

        pluginPackageContainers = new ArrayList<>();
        classPlugins = new LinkedList<>();
    }

    public void load(){
        try {
            if (finder.hasJarFiles()){
                log.info("读取 jar 插件包, 共找到 {} 个", finder.jarFiles.size());
                this.loadAllPackagePlugin(finder.getJarFiles());
            }

            if (finder.hasClassFiles()){
                log.info("读取 class 插件, 共找到 {} 个", finder.classFiles.size());
                this.loadAllClassPlugin(finder.getClassFiles());
            }
        } catch (IOException e) {
            log.error("读取插件时发生异常!", e);
        } catch (ClassNotFoundException e) {
            log.error("读取 jar 包中的插件主类时遭遇异常, 可能是输入的主类名有误.", e);
        }
    }

    public List<PluginClassContainer> loadAllClassPlugin(@NotNull List<File> fileList) throws IOException {
        for (File file : fileList) {
            loadClassPlugin(file);
        }

        return classPlugins ;
    }

    public PluginClassContainer loadClassPlugin(File file) throws IOException {
        PluginClassLoader loader = PluginClassLoader.classLoader(getClass().getClassLoader(), file);
        Class<?> aClass = loader.loadClassFromFile(file);
        PluginInformation pluginInformation = aClass.getAnnotation(PluginInformation.class);
        if (pluginInformation == null){
            log.error("路径中的纯class插件 {} 不存在 PluginInformation 注解, 无法获取插件信息.", aClass.getName());
            PluginClassContainer container = new PluginClassContainer(null, loader, aClass);
            classPlugins.add(container);
            return container;
        }else {
            PluginInfo pluginInfo = PluginInfo.fromAnnotation(pluginInformation, aClass.getName());
            PluginClassContainer container = new PluginClassContainer(pluginInfo, loader, aClass);
            classPlugins.add(container);
            return container;
        }
    }

    /**
     * 读取所有 jar 包类插件,
     * <p>
     * 加载完成后, jar包插件会被封装为 {@link PluginPackageContainer}
     * @param fileList 需要载入的所有 jar 包的文件对象
     * @return 每个 jar 包对应的 {@link PluginPackageContainer}
     * @throws IOException 读取文件发生错误时抛出
     * @throws ClassNotFoundException 找不到 jar 包中的插件主类时抛出.
     * @see PluginPackageContainer
     */
    public List<PluginPackageContainer> loadAllPackagePlugin(@NotNull List<File> fileList) throws IOException, ClassNotFoundException {
        for (File file :fileList) {
            PluginPackageContainer container = loadPackagePlugin(file);
            container.createContainers();
        }
        return pluginPackageContainers ;
    }

    public Map<String, PluginClassContainer> collect(){
        if (pluginPackageContainers.isEmpty() && classPlugins.isEmpty()){
            log.warn("无已加载插件信息. 注意: 调用 PluginLoader#collect() 方法前最好先调用 PluginLoader#load().");
            return Collections.emptyMap();
        }

        existPluginMap = new ConcurrentHashMap<>();
        existPluginList = new ArrayList<>(classPlugins);
        classPlugins.forEach(plugin -> existPluginMap.put(plugin.getId(), plugin));

        pluginPackageContainers
                .forEach(container -> Arrays.stream(container.getContainers())
                .forEachOrdered(classContainer -> {
                    assert classContainer.getPluginInfo() != null;
                    existPluginMap.put(classContainer.getId(), classContainer);
                    existPluginList.add(classContainer);
        }));

        return existPluginMap ;
    }

    /**
     * 读取一个 jar 包文件的信息.
     * @param file .jar 类型的文件
     * @return 该 jar 包中信息的封装类
     * @throws IOException e
     */
    public PluginPackageContainer loadPackagePlugin(File file) throws IOException {
        try (JarFile jarFile = new JarFile(file)){
            JarEntry entry = jarFile.getJarEntry(PluginProcessor.PLUGIN_RESOURCE);
            InputStream stream = jarFile.getInputStream(entry);

            PluginPackageContainer container = new PluginPackageContainer(
                    PluginClassLoader.jarLoader(getClass().getClassLoader(), file),
                    jarFile,
                    Json.read(stream, PackageContents.class)
            );

            pluginPackageContainers.add(container);
            return container ;
        }
    }

}
