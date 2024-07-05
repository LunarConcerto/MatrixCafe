package cafe.lunarconcerto.matrixcafe.api.responder.impl.command;

import cafe.lunarconcerto.matrixcafe.api.data.response.Response;
import cafe.lunarconcerto.matrixcafe.api.responder.Responder;
import cafe.lunarconcerto.matrixcafe.api.responder.ResponderGroup;
import cafe.lunarconcerto.matrixcafe.api.responder.action.ActionParam;

/**
 * <h1>命令行风格的响应器</h1>
 * 该响应器旨在实现一个严格的验证数据格式的响应器组件
 */
public class CommandResponder extends Responder {

    protected CommandParser parser ;

    public CommandResponder(String id) {
        super(id);
    }

    @Override
    public ResponderGroup<?> createGroup() {
        return null;
    }

    @Override
    public Response<?> respond(ActionParam data) {
        CommandActionParam actionData = (CommandActionParam) data;
        return null;
    }


}
