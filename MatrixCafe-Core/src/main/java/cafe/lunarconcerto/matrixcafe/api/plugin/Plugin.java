package cafe.lunarconcerto.matrixcafe.api.plugin;

import cafe.lunarconcerto.matrixcafe.api.common.ApplicationContext;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionRegistry;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginError;

import java.util.Collections;
import java.util.Set;

/**
 * <h1>插件主类</h1>
 * 该类一个规范化的插件主类
 * <p>
 * 所有的插件主类都应该继承这个类, 以便接入 {@link PluginManager} 的生命周期机制以及访问 MatrixCafe 的程序上下文.
 * <p>
 * <h2>插件生命周期</h2>
 * 以下内容揭示了一个插件中的方法被调用的顺序:
 * <ol>
 *     <li>
 *         构造函数
 *         <p>
 *             显然, 插件实例化时, 构造函数必然被调用.
 *         </p>
 *     </li>
 *     <li>
 *         {@link Plugin#getRequireConfigurationType()}
 *         <p>
 *             实例化后, 优先收集插件的配置类信息, 以便之后为该插件主类注入所需的配置类.
 *         </p>
 *     </li>
 *     <li>
 *         {@link Plugin#registerExtensions(ExtensionRegistry)}
 *     </li>
 *     <li>
 *         {@link Plugin#onStart()}
 *         <p>
 *             onStart方法主要是 MatrixCafe 程序的生命周期的通知方法,
 *             在调用该方法后, 标志着插件已经启动完成,
 *             此后, 该插件的状态将被标记为{@link cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginState#RUNNING}
 *         </p>
 *     </li>
 *     <li>
 *         {@link Plugin#onEnable()}
 *         <p>
 *
 *         </p>
 *     </li>
 * </ol>
 */
@SuppressWarnings("RedundantThrows")
public abstract class Plugin {

    protected ApplicationContext context ;

    public Plugin(ApplicationContext context) {
        this.context = context;
    }

    public Set<Class<?>> getRequireConfigurationType(){
        return Collections.emptySet();
    }

    public void registerExtensions(ExtensionRegistry registry){ };

    /**
     * 当程序处于启动阶段时的回调
     * <p>
     * 插件初始化的最终阶段, 在插件的一生中仅会调用一次.
     * @throws Exception 任意可能抛出的异常.
     */
    public void onStart() throws Exception { }

    /**
     * 当程序处于关闭阶段时的回调
     * <p>
     *
     * @throws Exception 任意可能抛出的异常.
     */
    public void onClose() throws Exception { }

    /**
     * 当插件的运行存在异常情况时的回调,
     * 使得该插件可以在处于异常情况时进行自我处理
     * @param error 插件的错误信息
     * @throws Exception 任意可能抛出的异常.
     */
    public void onError(PluginError error) throws Exception { }

    /**
     * 启用插件
     * <p>
     * 当插件第一次启动, 或者插件从{@link cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginState#DISABLE} 转变为
     * {@link cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginState#RUNNING} 时被调用.
     * 如有必要, 该方法有可能在程序运行中反复调用.
     * @throws Exception 任意可能抛出的异常.
     */
    public void onEnable() throws Exception { };

    /**
     * 禁用插件
     * @throws Exception 任意可能抛出的异常.
     */
    public void onDisable() throws Exception { };

}
