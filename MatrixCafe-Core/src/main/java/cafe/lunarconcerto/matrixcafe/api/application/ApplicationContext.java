package cafe.lunarconcerto.matrixcafe.api.application;

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
     * @param name 目标 bean 名称
     */
    <T> Optional<T> get(String name);

    /**
     * 查找符合类型的 bean
     * @param type 目标 bean 类型
     */
    <T> Optional<T> get(Class<T> type);

    /**
     * 查找是否存在对应名称的 bean
     * @param name 目标 bean 名称
     */
    boolean has(String name);

    boolean has(Class<?> type);

    ApplicationContext add(Object object);

    ApplicationContext add(String name, Object object);

    ConfigurationManager configurationManager();

    Database database();

    /**
     * 指定某Bean在IOC中的名称
     */
    @Documented
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface InApplicationContextName {

        String value();

    }

}
