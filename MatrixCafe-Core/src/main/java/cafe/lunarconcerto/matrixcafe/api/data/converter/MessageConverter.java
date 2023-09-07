package cafe.lunarconcerto.matrixcafe.api.data.converter;

import cafe.lunarconcerto.matrixcafe.api.data.message.Message;

/**
 * <h1>消息转换器</h1>
 * @author LunarConcerto
 * @time 2023/7/26
 * @param <T> 源消息类类型, 一般是从各种平台接收到的消息
 */
public abstract class MessageConverter<T> {

    public abstract Message convert(T target);

}
