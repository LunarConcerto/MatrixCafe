package cafe.lunarconcerto.matrixcafe.api.responder.command;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LunarConcerto
 * @time 2023/6/13
 */
public class Option {

    private OptionType type ;

    private String optionId ;

    private Set<String> keywords ;

    @Contract("_, _ -> new")
    public static @NotNull Option booleanOption(String optionId, String... keywords){
        return new Option(OptionType.BOOLEAN, optionId, keywords);
    }

    public static @NotNull Option argumentOption(String optionId, String... keywords){
        return new Option(OptionType.ARGUMENT, optionId, keywords);
    }

    public Option(OptionType type, String optionId, String... keywords) {
        this.type = type;
        this.optionId = optionId;
        this.keywords = new HashSet<>(List.of(keywords));
    }

    public boolean isBoolean(){
        return type == OptionType.BOOLEAN ;
    }

    public boolean isKey(String text){
        return keywords.contains(text);
    }

    public OptionType getType() {
        return type;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public String getOptionId() {
        return optionId;
    }
}
