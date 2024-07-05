package cafe.lunarconcerto.matrixcafe.api.data.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * 标记一个消息的发送者
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sender<T> {

    private String id;
    private String nickname;
    private T data;
    private Sex sex;

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Static Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    @Contract("_, _ -> new")
    public static @NotNull Sender<?> of(String id, String nickname) {
        return new Sender<>(id, nickname, null, Sex.UNDEFINE);
    }

    public static <T> @NotNull Sender<T> of(String id, String nickname, T data) {
        return new Sender<>(id, nickname, data, Sex.UNDEFINE);
    }

    public static <T> @NotNull Sender<T> of(String id, String nickname, T data, Sex sex) {
        return new Sender<>(id, nickname, data, sex);
    }

}
