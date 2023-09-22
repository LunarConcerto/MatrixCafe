package cafe.lunarconcerto.matrixcafe.api.config.model;

import cafe.lunarconcerto.matrixcafe.api.common.MessageMode;
import cafe.lunarconcerto.matrixcafe.api.config.Configuration;
import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationShouldSave;
import cafe.lunarconcerto.matrixcafe.api.common.ApplicationContext;
import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

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
        return configuration;
    }

    /**
     * 该配置文件的文件名
     */
    public static final String NAME = "matrix-cafe.yaml";

    private MessageMode mode = MessageMode.PASSIVE ;

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
     * 是否在没有可用适配器时退出?
     */
    private boolean noAdapterExit = true ;

    /**
     * 系统心跳事件间隔
     */
    private int heartbeatInterval = 5;

}

