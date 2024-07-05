package cafe.lunarconcerto.matrixcafe.api.responder.action;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;

public interface CommonAction extends Action<MessageContent, ActionParam> {

    CommonAction EMPTY_ACTION = CommonAction::emptyAction;

    static MessageContent emptyAction(ActionParam data) {
        return TextContent.EMPTY;
    }

    @Override
    MessageContent doAction(ActionParam message);

}
