package cafe.lunarconcerto.matrixcafe.api.web;

import cafe.lunarconcerto.matrixcafe.api.application.Bus;
import cafe.lunarconcerto.matrixcafe.api.web.http.HttpServer;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author LunarConcerto
 * @time 2023/6/14
 */
@Singleton
public class HttpApiManager {

    private final List<HttpApi> httpApiList ;

    public HttpApiManager() {
        httpApiList = new ArrayList<>();

        Bus.registerSystemEventListener(this);
    }

    @Subscribe
    public void httpApiRegisterEventListener(@NotNull HttpApiRegisterEvent httpApiRegisterEvent){
        Collections.addAll(httpApiList, httpApiRegisterEvent.getHttpApis());
    }

    public void collectHttpApi(){
        CollectHttpApiEvent event = new CollectHttpApiEvent();
        Bus.postSystemEvent(event);

        httpApiList.addAll(event.getHttpApiList());
    }

    public void registerToServer(@NotNull HttpServer server){
        server.registerAPI(httpApiList);
    }

}
