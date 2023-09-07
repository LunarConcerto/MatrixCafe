package cafe.lunarconcerto.matrixcafe.api.data.session;

import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;

import java.util.Optional;

public interface SessionManager {

    Optional<Session> get(SessionMetaInfo info);

    SessionMessage warp(Message message);

}
