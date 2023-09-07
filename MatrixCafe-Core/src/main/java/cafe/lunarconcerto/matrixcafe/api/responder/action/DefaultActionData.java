package cafe.lunarconcerto.matrixcafe.api.responder.action;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;

/**
 * @author LunarConcerto
 * @time 2023/6/9
 */
public class DefaultActionData implements ActionData {

    protected SessionMessage message ;

    @Override
    public SessionMessage message() {
        return message;
    }

    public DefaultActionData(SessionMessage message) {
        this.message = message;
    }
}
