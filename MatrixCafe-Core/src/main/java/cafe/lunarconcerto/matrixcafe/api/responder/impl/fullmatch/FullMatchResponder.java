package cafe.lunarconcerto.matrixcafe.api.responder.impl.fullmatch;

import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.data.response.Response;
import cafe.lunarconcerto.matrixcafe.api.responder.Responder;
import cafe.lunarconcerto.matrixcafe.api.responder.ResponderGroup;
import cafe.lunarconcerto.matrixcafe.api.responder.action.ActionParam;
import cafe.lunarconcerto.matrixcafe.api.responder.action.CommonAction;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author LunarConcerto
 * @time 2023/5/19
 */
public class FullMatchResponder extends Responder {

    protected final Set<String> keywords;

    protected final CommonAction action ;

    public static @NotNull FullMatchResponderBuilder builder(String id){
        return FullMatchResponderBuilder.builder(id);
    }

    public FullMatchResponder(String id, Set<String> keywords, CommonAction action) {
        super(id);
        this.keywords = keywords;
        this.action = action;
    }

    public FullMatchResponder(@NotNull FullMatchResponderBuilder builder) {
        super(builder.getId(), builder.getDescriptor(), builder.getPriority());
        this.keywords = builder.keywords;
        this.action = builder.action;
    }

    @Override
    public ResponderGroup<?> createGroup() {
        return new FullMatchResponderGroup();
    }

    @Override
    public Response<?> respond(ActionParam data) {
        MessageContent content = action.doAction(data);
        if (content.isEmpty()){
            return Response.FAILURE ;
        }else {
            return Response.okResponse(data.message().sendReply(content));
        }
    }



}
