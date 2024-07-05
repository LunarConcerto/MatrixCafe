package cafe.lunarconcerto.matrixcafe.api.web.http;

import cafe.lunarconcerto.matrixcafe.api.config.HttpServerInfo;
import cafe.lunarconcerto.matrixcafe.api.config.MatrixCafeConfiguration;
import cafe.lunarconcerto.matrixcafe.api.web.HttpApi;
import io.javalin.Javalin;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@Slf4j
public class HttpServer {

    private final Javalin app;

    private final HttpServerInfo serverInfo ;

    public HttpServer(@NotNull MatrixCafeConfiguration configuration) {
        serverInfo = configuration.getHttpServerInfo();
        app = Javalin.create();
    }

    public void start(){
        app.start(serverInfo.getPort());
    }

    public void registerAPI(@NotNull Collection<HttpApi> httpApis){
        for (HttpApi httpApi : httpApis) {
            registerAPI(httpApi);
        }
    }

    public void registerAPI(@NotNull HttpApi api){
        switch (api.getMethod()){
            case GET -> app.get(api.getPath(), api.getHandler());
            case HEAD -> app.head(api.getPath(), api.getHandler());
            case POST -> app.post(api.getPath(), api.getHandler());
            case PUT -> app.put(api.getPath(), api.getHandler());
            case PATCH -> app.patch(api.getPath(), api.getHandler());
            case DELETE -> app.delete(api.getPath(), api.getHandler());
            case OPTIONS -> app.options(api.getPath(), api.getHandler());
            case NONE -> {
                log.warn("无效的 Http api 定义 -> {}", api.getName());
            }
        }
    }

}
