package cafe.lunarconcerto.matrixcafe.api.data.message.content;

import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 标记一条纯文本信息
 * @param text 文本信息内容
 */
public record TextContent(String text) implements MessageContent {

    public static final TextContent EMPTY = new TextContent(Strings.EMPTY);

    public static final TextContent BLANK = new TextContent(Strings.BLANK);

    @Contract("_ -> new")
    public static @NotNull TextContent of(String text){
        return new TextContent(text);
    }

    @Override
    public String text() {
        return text;
    }

    @Override
    public boolean isEmpty() {
        return Strings.isInvalid(text);
    }

    @Override
    public String toString() {
        return text ;
    }

}
