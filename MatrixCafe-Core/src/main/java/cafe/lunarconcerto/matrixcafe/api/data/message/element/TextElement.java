package cafe.lunarconcerto.matrixcafe.api.data.message.element;

/**
 * 纯字符串
 * <p>
 * 纯字符串是最基本的消息格式.
 * @param text 字符串源
 */
public record TextElement(String text) implements Element { }
