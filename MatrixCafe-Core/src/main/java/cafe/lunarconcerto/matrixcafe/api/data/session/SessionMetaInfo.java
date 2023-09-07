package cafe.lunarconcerto.matrixcafe.api.data.session;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record SessionMetaInfo(

        SessionType type,

        String id

) {

    public static @NotNull SessionMetaInfo of(SessionType type, String id){
        return new SessionMetaInfo(type, id);
    }


    public static @NotNull SessionMetaInfo of(String id){
        return new SessionMetaInfo(SessionType.UNKNOWN, id);
    }

    public long longId(){
        return Long.parseLong(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionMetaInfo that = (SessionMetaInfo) o;
        return Objects.equals(type, that.type) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id);
    }
}
