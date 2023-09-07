package cafe.lunarconcerto.matrixcafe.impl;


import cafe.lunarconcerto.matrixcafe.api.common.DirectoryConstants;
import cafe.lunarconcerto.matrixcafe.api.config.*;
import cafe.lunarconcerto.matrixcafe.api.config.model.MatrixCafeConfiguration;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import com.google.inject.Singleton;
import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.nutz.lang.Files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Singleton
public class MatrixCafeConfigurationManager implements ConfigurationManager {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected static final String PROPERTIES_PATH = DirectoryConstants.CONFIG_DIR.get() + "matrix-cafe.properties";

    protected Properties properties;

    protected ConfigurationDataFormatter formatter = new MatrixCafeYAMLConfigurationDataFormatter();

    protected Map<String, ConfigurationContainer> configurationMap = new HashMap<>();

    protected List<Class<?>> typeList;

    protected MatrixCafeConfiguration matrixCafeConfiguration;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public MatrixCafeConfigurationManager() {
        properties = new Properties();

        typeList = new ArrayList<>();
        typeList.add(MatrixCafeConfiguration.class);
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Override
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Override
    public void setDataFormatter(@NotNull ConfigurationDataFormatter configurationDataFormatter) {
        this.formatter = configurationDataFormatter;
    }

    @Override
    public void load() throws IOException {
        /* 读取Properties */
        File propertiesFile = new File(PROPERTIES_PATH);
        if (propertiesFile.exists()) {
            properties.load(new FileInputStream(PROPERTIES_PATH));
        }

        /* 读取bean类配置 */
        typeList.forEach(this::loadConfig);
    }

    @Override
    public void save() throws IOException {
        properties.store(new FileOutputStream(PROPERTIES_PATH), "Custom properties create by MatrixCafe.");

        for (Map.Entry<String, ConfigurationContainer> entry : configurationMap.entrySet()) {
            if (!entry.getValue().isShouldSave()){
                continue;
            }

            String serialize = formatter.serialize(entry.getValue().getConfigurationInstance());
            File file = DirectoryConstants.CONFIG_DIR.of(entry.getKey());
            Files.write(file, serialize);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getConfig(@NotNull Class<T> type) {
        ConfigurationContainer result = configurationMap.get(getConfigName(type));
        if (result != null) {
            return (T) result.getConfigurationInstance();
        } else {
            return loadConfig(type);
        }
    }

    @Override
    public <T> void addConfigType(@NotNull Class<T> type) {
        typeList.add(type);
    }

    @Override
    public <T> void createConfigFile(T config) {
        String serialize = formatter.serialize(config);
        File file = DirectoryConstants.CONFIG_DIR.of(getConfigName(config.getClass()));
        Files.write(file, serialize);
    }


    @Override
    public @NotNull Properties getProperties() {
        if (properties == null) {
            throw new ConfigurationException("Properties not load.");
        }

        return properties;
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    protected static @Nullable String getConfigName(@NotNull Class<?> type) {
        Configuration annotation = type.getAnnotation(Configuration.class);
        return annotation == null ? null : annotation.value();
    }

    protected <T> @Nullable T loadConfig(@NotNull Class<T> type) {
        String name = getConfigName(type);
        if (Strings.isInvalid(name)) {
            throw new ConfigurationException(ConfigurationException.ExceptionType.NO_NAME_STATEMENT_CONFIG_TYPE, type.getName());
        }

        File file = DirectoryConstants.CONFIG_DIR.of(name);
        if (file.exists()) {
            T t = formatter.deserialize(file, type);
            if (t == null) return null ;
            configurationMap.put(name, ConfigurationContainer.of(t, name));
            return t;
        } else {
            return null;
        }
    }

    public MatrixCafeConfiguration getMatrixCafeConfiguration() {
        if (matrixCafeConfiguration == null) {
            throw new ConfigurationException("Matrix-Cafe 主配置未读取.");
        }

        return matrixCafeConfiguration;
    }

    public void setMatrixCafeConfiguration(MatrixCafeConfiguration matrixCafeConfiguration) {
        this.matrixCafeConfiguration = matrixCafeConfiguration;
        configurationMap.put(MatrixCafeConfiguration.NAME, ConfigurationContainer.of(matrixCafeConfiguration, MatrixCafeConfiguration.NAME));
    }

    public ConfigurationDataFormatter getFormatter() {
        return formatter;
    }

    @Getter
    static class ConfigurationContainer {

        private final Object configurationInstance ;

        private final String name ;

        private boolean shouldSave ;

        public static @NotNull ConfigurationContainer of(@NotNull Object configurationInstance){
            String configName = getConfigName(configurationInstance.getClass());
            if (Strings.isInvalid(configName)){
                throw new ConfigurationException(ConfigurationException.ExceptionType.NO_NAME_STATEMENT_CONFIG_TYPE, configurationInstance.getClass().getName());
            }

            return new ConfigurationContainer(configurationInstance, configName);
        }

        @Contract("_, _ -> new")
        public static @NotNull ConfigurationContainer of(@NotNull Object configurationInstance, String name){
            return new ConfigurationContainer(configurationInstance, name);
        }

        public ConfigurationContainer(@NotNull Object configurationInstance, String name) {
            this.configurationInstance = configurationInstance;
            this.name = name;

            ConfigurationShouldSave configurationShouldSave = configurationInstance.getClass().getAnnotation(ConfigurationShouldSave.class);
            if (configurationShouldSave!=null){
                shouldSave = configurationShouldSave.value() ;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConfigurationContainer that = (ConfigurationContainer) o;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
