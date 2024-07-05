package cafe.lunarconcerto.matrixcafe.api.bot;

import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <h1>协议端账户信息</h1>
 * 由适配器绑定 Bot 时分配给 Bot 使用.
 * @author LunarConcerto
 * @time 2023/9/24
 */
@Data
@AllArgsConstructor
public class ProtocolAccount {

    public static final ProtocolAccount EMPTY = new ProtocolAccount();

    public String id = Strings.EMPTY;

    public String username = Strings.EMPTY;

    public String password = Strings.EMPTY;

    public ProtocolAccount() { }
}
