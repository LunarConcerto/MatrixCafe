package cafe.lunarconcerto.matrixcafe.api.extension.adapter;

import cafe.lunarconcerto.matrixcafe.api.web.ws.WebSocketClientValidator;
import cafe.lunarconcerto.matrixcafe.api.web.ws.WebSocketConnectedClient;
import io.javalin.websocket.WsMessageContext;
import org.jetbrains.annotations.NotNull;

/**
 * @author LunarConcerto
 * @time 2023/12/14
 */
public class AdapterWebSocketClientConnectionProvider {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private WebSocketClientValidator validator;

    private WebSocketConnectedClient client;

    protected void onWebSocketMessage(WsMessageContext context){

    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Setter / Getter
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public void setClient(@NotNull WebSocketConnectedClient client) {
        this.client = client;
        client.bind(this::onWebSocketMessage);
    }

    public WebSocketConnectedClient getClient() {
        return client;
    }

    public WebSocketClientValidator getValidator() {
        return validator;
    }
}
