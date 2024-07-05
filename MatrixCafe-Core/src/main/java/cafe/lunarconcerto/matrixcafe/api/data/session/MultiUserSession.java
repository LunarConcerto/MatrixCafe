package cafe.lunarconcerto.matrixcafe.api.data.session;

import cafe.lunarconcerto.matrixcafe.api.bot.Bot;
import org.jetbrains.annotations.NotNull;

public class MultiUserSession extends Session {

    public MultiUserSession(SessionMetaInfo info, @NotNull Bot bot) {
        super(info, bot);
    }

}
