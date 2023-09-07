package cafe.lunarconcerto.matrixcafe.api.web.ws;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class Ws {

    public static @NotNull WebSocket simpleWebsocketClient(String url, WebSocketListener webSocketListener){
        OkHttpClient client = new OkHttpClient.Builder()
                .pingInterval(60, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newWebSocket(request, webSocketListener);
    }

}
