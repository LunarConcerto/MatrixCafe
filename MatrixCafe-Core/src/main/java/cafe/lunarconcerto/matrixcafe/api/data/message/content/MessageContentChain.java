package cafe.lunarconcerto.matrixcafe.api.data.message.content;

import cafe.lunarconcerto.matrixcafe.api.data.message.element.Element;

import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * 消息元素链
 * <p>
 * 该类维护一个链表, 用于表示一条含多种消息元素的内容
 */
public class MessageContentChain implements MessageContent {

    protected final LinkedList<Element> elements;

    public MessageContentChain() {
        elements = new LinkedList<>();
    }

    /**
     * 在链首插入一个消息元素
     * @param element 目标对象
     * @return this
     */
    public MessageContentChain addFirst(Element element) {
        elements.addFirst(element);
        return this;
    }

    /**
     * 在链尾加入一个消息元素
     * @param element 目标对象
     * @return this
     */
    public MessageContentChain addLast(Element element) {
        elements.addLast(element);
        return this;
    }

    /**
     * 将另一个消息元素链连接到该消息链中
     * @param other 其它消息元素链
     * @return this
     */
    public MessageContentChain join(MessageContentChain other){
        elements.addAll(other.elements);
        return this;
    }

    @Override
    public String text() {
        return elements.stream().map(Element::toString).collect(Collectors.joining());
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }
}
