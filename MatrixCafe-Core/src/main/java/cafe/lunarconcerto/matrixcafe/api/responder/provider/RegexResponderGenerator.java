package cafe.lunarconcerto.matrixcafe.api.responder.provider;

import cafe.lunarconcerto.matrixcafe.api.data.message.Message;
import cafe.lunarconcerto.matrixcafe.api.data.message.content.MessageContent;
import cafe.lunarconcerto.matrixcafe.api.responder.Responder;
import cafe.lunarconcerto.matrixcafe.api.responder.provider.annotations.Regex;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class RegexResponderGenerator {

    public List<Responder> generate(@NotNull ResponderProvider provider) {
        Method[] methods = provider.getClass().getMethods();

        List<Responder> results = new LinkedList<>();
        for (Method method : methods) {
            if (isApplicableMethod(method)){

            }
        }
        return null;
    }

    private boolean isApplicableMethod(@NotNull Method method){
        /* 需要有注解 */
        Regex regexAnnotation = method.getAnnotation(Regex.class);
        if (regexAnnotation == null) return false ;

        /* 返回值类型必须为 String 或 MessageContent 或 Void */
        Class<?> returnType = method.getReturnType();
        boolean returnTypeIsStringOrMessageContentOrVoid = returnType.equals(String.class) || MessageContent.class.isAssignableFrom(returnType) || returnType.equals(Void.class);
        if (!returnTypeIsStringOrMessageContentOrVoid) return false ;

        /* 参数类型需要为空或是 Message */
        Parameter[] parameters = method.getParameters();
        boolean flagNoParam = parameters == null || parameters.length == 0 ;
        if (flagNoParam) return true ;

        return parameters.length == 1 && parameters[0].getType().equals(Message.class);
    }

}
