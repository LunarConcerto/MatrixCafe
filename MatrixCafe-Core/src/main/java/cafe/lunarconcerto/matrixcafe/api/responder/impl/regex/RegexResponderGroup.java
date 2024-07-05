package cafe.lunarconcerto.matrixcafe.api.responder.impl.regex;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.responder.MatchResult;
import cafe.lunarconcerto.matrixcafe.api.responder.ResponderGroup;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author LunarConcerto
 * @time 2023/6/2
 */
public class RegexResponderGroup extends ResponderGroup<RegexResponder> {

    private Map<RegexMatcher, RegexResponder> responderMap ;

    public RegexResponderGroup() {
        responderMap = new HashMap<>();
    }

    @Override
    public Collection<RegexResponder> targets() {
        return responderMap.values();
    }

    @Override
    public MatchResult match(SessionMessage message) {
        for (RegexMatcher matcher : responderMap.keySet()) {
            Optional<MatchGroup> result = matcher.match(message);
            if (result.isPresent()){
                return MatchResult.ok(responderMap.get(matcher).getId(), result.get());
            }
        }
        return MatchResult.FAILURE ;
    }

    @Override
    public void add(RegexResponder responder) {

    }

    @Override
    public void remove(RegexResponder responder) {

    }

}
