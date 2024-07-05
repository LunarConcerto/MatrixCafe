package cafe.lunarconcerto.matrixcafe.impl;

import cafe.lunarconcerto.matrixcafe.api.application.MessageResolver;
import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.data.response.Response;
import cafe.lunarconcerto.matrixcafe.api.data.session.SessionManager;
import cafe.lunarconcerto.matrixcafe.api.responder.InterceptionResponder;
import cafe.lunarconcerto.matrixcafe.api.responder.MatchResult;
import cafe.lunarconcerto.matrixcafe.api.responder.ResponderRegistry;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Singleton
@Deprecated
public class MatrixCafeMessageResolver implements MessageResolver {

    @Inject
    private ResponderRegistry responderRegistry;

    private final ExecutorService executorService ;

    private final SessionManager sessionManager ;

    public MatrixCafeMessageResolver() {
        sessionManager = new MatrixCafeSessionManager();
        executorService = Executors.newFixedThreadPool(4);
    }

    @Override
    public void resolveMessage(Message message) {
        SessionMessage sessionMessage = sessionManager.warp(message);
        try {
            executorService.submit(() -> {
                        MatchResult result = responderRegistry.match(sessionMessage);
                        if (result.isOk()) {
                            return callResponder(result);
                        }
                        return Response.FAILURE ;
                    })
                    .get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("消息响应失败:", e);
        }
    }

    @Contract(pure = true)
    private @Nullable Response<?> callResponder(@NotNull MatchResult matchResult){
        if (!matchResult.haveTarget()) {
            return null;
        }

        InterceptionResponder responder = responderRegistry.find(matchResult.matchedResponderId());
        return responder.respond(matchResult.data());
    }

}
