package cafe.lunarconcerto.matrixcafe.api.plugin;

import java.io.IOException;
import java.util.Optional;

public interface PluginManager {

    /**
     * 初始化插件系统, 收集插件信息.
     */
    void initialize() throws IOException;

    /**
     * <h1>实例化所有插件</h1>
     * 实例化一般分为以下步骤:
     * <ol>
     *     <li>实例化插件主类</li>
     * </ol>
     */
    void instantiate() throws Exception ;

    /**
     * 启动所有准备就绪的插件, 即调用其 {@link Plugin#onStart()} 方法.
     */
    void start() throws Exception;

    /**
     * 启动特定插件, 即调用目标插件的 {@link Plugin#onStart()} 方法.
     * @param pluginID 目标插件的ID
     * @return 启动是否成功
     */
    boolean start(String pluginID) throws Exception;

    /**
     * 查找一个加载的插件对象.
     * @return 插件对象.
     */
    Optional<Plugin> findPlugin(String pluginID);

    /**
     * 关闭插件系统, 并调用所有插件的 {@link Plugin#onClose()} 方法.
     */
    void close() throws Exception;

    /**
     * 关闭特定插件
     * @param pluginID 插件的id
     * @return 关闭是否成功
     */
    boolean close(String pluginID) throws Exception;

    /**
     * 返回已加载的插件数量
     * @return 已加载插件数量
     */
    int loadedPluginCount();

    /**
     * 返回已激活的插件数量
     * @return 已激活插件数量
     */
    int enabledPluginCount();

    void disableAll();
}
