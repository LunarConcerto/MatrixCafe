package cafe.lunarconcerto.matrixcafe.api.web.ws;

import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsMessageContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * @author LunarConcerto
 * @time 2023/8/2
 */
public class WebSocketConnectedClient {

    protected WebSocketServer parent;

    protected WsConnectContext context ;

    public WebSocketConnectedClient(WebSocketServer parent, WsConnectContext context) {
        this.parent = parent;
        this.context = context;
    }

    /**
     * <h1>绑定消息处理器</h1>
     * 调用该方法以绑定对该次连接的 WebSocket 客户端的处理方法
     * @param messageConsumer 对该客户端消息的处理方法
     * @return WsBindHandler对象, 用于进一步绑定对该Ws客户端的其他消息的处理方法.
     */
    public WsBindHandler bind(Consumer<WsMessageContext> messageConsumer){
        return new WsBindHandler(parent, context.getSessionId()).withWsMessageHandler(messageConsumer);
    }

    public @Nullable String header(@NotNull String header) {
        return context.header(header);
    }

    public @Nullable String authorization(){
        return header("Authorization");
    }

    public @Nullable String userAgent(){
        return header("User-Agent");
    }

    public WsConnectContext getWsConnectContext() {
        return context;
    }

    @NotNull
    public String getSessionId() {
        return context.getSessionId();
    }

}
