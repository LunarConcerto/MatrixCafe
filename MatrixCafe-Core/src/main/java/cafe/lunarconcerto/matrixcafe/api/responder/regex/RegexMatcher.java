package cafe.lunarconcerto.matrixcafe.api.responder.regex;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LunarConcerto
 * @time 2023/6/2
 */
public class RegexMatcher {

    private Pattern pattern ;

    private String regex ;

    public RegexMatcher() { }

    @Contract(pure = true)
    public RegexMatcher(@NotNull Pattern pattern) {
        this.pattern = pattern;
        this.regex = pattern.pattern();
    }

    @Contract("_ -> new")
    public static @NotNull RegexMatcher of(@Language("RegExp") String regex){
        return new RegexMatcher(Pattern.compile(regex));
    }

    public Optional<MatchGroup> match(@NotNull SessionMessage message){
        Matcher matcher = pattern.matcher(message.getTextContents());
        if (matcher.find()){
            MatchGroup group = new MatchGroup(message, matcher.group());
            while (matcher.find()){
                group.addResult(matcher.group());
            }
            return Optional.of(group);
        }
        else {
            return Optional.empty();
        }
    }

    public RegexMatcher regex(@NotNull Pattern pattern){
        this.pattern = pattern ;
        this.regex = pattern.pattern() ;
        return this;
    }

    public RegexMatcher regex(@Language("RegExp") String regex){
        this.pattern = Pattern.compile(regex);
        this.regex = regex;
        return this ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegexMatcher that = (RegexMatcher) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(regex);
    }
}
