package cafe.lunarconcerto.matrixcafe.api.web.http;

import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
@AllArgsConstructor
public final class HttpResult {
    private final HttpStatus status;
    private final boolean success;
    private final String message;

    public static @NotNull HttpResult of(boolean success, String message) {
        return new HttpResult(HttpStatus.UNDEFINED, success, message);
    }

    @Contract("_, _ -> new")
    public static @NotNull HttpResult of(HttpStatus status, boolean success) {
        return new HttpResult(status, success, Strings.EMPTY);
    }

    public static @NotNull HttpResult of(HttpStatus status, boolean success, String message) {
        return new HttpResult(status, success, message);
    }

}
