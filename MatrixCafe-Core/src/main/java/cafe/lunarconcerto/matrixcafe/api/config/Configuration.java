package cafe.lunarconcerto.matrixcafe.api.config;

import java.lang.annotation.*;

/**
 * <h1>配置注解</h1>
 * 声明一个类为配置类
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {

    /**
     * 声明该配置保存时使用的文件名
     *
     * @return 包含拓展名的可用文件名字符串值
     */
    String value();

}
