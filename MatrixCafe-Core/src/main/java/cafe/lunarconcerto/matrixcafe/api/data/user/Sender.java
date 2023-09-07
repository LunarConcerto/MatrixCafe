package cafe.lunarconcerto.matrixcafe.api.data.user;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 标记一个消息的发送者
 */
public record Sender<T> (

        String id,

        String nickname,

        T data,

        Sex sex

){

    @Contract("_, _ -> new")
    public static @NotNull Sender<?> of(String id, String nickname){
        return new Sender<>(id, nickname, null, Sex.UNDEFINE);
    }

    public static <T> @NotNull Sender<T> of(String id, String nickname, T data){
        return new Sender<>(id, nickname, data, Sex.UNDEFINE);
    }

    public static <T> @NotNull Sender<T> of(String id, String nickname, T data, Sex sex){
        return new Sender<>(id, nickname, data, sex);
    }

}
