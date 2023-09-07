package cafe.lunarconcerto.matrixcafe.api.responder.provider.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记一个方法为响应方法
 * @author LunarConcerto
 * @time 2023/7/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Responds { }
