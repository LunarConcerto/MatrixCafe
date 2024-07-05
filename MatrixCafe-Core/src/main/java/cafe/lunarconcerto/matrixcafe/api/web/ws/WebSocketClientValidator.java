package cafe.lunarconcerto.matrixcafe.api.web.ws;

/**
 * @author LunarConcerto
 * @time 2023/8/2
 */
public interface WebSocketClientValidator {

    boolean validate(WebSocketConnectedClient client);

}
