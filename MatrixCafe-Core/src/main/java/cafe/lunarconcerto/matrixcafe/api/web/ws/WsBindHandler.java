package cafe.lunarconcerto.matrixcafe.api.web.ws;

import io.javalin.websocket.WsBinaryMessageContext;
import io.javalin.websocket.WsCloseContext;
import io.javalin.websocket.WsErrorContext;
import io.javalin.websocket.WsMessageContext;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * @author LunarConcerto
 * @time 2023/8/2
 */
@Getter
public class WsBindHandler {
    
    private WebSocketServer webSocketServer ;

    private String name ;

    private String sessionId ;

    private Consumer<WsMessageContext> wsMessageHandler;

    private Consumer<WsBinaryMessageContext> wsBinaryMessageHandler;

    private Consumer<WsCloseContext> wsCloseHandler;

    private Consumer<WsErrorContext> wsErrorHandler;

    private WsBindHandler() { }

    WsBindHandler(WebSocketServer webSocketServer, String sessionId) {
        this.webSocketServer = webSocketServer;
        this.sessionId = sessionId;
    }

    WsBindHandler withAlias(String name){
        this.name = name ;
        return this ;
    }

    WsBindHandler withWsMessageHandler(Consumer<WsMessageContext> wsMessageContextConsumer) {
        this.wsMessageHandler = wsMessageContextConsumer;
        return this;
    }

    public WsBindHandler withWsBinaryMessageHandler(Consumer<WsBinaryMessageContext> wsBinaryMessageContextConsumer) {
        this.wsBinaryMessageHandler = wsBinaryMessageContextConsumer;
        return this;
    }

    public WsBindHandler withWsCloseHandler(Consumer<WsCloseContext> wsCloseContextConsumer) {
        this.wsCloseHandler = wsCloseContextConsumer;
        return this;
    }

    public WsBindHandler withWsErrorHandler(Consumer<WsErrorContext> wsErrorContextConsumer) {
        this.wsErrorHandler = wsErrorContextConsumer;
        return this;
    }

    public void endBind(){
        webSocketServer.registerWsMessageHandler(
                name,
                sessionId,
                wsMessageHandler,
                wsBinaryMessageHandler,
                wsCloseHandler,
                wsErrorHandler
        );
    }


}
