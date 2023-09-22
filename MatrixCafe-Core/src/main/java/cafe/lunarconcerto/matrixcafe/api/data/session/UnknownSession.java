package cafe.lunarconcerto.matrixcafe.api.data.session;

import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;

public class UnknownSession extends Session{

    public static final UnknownSession UNKNOWN_SESSION = new UnknownSession(null, Bot.NULL);

    private UnknownSession(SessionMetaInfo info, Bot bot) {
        super(info, bot);
    }

}
