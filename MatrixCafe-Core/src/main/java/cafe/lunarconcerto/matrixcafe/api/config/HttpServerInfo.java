package cafe.lunarconcerto.matrixcafe.api.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LunarConcerto
 * @time 2023/6/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpServerInfo {

    private boolean enable = true ;

    private int port = 3006 ;

}
