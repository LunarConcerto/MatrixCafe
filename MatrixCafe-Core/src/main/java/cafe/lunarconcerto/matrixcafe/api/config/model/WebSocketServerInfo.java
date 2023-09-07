package cafe.lunarconcerto.matrixcafe.api.config.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketServerInfo {

    private int port = 3005;

    private String path = "";

}
