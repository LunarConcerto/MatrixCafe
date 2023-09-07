package cafe.lunarconcerto.matrixcafe.api.responder;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import com.google.inject.Singleton;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author LunarConcerto
 */
@Singleton
public final class ResponderRegistry extends ResponderGroup<InterceptionResponder>{

    private final Map<String, InterceptionResponder> idToResponderMap ;

    private final Map<Class<?>, ResponderGroup<?>> responderToResponderGroupList;

    public ResponderRegistry() {
        idToResponderMap = new ConcurrentHashMap<>();
        responderToResponderGroupList = new ConcurrentHashMap<>();
    }

    public InterceptionResponder find(String id){
        return idToResponderMap.get(id);
    }

    /**
     * 注册一个响应器,
     * 响应器被注册以后, 即可收到消息.
     * @param responder 目标响应器
     */
    public void register(Responder responder){
        InterceptionResponder wrapper = InterceptionResponder.wrap(responder);
        idToResponderMap.put(wrapper.getId(), wrapper);

        ResponderGroup<?> group = responderToResponderGroupList.computeIfAbsent(responder.getClass(), aClass -> responder.createGroup());
        group.addUnsafe(responder);
    }

    /**
     * 取消某响应器的注册,
     * 响应器被取消注册以后, 则被解除引用, 并不再接收消息.
     * @param responder 目标响应器
     */
    public void unregister(@NotNull Responder responder){
        idToResponderMap.remove(responder.getId());

        ResponderGroup<?> group = responderToResponderGroupList.computeIfAbsent(responder.getClass(), aClass -> responder.createGroup());
        group.removeUnsafe(responder);
    }

    /**
     * 不支持直接取用响应器列表.
     * @return 空集合.
     */
    @Contract(pure = true)
    @Override
    public @NotNull @Unmodifiable Collection<InterceptionResponder> targets() {
        return Collections.emptyList();
    }

    @Contract(pure = true)
    @Override
    public @NotNull MatchResult match(SessionMessage message) {
        for (Map.Entry<Class<?>, ResponderGroup<?>> entry : responderToResponderGroupList.entrySet()) {
            MatchResult result = entry.getValue().match(message);
            if (result.isOk()){
                return result ;
            }
        }
        return MatchResult.FAILURE ;
    }

    @Override
    public void add(InterceptionResponder responder) {
        register(responder);
    }

    @Override
    public void remove(InterceptionResponder responder) {
        unregister(responder);
    }
}
