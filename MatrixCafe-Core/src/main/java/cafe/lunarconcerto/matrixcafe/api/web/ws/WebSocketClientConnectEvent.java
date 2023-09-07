package cafe.lunarconcerto.matrixcafe.api.web.ws;

import cafe.lunarconcerto.matrixcafe.api.data.event.SystemEvent;

/**
 * <h1>WebSocket 客户端连接事件</h1>
 */
public class WebSocketClientConnectEvent extends SystemEvent {

    protected WebSocketConnectedClient client ;

    public WebSocketClientConnectEvent(WebSocketConnectedClient client) {
        this.client = client;
    }

    public WebSocketConnectedClient getClient() {
        return client;
    }
}
