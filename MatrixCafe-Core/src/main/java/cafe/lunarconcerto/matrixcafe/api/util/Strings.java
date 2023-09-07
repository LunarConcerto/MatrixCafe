package cafe.lunarconcerto.matrixcafe.api.util;

import org.apache.commons.lang3.tuple.Pair;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Strings {

    public static final String EMPTY = "";

    public static final String BLANK = " ";

    public static final String[] EMPTY_ARRAY = {} ;

    public static final String UNDEFINED = "undefined" ;

    public static final Pair<String, String> EMPTY_PAIR = Pair.of(EMPTY, EMPTY);

    public static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Language("RegExp")
    public static final String URL_REGEX = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?+-=\\\\.&]*)" ;

    /**
     * Windows系统上的文件名非法字符
     */
    public static final String[] INVALID_CHAR = {
            "\\", "/", ":", "*", "?", "\"", "<", ">", "|"
    };

    private Strings() { }

    /**
     * 检查字符串是否是一个合法的字符串,
     * 合法字符串的标准: 非Null, 非空, 非空格;
     * @param string 目标字符串
     * @return 当字符串包含可用字符时返回 true
     */
    public static boolean isValid(@Nullable String string) {
        return string != null && !string.isEmpty() && !string.isBlank();
    }

    /**
     * 检查字符串是否是一个非法的字符串。
     * 非法字符串的标准: 是Null, 空值, 或者仅包含空格;
     * @param string 目标字符串
     * @return 当字符串不包含任何可用字符时返回 true
     */
    public static boolean isInvalid(@Nullable String string) {
        return !isValid(string);
    }

    /**
     * 移除字符串中的第一个字符
     * @param string 目标字符串
     * @return 若目标字符串长度为1, 返回空字符串, 否则返回移除了第一个字符的新字符串
     */
    @Contract(pure = true)
    public static @NotNull String removeFirst(@NotNull String string){
        return string.length() <= 1 ?
                EMPTY :
                string.substring(1);
    }

    /**
     * 移除字符串中的最后一个字符
     * @param string 目标字符串
     * @return 若目标字符串长度为1, 返回空字符串, 否则返回移除了最后一个字符的新字符串
     */
    public static @NotNull String removeLast(@NotNull String string){
        return string.length() <= 1 ?
                EMPTY :
                string.substring(0, string.length()-1);
    }

    /**
     * 移除一个字符串中的首尾字符
     * @param string 目标字符串
     * @return 若目标字符串长度为2, 返回空字符串, 否则返回移除了首尾字符的新字符串
     */
    public static @NotNull String removeFirstAndLast(@NotNull String string){
        return string.length() <= 2 ?
                EMPTY :
                string.substring(1, string.length() - 1);
    }

    @Contract("_, _ -> new")
    public static @NotNull Pair<String, String> splitOn(@NotNull String string, int index){
        if (string.length() == 0) return EMPTY_PAIR;

        if (index >= string.length()-1){
            return Pair.of(string, EMPTY);
        }else if (index <= 0){
            return Pair.of(EMPTY, string);
        }else {
            return Pair.of(string.substring(0, index), string.substring(index+1));
        }
    }

    /**
     * 提取字符串中所含的URL
     * @param text 源文本
     * @return URL 列表
     */
    public static @NotNull List<String> extractUrls(String text) {
        List<String> containedUrls = new LinkedList<>();
        Pattern pattern = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find()) {
            containedUrls.add(text.substring(
                    urlMatcher.start(0),
                    urlMatcher.end(0))
            );
        }

        return containedUrls;
    }

    /**
     * 提取字符串中所含的第一个字符串
     * @param text 源文本
     * @return URL
     */
    public static Optional<String> extractUrl(String text) {
        List<String> urls = extractUrls(text);
        return urls.isEmpty() ? Optional.empty() : Optional.of(urls.get(0));
    }

}
