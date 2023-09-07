package cafe.lunarconcerto.matrixcafe.api.config;

import org.jetbrains.annotations.NotNull;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(@NotNull ExceptionType type, String args){
        super(type.of(args));
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public enum ExceptionType {

        NO_NAME_STATEMENT_CONFIG_TYPE("类型 %s 不包含名为 @Configuration 的注解, 或指定了不合法的字符串.");

        final String message ;

        ExceptionType(String message) {
            this.message = message;
        }

        public String of(String text){
            return message.formatted(text);
        }
    }
}
