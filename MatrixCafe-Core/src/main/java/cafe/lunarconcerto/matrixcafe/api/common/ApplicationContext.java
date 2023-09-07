package cafe.lunarconcerto.matrixcafe.api.common;

import cafe.lunarconcerto.matrixcafe.api.db.Database;
import cafe.lunarconcerto.matrixcafe.api.config.ConfigurationManager;
import cafe.lunarconcerto.matrixcafe.api.plugin.Plugin;

import java.lang.annotation.*;
import java.util.Optional;

/**
 * <h1>应用程序上下文</h1>
 * 可以理解为 MatrixCafe 的 Ioc 接口.
 * 将会在运行时为任何继承了插件基类 {@link Plugin} 的类提供.
 */
public interface ApplicationContext {

    /**
     * 按名查找任意 bean .
     * @param name 该 bean 的名.
     */
    <T> Optional<T> get(String name);

    <T> Optional<T> get(Class<T> type);

    boolean has(String name);

    boolean has(Class<?> type);

    ApplicationContext add(Object object);

    ApplicationContext add(String name, Object object);

    ConfigurationManager configurationManager();

    Database database();

    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface InApplicationContextName {

        String value();

    }

}
