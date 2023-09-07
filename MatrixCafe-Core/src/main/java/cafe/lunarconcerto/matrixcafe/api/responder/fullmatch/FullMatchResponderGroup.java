package cafe.lunarconcerto.matrixcafe.api.responder.fullmatch;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.responder.MatchResult;
import cafe.lunarconcerto.matrixcafe.api.responder.ResponderGroup;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LunarConcerto
 * @time 2023/6/2
 */
public class FullMatchResponderGroup extends ResponderGroup<FullMatchResponder> {

    private final Map<String, FullMatchResponder> responderMap ;

    public FullMatchResponderGroup() {
        responderMap = new HashMap<>();
    }

    @Override
    public Collection<FullMatchResponder> targets() {
        return responderMap.values();
    }

    @Override
    public MatchResult match(@NotNull SessionMessage message) {
        return MatchResult.ok(responderMap.get(message.getTextContents()).getId());
    }

    @Override
    public void add(@NotNull FullMatchResponder responder) {
        for (String keyword : responder.keywords) {
            responderMap.put(keyword, responder);
        }
    }

    @Override
    public void remove(FullMatchResponder responder) {

    }

}
