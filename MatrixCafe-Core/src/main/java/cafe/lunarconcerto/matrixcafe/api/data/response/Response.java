package cafe.lunarconcerto.matrixcafe.api.data.response;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 响应器的响应
 * <p>
 * 响应最后会被回传给消息解析器, 用于确认消息是否被成功响应.
 * @param <T> 任意回报, 可能是由协议端传回的响应等, 主要作用是为拦截器进行after操作.
 */
@Getter
public class Response<T> {

    protected T data ;

    protected ResponseStatus status ;

    public Response(T data) {
        this(data, ResponseStatus.UNDEFINE);
    }

    public Response(T data, ResponseStatus status) {
        this.data = data;
        this.status = status;
    }

    public static final Response<?> OK = new Response<>(null, ResponseStatus.OK);

    public static final Response<?> ERROR = new Response<>(null, ResponseStatus.ERROR);

    public static final Response<?> FAILURE = new Response<>(null, ResponseStatus.FAILURE);

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull Response<T> okResponse(T data){
        return new Response<>(data, ResponseStatus.OK);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull Response<?> okResponse(){
        return new Response<>(null, ResponseStatus.OK);
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull Response<T> errorResponse(T data){
        return new Response<>(data, ResponseStatus.ERROR);
    }

    public static @NotNull Response<?> errorResponse(){
        return new Response<>(null, ResponseStatus.ERROR);
    }

    public static <T> @NotNull Response<T> failureResponse(T data){
        return new Response<>(data, ResponseStatus.FAILURE);
    }

    public static @NotNull Response<?> failureResponse(){
        return new Response<>(null, ResponseStatus.FAILURE);
    }


    public boolean isProtocolResponse(){
        return data instanceof ProtocolData<?>;
    }

}
