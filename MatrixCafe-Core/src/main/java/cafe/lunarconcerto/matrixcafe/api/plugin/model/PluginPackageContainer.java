package cafe.lunarconcerto.matrixcafe.api.plugin.model;

import cafe.lunarconcerto.matrixcafe.api.plugin.PluginClassLoader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.jar.JarFile;

/**
 * Plugin jar 包容器, 存储包内容信息
 */
@Getter
@ToString
@RequiredArgsConstructor
public final class PluginPackageContainer {

    private final PluginClassLoader pluginClassLoader ;

    private final JarFile jarFile ;

    private final PackageContents contents ;

    private PluginClassContainer[] containers = { } ;

    public PluginClassContainer[] createContainers() throws ClassNotFoundException {
        List<PluginInfo> infoList = contents.pluginInfos();
        containers = new PluginClassContainer[infoList.size()];

        for (int i = 0, infoListSize = infoList.size(); i < infoListSize; i++) {
            PluginInfo pluginInfo = infoList.get(i);
            containers[i] = new PluginClassContainer(
                    pluginInfo,
                    pluginClassLoader,
                    pluginClassLoader.loadClass(pluginInfo.classname())
            );
        }
        return containers ;
    }




}
