package cafe.lunarconcerto.matrixcafe.api.data.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class SessionMetaInfo {

    public static @NotNull SessionMetaInfo of(SessionType type, String id) {
        return new SessionMetaInfo(type, id);
    }

    public static @NotNull SessionMetaInfo of(String id) {
        return new SessionMetaInfo(SessionType.UNKNOWN, id);
    }

    public static SessionMetaInfo NULL = of(null);
    private final SessionType type;
    private final String id;

    public SessionMetaInfo(SessionType type, String id) {
        this.type = type;
        this.id = id;
    }


}

