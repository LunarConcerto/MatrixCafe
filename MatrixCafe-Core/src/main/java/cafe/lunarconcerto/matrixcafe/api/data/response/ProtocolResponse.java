package cafe.lunarconcerto.matrixcafe.api.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 代表一个协议端的响应
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolResponse<T> {

    protected T data ;

    protected ResponseStatus status ;

    @Contract("_ -> new")
    public static <T> @NotNull ProtocolResponse<T> okResponse(T data){
        return new ProtocolResponse<>(data, ResponseStatus.OK);
    }

    public static @NotNull ProtocolResponse<?> okResponse(){
        return new ProtocolResponse<>(null, ResponseStatus.OK);
    }

    public static <T> @NotNull ProtocolResponse<T> errorResponse(T data){
        return new ProtocolResponse<>(data, ResponseStatus.ERROR);
    }

    public static @NotNull ProtocolResponse<?> errorResponse(){
        return new ProtocolResponse<>(null, ResponseStatus.ERROR);
    }

    public static <T> @NotNull ProtocolResponse<T> failureResponse(T data){
        return new ProtocolResponse<>(data, ResponseStatus.FAILURE);
    }

    @Contract(" -> new")
    public static @NotNull ProtocolResponse<?> failureResponse(){
        return new ProtocolResponse<>(null, ResponseStatus.FAILURE);
    }

    public static <T> @NotNull ProtocolResponse<T> undefineResponse(T data){
        return new ProtocolResponse<>(data, ResponseStatus.UNDEFINE);
    }

    public static @NotNull ProtocolResponse<?> undefineResponse(){
        return new ProtocolResponse<>(null, ResponseStatus.UNDEFINE);
    }


}
