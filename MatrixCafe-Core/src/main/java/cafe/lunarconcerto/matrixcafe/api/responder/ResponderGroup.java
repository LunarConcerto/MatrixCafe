package cafe.lunarconcerto.matrixcafe.api.responder;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * 响应器组
 * 用以管理一组同类型的响应器
 * @author LunarConcerto
 * @time 2023/6/2
 */
@Slf4j
public abstract class ResponderGroup<ResponderType extends Responder> implements Comparable<ResponderGroup<ResponderType>> {

    protected int priority = 0 ;

    public abstract Collection<ResponderType> targets();

    public abstract MatchResult match(SessionMessage message);

    public abstract void add(ResponderType responder);

    public abstract void remove(ResponderType responder);

    @SuppressWarnings("unchecked")
    public void addUnsafe(Responder responder){
        try {
            add((ResponderType) responder);
        }catch (ClassCastException e){
            log.error("响应器组 {} 收到类型不正确的响应器: {} ", this.getClass().getName(), responder.getClass().getName());
        }
    }

    @SuppressWarnings("unchecked")
    public void removeUnsafe(Responder responder){
        try {
            remove((ResponderType) responder);
        } catch (ClassCastException e){
            log.error("响应器组 {} 收到类型不正确的响应器: {} ", this.getClass().getName(), responder.getClass().getName());
        }
    }

    public ResponderGroup<ResponderType> setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(@NotNull ResponderGroup<ResponderType> o) {
        return o.getPriority() - getPriority();
    }

}
