package cafe.lunarconcerto.matrixcafe.api.plugin;

import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginClassContainer;
import cafe.lunarconcerto.matrixcafe.api.plugin.model.PluginState;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
public class PluginDependencyResolver {

    public static final String SPLIT_SYMBOL = ":" ;

    protected Map<String, PluginClassContainer> pluginMap ;

    protected SimpleDirectedGraph<String, Dependencies> dependenciesGraph ;

    public PluginDependencyResolver(Map<String, PluginClassContainer> pluginMap) {
        this.pluginMap = pluginMap;
    }

    public List<String> resolve(){
        SimpleDirectedGraph<String, Dependencies> graph = createGraph();
        /* 输入所有顶点 */
        pluginMap.keySet().forEach(graph::addVertex);

        /* 输入边 */
        for (Map.Entry<String, PluginClassContainer> entry : pluginMap.entrySet()) {
            PluginClassContainer value = entry.getValue();

            if (value.getPluginInfo() == null) {
                continue;
            }

            for (String dependency : value.getPluginInfo().dependencies()) {
                Pair<String, String> pair = spilt(dependency);

                try {
                    graph.addEdge(value.getId(), pair.getLeft(), Dependencies.of(value.getId(), pair));
                }catch (IllegalArgumentException e){
                    log.error("没有找到名为 {} 的插件的依赖 {}, 这将导致当前插件可能无法正常运行, 请检查你的插件文件夹.",
                            value.getId(), pair.getLeft());
                    value.changeState(PluginState.ERROR);
                }
            }
        }

        dependenciesGraph = graph ;
        resolveVersion();

        return sort() ;
    }

    protected void resolveVersion(){
        /* 遍历所有边以检查依赖关系 */
        for (Dependencies dependencies : dependenciesGraph.edgeSet()) {
            String requiresVersion = dependencies.requiresVersion;
            if (requiresVersion.isEmpty()) {
                continue;
            }

            PluginClassContainer container = pluginMap.get(dependencies.requiresId);

            if (container.satisfiesTo(requiresVersion)) {
                continue;
            }

            log.error("插件 {} 需求 {} 的版本为 {} , 但现有版本为 {} .",
                    dependencies.sourceId,
                    dependencies.requiresId,
                    requiresVersion,
                    container.getVersion()
            );
            pluginMap.get(dependencies.sourceId).changeState(PluginState.ERROR);
        }
    }

    protected List<String> sort(){
        List<String> result = new ArrayList<>(dependenciesGraph.vertexSet().size());
        for (String pluginID : dependenciesGraph.vertexSet()) {
            if (dependenciesGraph.edgesOf(pluginID).size() == 0) {
                result.add(0, pluginID);
            } else {
                result.add(pluginID);
            }
        }

        for (String pluginID : result) {
            for (String dependencyID : pluginMap.get(pluginID).getDependencies()) {

                int indexOfDependency = result.indexOf(dependencyID);
                int indexOfPlugin = result.indexOf(pluginID);

                if (indexOfDependency > indexOfPlugin) {
                    Collections.swap(result, indexOfDependency, indexOfPlugin);
                }
            }
        }
        return result ;
    }


    protected SimpleDirectedGraph<String, Dependencies> createGraph(){
        return new SimpleDirectedGraph<>(Dependencies.class);
    }

    /**
     * 拆分依赖声明中的id与版本需求
     * @param dependency 依赖声明字符串
     * @return 一个只读对, 左边为id, 右边为版本需求
     */
    @Contract("_ -> new")
    public static @NotNull Pair<String, String> spilt(@NotNull String dependency){
        return dependency.contains(SPLIT_SYMBOL) ?
                Strings.splitOn(dependency, dependency.lastIndexOf(SPLIT_SYMBOL)) :
                Pair.of(dependency, Strings.EMPTY);
    }


    /**
     * 依赖图的边, 代表依赖关系
     */
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class Dependencies {

        protected String sourceId, requiresId, requiresVersion;

        @Contract("_, _ -> new")
        public static @NotNull Dependencies of(String sourceId, @NotNull Pair<String, String> pair){
            return new Dependencies(sourceId, pair.getLeft(), pair.getRight()) ;
        }

        @Contract(pure = true)
        public static @NotNull Dependencies of(String sourceId, String requiresId, String requiresVersion){
            return new Dependencies(sourceId, requiresId, requiresVersion) ;
        }

    }

}
