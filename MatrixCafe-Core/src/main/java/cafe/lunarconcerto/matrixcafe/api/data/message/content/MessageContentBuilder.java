package cafe.lunarconcerto.matrixcafe.api.data.message.content;

import cafe.lunarconcerto.matrixcafe.api.data.message.element.Element;
import cafe.lunarconcerto.matrixcafe.api.data.message.element.TextElement;

public class MessageContentBuilder extends MessageContentChain {

    @Override
    public MessageContentBuilder addFirst(Element element) {
        return (MessageContentBuilder) super.addFirst(element);
    }

    @Override
    public MessageContentBuilder addLast(Element element) {
        return (MessageContentBuilder) super.addLast(element);
    }

    public MessageContentBuilder addFirst(String text) {
        return addFirst(new TextElement(text));
    }

    public MessageContentBuilder addLast(String text) {
        return addLast(new TextElement(text));
    }

}
