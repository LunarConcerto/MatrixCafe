package cafe.lunarconcerto.matrixcafe.api.web.ws;

import cafe.lunarconcerto.matrixcafe.api.config.model.MatrixCafeConfiguration;
import cafe.lunarconcerto.matrixcafe.api.config.model.WebSocketServerInfo;
import cafe.lunarconcerto.matrixcafe.api.common.Bus;
import io.javalin.Javalin;
import io.javalin.websocket.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.function.Consumer;

@Slf4j
@Singleton
public final class WebSocketServer {

    private static final Consumer<WsMessageContext> DEFAULT_WS_MESSAGE_HANDLER = wsMessageContext -> {};

    private final Javalin app ;
    private final WebSocketServerInfo info ;

    private Map<String, WsConnectContext> connectedWsClientMap;

    private Map<String, WsMessageHandler> wsHandlerMap;

    @Inject
    public WebSocketServer(@NotNull MatrixCafeConfiguration configuration) {
        info = configuration.getWebSocketServer();
        app = Javalin.create()
                .ws(info.getPath(), ws -> {
                    ws.onConnect(this::onWsClientConnect);
                    ws.onMessage(this::onWsClientMessage);
                    ws.onClose(this::onWsClientClose);
                    ws.onError(this::onWsClientError);
                    ws.onBinaryMessage(this::onWsClientBinaryMessage);
                });
    }

    private void onWsClientConnect(@NotNull WsConnectContext wsConnectContext) {
        log.info("::WebSocket客户端 -> {}[{}] 已经连接成功", wsConnectContext.host(), wsConnectContext.getSessionId());
        connectedWsClientMap.put(wsConnectContext.getSessionId(), wsConnectContext);
        WebSocketClientConnectEvent event = new WebSocketClientConnectEvent(new WebSocketConnectedClient(this, wsConnectContext));
        Bus.postSystemEvent(event);
    }

    private void onWsClientMessage(@NotNull WsMessageContext wsMessageContext) {
        log.debug("::WebSocket客户端[{}]的信息 -> {}",wsMessageContext.getSessionId(), wsMessageContext.message());
        if (wsHandlerMap.containsKey(wsMessageContext.getSessionId())){
            wsHandlerMap.get(wsMessageContext.getSessionId()).accept(wsMessageContext);
        }
    }

    private void onWsClientBinaryMessage(WsBinaryMessageContext wsBinaryMessageContext) {
    }

    private void onWsClientClose(@NotNull WsCloseContext wsCloseContext) {
        log.info("::WebSocket客户端 {}[{}] 断开连接.", wsCloseContext.host(), wsCloseContext.getSessionId());

        wsHandlerMap.remove(wsCloseContext.getSessionId());
    }

    private void onWsClientError(@NotNull WsErrorContext wsErrorContext) {
        log.warn("::WebSocket客户端 {}[{}] 发生错误.", wsErrorContext.host(), wsErrorContext.getSessionId());
    }

    public void start(){
        app.start(info.getPort());
        log.info("::WebSocket服务器已在路径 {}:{} 上开放.", info.getPath(), info.getPort());

        connectedWsClientMap = new HashMap<>();
        wsHandlerMap = new HashMap<>();
    }

    private WsMessageHandler getHandler(String wsSessionId){
        if (wsHandlerMap.containsKey(wsSessionId)) {
            return wsHandlerMap.get(wsSessionId);
        }else {
            WsMessageHandler handler = WsMessageHandler.create(wsSessionId);
            wsHandlerMap.put(wsSessionId, handler);
            return handler ;
        }
    }

    public void registerWsMessageHandler(String wsSessionId, Consumer<WsMessageContext> wsMessageContextConsumer){
        WsMessageHandler handler = getHandler(wsSessionId);
        handler.setName(wsSessionId);
        handler.setWsMessageHandler(wsMessageContextConsumer);
    }

    public void sendMessageToAllClient(String message){
        connectedWsClientMap.values().forEach(wsConnectContext -> wsConnectContext.send(message));
    }

    public void sendMessageToClient(String clientSessionId, String message){
        if (connectedWsClientMap.containsKey(clientSessionId)) {
            connectedWsClientMap.get(clientSessionId)
                    .send(message);
        }
    }

    void registerWsMessageHandler(
            String name,
            String sessionId,
            Consumer<WsMessageContext> wsMessageHandler,
            Consumer<WsBinaryMessageContext> wsBinaryMessageHandler,
            Consumer<WsCloseContext> wsCloseHandler,
            Consumer<WsErrorContext> wsErrorHandler){
        WsMessageHandler handler = getHandler(sessionId);
        handler.setName(name);
        handler.setWsMessageHandler(wsMessageHandler);
        handler.setWsBinaryMessageHandler(wsBinaryMessageHandler);
        handler.setWsCloseHandler(wsCloseHandler);
        handler.setWsErrorHandler(wsErrorHandler);
    }

    void registerWsMessageHandler(
            String sessionId,
            Consumer<WsMessageContext> wsMessageHandler,
            Consumer<WsBinaryMessageContext> wsBinaryMessageHandler,
            Consumer<WsCloseContext> wsCloseHandler,
            Consumer<WsErrorContext> wsErrorHandler){
        WsMessageHandler handler = getHandler(sessionId);
        handler.setName(sessionId);
        handler.setWsMessageHandler(wsMessageHandler);
        handler.setWsBinaryMessageHandler(wsBinaryMessageHandler);
        handler.setWsCloseHandler(wsCloseHandler);
        handler.setWsErrorHandler(wsErrorHandler);
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class WsMessageHandler {

        private String name ;

        private String sessionId ;

        private Consumer<WsMessageContext> wsMessageHandler = DEFAULT_WS_MESSAGE_HANDLER;

        private Consumer<WsBinaryMessageContext> wsBinaryMessageHandler;

        private Consumer<WsCloseContext> wsCloseHandler;

        private Consumer<WsErrorContext> wsErrorHandler;

        public static @NotNull WsMessageHandler create(String sessionId){
            return new WsMessageHandler(sessionId);
        }

        public WsMessageHandler(String sessionId) {
            this.sessionId = sessionId;
        }

        public void accept(WsMessageContext wsMessageContext) {
            wsMessageHandler.accept(wsMessageContext);
        }

        public void accept(WsBinaryMessageContext wsMessageContext) {
            if (wsBinaryMessageHandler == null) return; ;
            wsBinaryMessageHandler.accept(wsMessageContext);
        }

        public void accept(WsCloseContext wsMessageContext) {
            if (wsCloseHandler == null) return;
            wsCloseHandler.accept(wsMessageContext);
        }

        public void accept(WsErrorContext wsMessageContext) {
            if (wsErrorHandler == null) return;
            wsErrorHandler.accept(wsMessageContext);
        }
    }

}
