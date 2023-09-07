package cafe.lunarconcerto.matrixcafe.api.data.session;

import cafe.lunarconcerto.matrixcafe.api.protocol.Bot;
import org.jetbrains.annotations.NotNull;

public class SingleUserSession extends Session {

    public SingleUserSession(
            SessionMetaInfo info,
            @NotNull Bot bot) {
        super(info, bot);
    }

}
