package cafe.lunarconcerto.matrixcafe.api.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Properties;

/**
 * <h1>配置管理器</h1>
 * 配置管理器用于管理配置的保存, 读取等.
 */
public interface ConfigurationManager {

    void setDataFormatter(@NotNull ConfigurationDataFormatter configurationDataFormatter);

    void load() throws IOException;

    void save() throws IOException;

    <T> @Nullable T getConfig(@NotNull Class<T> type);

    <T> void addConfigType(@NotNull Class<T> type);

    <T> void createConfigFile(T config);

    @NotNull Properties getProperties();

}
