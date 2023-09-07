package cafe.lunarconcerto.matrixcafe.api.data.session;

import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;
import cafe.lunarconcerto.matrixcafe.api.protocol.EmptyBot;

public class UnknownSession extends Session{

    public static final UnknownSession UNKNOWN_SESSION = new UnknownSession(null, new EmptyBot());

    private UnknownSession(SessionMetaInfo info, Bot bot) {
        super(info, bot);
    }

}
