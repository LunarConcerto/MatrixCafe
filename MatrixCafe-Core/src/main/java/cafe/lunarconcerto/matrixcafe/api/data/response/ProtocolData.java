package cafe.lunarconcerto.matrixcafe.api.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 代表一个协议端的响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolData<T> {

    protected T data ;

    protected String identity;


}
