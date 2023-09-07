package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.data.session.*;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MatrixCafeSessionManager implements SessionManager {

    protected Cache<SessionMetaInfo, Session> sessionCache;

    public MatrixCafeSessionManager() {
        sessionCache = Caffeine.newBuilder()
                .initialCapacity(20)
                .maximumSize(100)
                .build();
    }

    @Override
    public Optional<Session> get(SessionMetaInfo info) {
        return Optional.ofNullable(sessionCache.getIfPresent(info)) ;
    }

    @Override
    public SessionMessage warp(@NotNull Message message) {
        Optional<Session> session = get(message.getSessionMetaInfo());
        if (session.isPresent()){
            return new SessionMessage(message, session.get());
        }else {
            Session generateSession = createSession(message);
            sessionCache.put(generateSession.getInfo(), generateSession);
            return new SessionMessage(message, generateSession) ;
        }
    }

    private Session createSession(@NotNull Message message){
        SessionMetaInfo info = message.getSessionMetaInfo();
        SessionType type = info.type();
        Session session ;

        if (type == SessionType.SINGLE_USER){
            session = new SingleUserSession(info, message.getBot());
        }else if (type == SessionType.MULTI_USER){
            session = new MultiUserSession(info, message.getBot());
        }else {
            session = UnknownSession.UNKNOWN_SESSION ;
        }

        return session ;
    }


}
