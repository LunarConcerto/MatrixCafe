package cafe.lunarconcerto.matrixcafe.api.config;

import java.lang.annotation.*;

/**
 * <h1>是否更新配置文件</h1>
 * 声明一个配置类的对象是否应该被被动保存.
 * 被动保存是指类似于程序退出时的自动保存事件.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigurationShouldSave {

    boolean value() default true ;

}
