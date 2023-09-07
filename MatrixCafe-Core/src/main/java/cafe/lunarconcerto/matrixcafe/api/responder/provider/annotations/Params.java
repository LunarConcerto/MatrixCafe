package cafe.lunarconcerto.matrixcafe.api.responder.provider.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 绑定响应方法中的参数
 * @author LunarConcerto
 * @time 2023/7/6
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Params { }