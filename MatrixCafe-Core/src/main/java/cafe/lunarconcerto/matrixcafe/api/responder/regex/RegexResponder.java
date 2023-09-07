package cafe.lunarconcerto.matrixcafe.api.responder.regex;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.response.ResponseResult;
import cafe.lunarconcerto.matrixcafe.api.responder.Responder;
import cafe.lunarconcerto.matrixcafe.api.responder.ResponderGroup;
import cafe.lunarconcerto.matrixcafe.api.responder.action.ActionData;
import cafe.lunarconcerto.matrixcafe.api.responder.action.CommonAction;
import org.jetbrains.annotations.NotNull;

public class RegexResponder extends Responder {

    protected RegexMatcher matcher ;

    protected CommonAction action ;

    public RegexResponder(String id) {
        super(id);
    }

    public RegexResponder(@NotNull RegexResponderBuilder builder) {
        super(builder.getId(), builder.getDescriptor(), builder.getPriority());
        matcher = builder.matcher;
        action = builder.action;
    }

    @Override
    public ResponderGroup<?> createGroup() {
        return new RegexResponderGroup();
    }

    @Override
    public ResponseResult<?> respond(ActionData data) {
        MessageContent replyContent = action.start(data);
        if (replyContent.isEmpty()) {
            return ResponseResult.failureResponse() ;
        }

        return sendReply(replyContent, data.message());
    }


}
