package cafe.lunarconcerto.matrixcafe.api.responder.regex;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.responder.action.ActionData;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/6/2
 */
public class MatchGroup implements ActionData {

    private final List<String> results ;

    private final SessionMessage message ;

    public MatchGroup(SessionMessage message) {
        this.message = message;
        results = new LinkedList<>();
    }

    public MatchGroup(SessionMessage message, String firstResult) {
        this.message = message;
        results = new LinkedList<>();
        results.add(firstResult);
    }

    public String getResult(int index){
        return results.get(index);
    }

    public List<String> getResults() {
        return results;
    }

    public void addResult(String result){
        results.add(result);
    }

    public int groupCount(){
        return results.size() ;
    }

    @Override
    public SessionMessage message() {
        return message ;
    }
}
