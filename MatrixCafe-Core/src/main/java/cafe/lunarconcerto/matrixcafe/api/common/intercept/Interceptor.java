package cafe.lunarconcerto.matrixcafe.api.common.intercept;

public abstract class Interceptor<V, R> {

    /**
     * 指示该拦截器是否为单例
     * @return 若为 true, 系统只保留一份该类的实例并为所有可拦截类共享.
     */
    public boolean isSingleton(){
        return false ;
    }

    public abstract boolean beforeAction(V value);

    public abstract void afterAction(R result);

}
