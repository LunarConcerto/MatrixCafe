package cafe.lunarconcerto.matrixcafe.api.web;

import cafe.lunarconcerto.matrixcafe.api.data.event.SystemEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author LunarConcerto
 * @time 2023/6/16
 */
public class HttpApiRegisterEvent extends SystemEvent {

    private final HttpApi[] httpApis ;

    public HttpApiRegisterEvent(HttpApi... httpApis) {
        this.httpApis = httpApis;
    }

    public HttpApiRegisterEvent(@NotNull Collection<HttpApi> httpApis) {
        HttpApi[] apis = new HttpApi[httpApis.size()];
        int index = 0;
        for (HttpApi api : httpApis) {
            apis[index] = api;
        }

        this.httpApis = apis;
    }

    public HttpApi[] getHttpApis() {
        return httpApis;
    }
}
