package cafe.lunarconcerto.matrixcafe.api.plugin.model;

import java.util.Collections;
import java.util.List;

/**
 * 代表一个 jar 包的内容
 */
public record PackageContents(

        List<PluginInfo> pluginInfos

){

    public static PackageContents EMPTY = new PackageContents(Collections.emptyList());

}
