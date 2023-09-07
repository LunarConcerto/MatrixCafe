package cafe.lunarconcerto.matrixcafe.api.common.intercept;

import java.util.Collection;


public interface Interception<P, R> {

    Collection<Interceptor<P, R>> interceptors();

    default void addInterceptor(Interceptor<P, R> interceptor) {
        interceptors().add(interceptor);
    }

    default void addAllInterceptor(Collection<Interceptor<P, R>> interceptors) {
        interceptors().addAll(interceptors);
    }

    default void removeInterceptor(Interceptor<P, R> interceptor) {
        interceptors().remove(interceptor);
    }

    default void removeAllInterceptor(Collection<Interceptor<P, R>> interceptors) {
        interceptors().removeAll(interceptors);
    }

}
