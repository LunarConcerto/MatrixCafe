package cafe.lunarconcerto.matrixcafe.api.responder.action;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class TextToMessageContentAction implements CommonAction {

    protected TextCommonAction textAction ;

    @Contract(value = "_ -> new", pure = true)
    public static @NotNull TextToMessageContentAction of(TextCommonAction action){
        return new TextToMessageContentAction(action);
    }

    public TextToMessageContentAction(TextCommonAction textAction) {
        this.textAction = textAction;
    }

    @Override
    public MessageContent doAction(ActionParam message) {
        return new TextContent(textAction.doAction(message));
    }
}
