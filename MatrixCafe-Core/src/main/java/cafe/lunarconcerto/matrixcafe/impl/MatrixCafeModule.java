package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.application.Application;
import cafe.lunarconcerto.matrixcafe.api.application.ApplicationContext;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionManager;
import cafe.lunarconcerto.matrixcafe.api.db.Database;
import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationManager;
import cafe.lunarconcerto.matrixcafe.api.config.MatrixCafeConfiguration;
import cafe.lunarconcerto.matrixcafe.api.extension.ExtensionRegistry;
import cafe.lunarconcerto.matrixcafe.api.plugin.PluginManager;
import cafe.lunarconcerto.matrixcafe.api.bot.BotManager;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class MatrixCafeModule extends AbstractModule {

    @Provides
    ApplicationContext providesApplicationContext(MatrixCafeApplicationContext applicationContext){
        return applicationContext ;
    }

    @Provides
    ConfigurationManager providesConfigurationManager(MatrixCafeConfigurationManager configurationManager){
        return configurationManager;
    }

    @Provides
    ExtensionRegistry providesExtensionRegistry(MatrixCafeExtensionRegistry extensionRegistry){
        return extensionRegistry ;
    }

    @Provides
    @Inject
    MatrixCafeConfiguration providesMatrixCafeConfiguration(@NotNull MatrixCafeConfigurationManager configurationManager) throws IOException {
        MatrixCafeConfiguration configuration = configurationManager.getConfig(MatrixCafeConfiguration.class);

        if (configuration == null){
            log.error("没有可用的 MatrixCafe 配置文件.");
            configurationManager.createConfigFile(MatrixCafeConfiguration.create());
            configurationManager.save();
            log.info("已生成默认配置文件, 请先进行一次修改, 程序将退出.");
            Application.exit(0);
        }

        return configuration ;
    }

    @Provides
    Database providesDatabase(MatrixCafeDatabase database){
        return database;
    }

    @Provides
    PluginManager providesPluginManager(MatrixCafePluginManager pluginManager){
        return pluginManager;
    }

    @Provides
    Cache<String, Object> providesObjectPools(){
        return Caffeine.newBuilder()
                .build();
    }

    @Provides
    BotManager providesBotManager(MatrixCafeBotManager botManager){
        return botManager ;
    }

    @Provides
    SessionManager providesSessionManager(MatrixCafeSessionManager sessionManager){
        return sessionManager;
    }

}
