package cafe.lunarconcerto.matrixcafe.api.common;

import cafe.lunarconcerto.matrixcafe.api.data.message.Message;

/**
 * <h1>消息处理器</h1>
 * 消息处理器是消息处理的总入口,
 * 适配器转换后的消息应发送给该实现类的对象.
 */
public interface MessageResolver {

    void resolveMessage(Message message);

}
