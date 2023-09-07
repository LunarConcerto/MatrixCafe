package cafe.lunarconcerto.matrixcafe.api.responder.provider;

import java.util.LinkedList;
import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/7/6
 */
public class ResponderGeneratorManager {

    private final List<ResponderGenerator<?>> responderGeneratorList ;

    public ResponderGeneratorManager() {
        responderGeneratorList = new LinkedList<>();
    }

    public void register(ResponderGenerator<?> generator){

    }

    public void parseProvider(ResponderProvider provider){

    }

}
