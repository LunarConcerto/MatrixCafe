package cafe.lunarconcerto.matrixcafe.api.responder.action;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.TextContent;

public interface CommonAction extends Action<MessageContent, ActionData> {

    CommonAction EMPTY_ACTION = CommonAction::emptyAction;

    static MessageContent emptyAction(ActionData data) {
        return TextContent.EMPTY;
    }

    @Override
    MessageContent start(ActionData message);

}
