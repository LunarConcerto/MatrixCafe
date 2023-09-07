package cafe.lunarconcerto.matrixcafe.api.web;

import cafe.lunarconcerto.matrixcafe.api.data.event.SystemEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/6/16
 */
public class CollectHttpApiEvent extends SystemEvent {

    private final List<HttpApi> httpApiList;

    public CollectHttpApiEvent() {
        httpApiList = new ArrayList<>();
    }

    public void add(HttpApi httpApi){
        httpApiList.add(httpApi);
    }

    @Override
    public void consume() {
        // 不中断传递.
    }

    List<HttpApi> getHttpApiList() {
        super.consume();
        return httpApiList;
    }
}
