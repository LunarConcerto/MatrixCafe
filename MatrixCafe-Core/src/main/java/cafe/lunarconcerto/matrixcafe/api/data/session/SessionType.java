package cafe.lunarconcerto.matrixcafe.api.data.session;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

/**
 * 对上下文类型的抽象;
 * 此处进行简单的抽象, 即分为单用户和多用户,
 * 比如, 前者可以对应QQ中的私聊, 后者则可以对应QQ群, 频道等.
 * 若存在无法对应的类型时, 则也可以手动进行拓展.
 *
 * @param type 类型的可用名, 或是描述
 */
public record SessionType(String type) {

    /**
     * 单用户类型. 即私聊等.
     */
    public static final SessionType SINGLE_USER = of("single");

    /**
     * 多用户类型. 即群聊等.
     */
    public static final SessionType MULTI_USER = of("multi");

    /**
     * 未知类型. 默认类型.
     */
    public static final SessionType UNKNOWN = of("unknown");

    private static final HashMap<String, SessionType> SESSION_TYPE_MAP = new HashMap<>();

    static {
        SESSION_TYPE_MAP.put(SINGLE_USER.type(), SINGLE_USER);
        SESSION_TYPE_MAP.put(MULTI_USER.type(), MULTI_USER);
        SESSION_TYPE_MAP.put(UNKNOWN.type(), UNKNOWN);
    }

    public static void add(SessionType type) {
        SESSION_TYPE_MAP.put(type.type(), type);
    }

    @Contract(pure = true)
    public static SessionType of(@NotNull String name) {
        SessionType type = SESSION_TYPE_MAP.get(name);
        return type == null ? UNKNOWN : type;
    }

    public String toString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SessionType that)) return false;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
