package cafe.lunarconcerto.matrixcafe.api.plugin.model;

import cafe.lunarconcerto.matrixcafe.api.plugin.annotations.PluginInformation;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.semver4j.Semver;

/**
 * 代表一个 Plugin 主类, 包含该主类相关信息.
 * <p>
 * 一般情况下从插件 jar 包中的 "META-INF/plugin-info.json" 中获得,
 * 当插件由单个 class 文件提供时, 反射其 {@link PluginInformation} 注解获得.
 *
 * @see PluginInformation
 */
public record PluginInfo(

        @NotNull String classname,

        @NotNull String id,

        @NotNull String version,

        String[] dependencies,

        String[] libraries,

        @NotNull String author) {

    public boolean satisfiesTo(String requires) {
        return new Semver(version).satisfies(requires);
    }

    @Contract("_, _ -> new")
    public static @NotNull PluginInfo fromAnnotation(@NotNull PluginInformation info, String classname) {
        return PluginInfoBuilder.builder()
                .classname(classname)
                .id(info.id())
                .version(info.version())
                .dependencies(info.dependencies())
                .libraries(info.libraries())
                .author(info.author())
                .build();
    }

    @Override
    public String classname() {
        return classname;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String version() {
        return version;
    }

    @Override
    public String[] dependencies() {
        return dependencies;
    }

    @Override
    public String[] libraries() {
        return libraries;
    }

    @Override
    public String author() {
        return author;
    }

    public static final class PluginInfoBuilder {

        private String classname;

        private String id;

        private String version;

        private String[] dependencies;

        private String[] libraries;

        private String author;

        private PluginInfoBuilder() {
        }

        @Contract(value = " -> new", pure = true)
        public static @NotNull PluginInfoBuilder builder() {
            return new PluginInfoBuilder();
        }

        public PluginInfoBuilder classname(String classname) {
            this.classname = classname;
            return this;
        }

        public PluginInfoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public PluginInfoBuilder version(String version) {
            this.version = version;
            return this;
        }

        public PluginInfoBuilder dependencies(String[] dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public PluginInfoBuilder libraries(String[] libraries) {
            this.libraries = libraries;
            return this;
        }

        public PluginInfoBuilder author(String author) {
            this.author = author;
            return this;
        }

        @Contract(" -> new")
        public @NotNull PluginInfo build() {
            return new PluginInfo(classname, id, version, dependencies, libraries, author);
        }
    }
}
