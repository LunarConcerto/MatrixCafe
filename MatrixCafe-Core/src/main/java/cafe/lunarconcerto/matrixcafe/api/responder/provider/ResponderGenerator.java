package cafe.lunarconcerto.matrixcafe.api.responder.provider;

import cafe.lunarconcerto.matrixcafe.api.responder.Responder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ResponderGenerator<T extends Responder> {

    Set<Annotation> getSupportedAnnotations();

    T generate(Method respondMethod);

    boolean isValidate(Method target);

}
