package cafe.lunarconcerto.matrixcafe.api.responder.descriptor;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;

public class TextDescriptor implements ResponderDescriptor {

    public static final TextDescriptor DEFAULT_DESCRIPTOR = new TextDescriptor("描述未定义", "用法未定义");

    /**
     * 用法 / 描述
     */
    protected final TextContent description ;

    /**
     * 样例
     */
    protected final TextContent example ;

    public TextDescriptor(String description) {
        this.description = new TextContent(description);
        example = new TextContent(Strings.EMPTY);
    }

    public TextDescriptor(String description, String example) {
        this.description = new TextContent(description);
        this.example = new TextContent(example);
    }

    @Override
    public MessageContent description() {
        return description;
    }

    @Override
    public MessageContent example() {
        return example;
    }

}
