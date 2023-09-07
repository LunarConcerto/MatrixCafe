package cafe.lunarconcerto.matrixcafe.api.responder.provider.annotations;

import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import org.intellij.lang.annotations.Language;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Regex {

    @Language("RegExp")
    String regex();

    String description() default Strings.EMPTY ;

}
