package cafe.lunarconcerto.matrixcafe.api.responder;

import cafe.lunarconcerto.matrixcafe.api.application.intercept.Interception;
import cafe.lunarconcerto.matrixcafe.api.application.intercept.Interceptor;
import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.data.response.ProtocolData;
import cafe.lunarconcerto.matrixcafe.api.data.response.Response;
import cafe.lunarconcerto.matrixcafe.api.responder.action.ActionParam;
import cafe.lunarconcerto.matrixcafe.api.responder.descriptor.ResponderDescriptor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 可拦截的响应器
 * 是在所有响应器之上的一层封装, 使得响应器的输入和输出都可以被拦截.
 */
public final class InterceptionResponder extends Responder implements Interception<SessionMessage, ProtocolData<?>> {

    private final Responder responder ;

    private boolean active = true ;

    private final List<Interceptor<SessionMessage, ProtocolData<?>>> interceptors ;

    @Contract("_ -> new")
    public static @NotNull InterceptionResponder wrap(final @NotNull Responder responder){
        return new InterceptionResponder(responder);
    }
    private InterceptionResponder(final @NotNull Responder responder) {
        super(responder.getId());
        this.responder = responder;
        interceptors = new LinkedList<>();
    }

    @Contract(pure = true)
    @Override
    public @Nullable ResponderGroup<?> createGroup() {
        return responder.createGroup() ;
    }

    @Override
    public Response<?> respond(@NotNull ActionParam data) {
        if (beforeIntercept(data.message())) return Response.FAILURE ;

        Response<?> result = responder.respond(data);

        if (result.isProtocolResponse()) afterIntercept((ProtocolData<?>) result.getData());
        return result ;
    }


    private boolean beforeIntercept(SessionMessage message){
        return interceptors.stream().allMatch(interceptor -> interceptor.beforeAction(message));
    }

    private void afterIntercept(ProtocolData<?> protocolData){
        interceptors.forEach(interceptor -> interceptor.afterAction(protocolData));
    }

    @Override
    @Contract(pure = true)
    public @NotNull Collection<Interceptor<SessionMessage, ProtocolData<?>>> interceptors() {
        return this.interceptors ;
    }

    @Override
    public void addInterceptor(Interceptor<SessionMessage, ProtocolData<?>> interceptor) {
        interceptors.add(interceptor);
    }

    @Override
    public void addAllInterceptor(Collection<Interceptor<SessionMessage, ProtocolData<?>>> interceptors) {
        this.interceptors.addAll(interceptors);
    }

    @Override
    public void removeInterceptor(Interceptor<SessionMessage, ProtocolData<?>> interceptor) {
        interceptors.remove(interceptor);
    }

    @Override
    public void removeAllInterceptor(Collection<Interceptor<SessionMessage, ProtocolData<?>>> interceptors) {
        this.interceptors.removeAll(interceptors);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean contains(Responder responder) {
        if (this.responder == responder) return true;
        if (responder == null || this.responder.getClass() != responder.getClass()) return false;
        InterceptionResponder other = (InterceptionResponder) responder;
        return Objects.equals(this.responder, other.responder);
    }

    @Override
    public int getPriority() {
        return responder.getPriority() ;
    }

    @Override
    public ResponderDescriptor getDescriptor() {
        return responder.getDescriptor();
    }

}
