package cafe.lunarconcerto.matrixcafe.api.config;

import cafe.lunarconcerto.matrixcafe.api.application.ApplicationContext;
import cafe.lunarconcerto.matrixcafe.api.bot.BotConfig;
import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Singleton
@NoArgsConstructor
@AllArgsConstructor
@Configuration(MatrixCafeConfiguration.NAME)
@ConfigurationShouldSave(false)
@ApplicationContext.InApplicationContextName("config")
public class MatrixCafeConfiguration {

    public static @NotNull MatrixCafeConfiguration create(){
        MatrixCafeConfiguration configuration = new MatrixCafeConfiguration();
        configuration.setHttpServerInfo(new HttpServerInfo());
        configuration.setDb(new DatabaseConnectionInfo());
        configuration.setWebSocketServer(new WebSocketServerInfo());
        List<BotConfig> bots = new ArrayList<>();
        bots.add(new BotConfig());
        configuration.setBots(bots);
        return configuration;
    }

    /**
     * 该配置文件的文件名
     */
    public static final String NAME = "matrix-cafe.yaml";

    /**
     * 数据库连接信息
     */
    private DatabaseConnectionInfo db ;

    /**
     * ws服务器信息
     */
    private WebSocketServerInfo webSocketServer ;

    /**
     * http服务器信息
     */
    private HttpServerInfo httpServerInfo ;

    /**
     * Bot 设置
     * <p>
     * 一个设置代表一个 Bot
     */
    private List<BotConfig> bots;

    /**
     * 是否在没有可用适配器时退出?
     */
    private boolean noAdapterExit = true ;

    /**
     * 系统心跳事件间隔
     */
    private int heartbeatInterval = 5;

}

