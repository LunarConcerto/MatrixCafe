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
public class ResponseResult<T> {

    protected T data ;

    protected ResponseStatus status ;

    public ResponseResult(T data) {
        this(data, ResponseStatus.UNDEFINE);
    }

    public ResponseResult(T data, ResponseStatus status) {
        this.data = data;
        this.status = status;
    }

    public static final ResponseResult<?> OK = new ResponseResult<>(null, ResponseStatus.OK);

    public static final ResponseResult<?> ERROR = new ResponseResult<>(null, ResponseStatus.ERROR);

    public static final ResponseResult<?> FAILURE = new ResponseResult<>(null, ResponseStatus.FAILURE);

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull ResponseResult<T> okResponse(T data){
        return new ResponseResult<>(data, ResponseStatus.OK);
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull ResponseResult<?> okResponse(){
        return new ResponseResult<>(null, ResponseStatus.OK);
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull ResponseResult<T> errorResponse(T data){
        return new ResponseResult<>(data, ResponseStatus.ERROR);
    }

    public static @NotNull ResponseResult<?> errorResponse(){
        return new ResponseResult<>(null, ResponseStatus.ERROR);
    }

    public static <T> @NotNull ResponseResult<T> failureResponse(T data){
        return new ResponseResult<>(data, ResponseStatus.FAILURE);
    }

    public static @NotNull ResponseResult<?> failureResponse(){
        return new ResponseResult<>(null, ResponseStatus.FAILURE);
    }


    public boolean isProtocolResponse(){
        return data instanceof ProtocolResponse<?> ;
    }

}
