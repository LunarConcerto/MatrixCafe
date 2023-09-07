package cafe.lunarconcerto.matrixcafe.api.responder;

import cafe.lunarconcerto.matrixcafe.api.data.response.ResponseStatus;
import cafe.lunarconcerto.matrixcafe.api.responder.action.ActionData;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import org.jetbrains.annotations.NotNull;

/**
 * @author LunarConcerto
 * @time 2023/6/5
 */
public record MatchResult(String matchedResponderId, ResponseStatus status, ActionData data) {

    public static final MatchResult OK = new MatchResult(Strings.EMPTY, ResponseStatus.OK, null);

    public static final MatchResult ERROR = new MatchResult(Strings.EMPTY, ResponseStatus.ERROR, null);

    public static final MatchResult FAILURE = new MatchResult(Strings.EMPTY, ResponseStatus.FAILURE, null);

    public static final MatchResult UNDEFINE = new MatchResult(Strings.EMPTY, ResponseStatus.UNDEFINE, null);

    public static @NotNull MatchResult ok(String matchedResponderId, ActionData data) {
        return new MatchResult(matchedResponderId, ResponseStatus.OK, data);
    }

    public static @NotNull MatchResult error(String matchedResponderId, ActionData data) {
        return new MatchResult(matchedResponderId, ResponseStatus.ERROR, data);
    }

    public static @NotNull MatchResult failure(String matchedResponderId, ActionData data) {
        return new MatchResult(matchedResponderId, ResponseStatus.FAILURE, data);
    }

    public static @NotNull MatchResult ok(String matchedResponderId) {
        return new MatchResult(matchedResponderId, ResponseStatus.OK, null);
    }

    public static @NotNull MatchResult error(String matchedResponderId) {
        return new MatchResult(matchedResponderId, ResponseStatus.ERROR, null);
    }

    public static @NotNull MatchResult failure(String matchedResponderId) {
        return new MatchResult(matchedResponderId, ResponseStatus.FAILURE, null);
    }

    public static @NotNull MatchResult undefine(String matchedResponderId) {
        return new MatchResult(matchedResponderId, ResponseStatus.UNDEFINE, null);
    }

    public static @NotNull MatchResult ok(ActionData data) {
        return new MatchResult(Strings.EMPTY, ResponseStatus.OK, data);
    }

    public static @NotNull MatchResult error(ActionData data) {
        return new MatchResult(Strings.EMPTY, ResponseStatus.ERROR, data);
    }

    public static @NotNull MatchResult failure(ActionData data) {
        return new MatchResult(Strings.EMPTY, ResponseStatus.FAILURE, data);
    }

    public static @NotNull MatchResult undefine(ActionData data) {
        return new MatchResult(Strings.EMPTY, ResponseStatus.UNDEFINE, data);
    }

    public boolean isOk() {
        return status == ResponseStatus.OK;
    }

    public  boolean haveTarget(){
        return Strings.isValid(matchedResponderId);
    }

    public boolean haveData(){
        return data != null ;
    }

}
