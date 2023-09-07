package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.common.DirectoryConstants;
import cafe.lunarconcerto.matrixcafe.api.common.ApplicationContext;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionRegistry;
import cafe.lunarconcerto.matrixcafe.api.plugin.*;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginClassContainer;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginPackageContainer;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginState;
import cafe.lunarconcerto.matrixcafe.api.plugin.mvn.ArtifactDependencyResolver;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <h1>Matrix Cafe 插件管理器</h1>
 */
@Slf4j
@Singleton
public class MatrixCafePluginManager implements PluginManager {

    protected final ApplicationContext applicationContext;

    protected final ExtensionRegistry extensionRegistry;

    protected PluginLoader loader ;

    protected PluginDependencyResolver resolver ;

    protected ArtifactDependencyResolver artifactResolver ;

    protected Map<String, PluginClassContainer> existPluginMap;

    protected List<String> sortedPluginList ;

    /**
     * 为所有插件对象注入依赖
     */
    protected Injector injector ;

    @Inject
    public MatrixCafePluginManager(ApplicationContext applicationContext,
                                   ExtensionRegistry extensionRegistry,
                                   Injector injector)
    {
        this.applicationContext = applicationContext;
        this.extensionRegistry = extensionRegistry;
        this.injector = injector;
    }

    @Override
    public void initialize() {
        /* 扫描插件文件夹, 加载插件信息 */
        loader = new PluginLoader(new PluginFileFinder(DirectoryConstants.PLUGIN_DIR.get()));
        loader.load();
        /* 收集插件信息整理为键值对, 左值是插件ID */
        existPluginMap = loader.collect();
        /* 扫描插件的依赖关系 */
        resolver = new PluginDependencyResolver(existPluginMap);
        sortedPluginList = resolver.resolve();
        /* 扫描并下载各插件的库依赖 */
        artifactResolver = new ArtifactDependencyResolver(existPluginMap);
        try {
            artifactResolver.resolve();
            /* 库依赖加载到classpath中以便调用 */
            artifactResolver.load();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @Override
    @SuppressWarnings("SimplifyStreamApiCallChains")
    public void instantiate() {
        sortedPluginList.stream().forEachOrdered(pluginID -> {
            PluginClassContainer container = existPluginMap.get(pluginID);
            container.instantiate(applicationContext);

            // 注入依赖
            container.injectPlugin(injector);
        });
    }

    @Override
    public void start() {
        existPluginMap.values().forEach(container -> {
            try {
                container.startPlugin();
            } catch (Exception e) {
                log.error("插件启动时发生异常:" , e);
            }
        });
    }

    @Override
    public boolean start(String pluginID) throws Exception {
        PluginClassContainer container = existPluginMap.get(pluginID);
        if (container != null){
            return container.startPlugin();
        }else {
            log.warn("启动名为 {} 的插件失败, 该插件不存在.", pluginID);
            return false ;
        }
    }

    @Override
    public Optional<Plugin> findPlugin(String pluginID) {
        if (existPluginMap == null || existPluginMap.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(existPluginMap
                .get(pluginID)
                .getStandardPluginInstance());
    }

    @Override
    public void close() throws Exception {
        for (PluginClassContainer pluginClassContainer : existPluginMap.values()) {
            pluginClassContainer.closePlugin();
        }
    }

    @Override
    public boolean close(String pluginID) throws Exception {
        PluginClassContainer container = existPluginMap.get(pluginID);
        if (container != null){
            return container.closePlugin();
        }else {
            log.warn("关闭名为 {} 的插件失败, 该插件不存在.", pluginID);
            return false ;
        }
    }

    @Override
    public int loadedPluginCount() {
        return existPluginMap.size();
    }

    @Override
    public int enabledPluginCount() {
        int count = 0;
        for (PluginClassContainer value : existPluginMap.values()) {
            if (value.getState() == PluginState.RUNNING) {
                count++;
            }
        }
        return count ;
    }

    public List<PluginPackageContainer> getPluginPackageContainers() {
        return loader.getPluginPackageContainers();
    }
}
