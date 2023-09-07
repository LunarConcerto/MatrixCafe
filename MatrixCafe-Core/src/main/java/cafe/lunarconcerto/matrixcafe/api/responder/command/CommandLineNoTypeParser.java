package cafe.lunarconcerto.matrixcafe.api.responder.command;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * <h1>命令行风格进行解析</h1>
 * 一个标准的命令如下所示:
 * <p>
 * ①keyword  ②arg1  ③arg2 ④--opt1 ⑤-opt2 ⑥-opt3 ⑦foo
 * <p>
 * ① 关键字
 * ② 参数1
 * ③ 参数2
 * ④ 布尔选项1
 * ⑤ 布尔选项2
 * ⑥ 带参选项1
 * ⑦ 带参选项参数
 * @author LunarConcerto
 * @time 2023/5/19
 */
public class CommandLineNoTypeParser implements CommandParser{

    /**
     * 最少参
     */
    private int minArgLength = 0;

    /**
     * 最多参
     */
    private int maxArgLength = 0;

    /**
     * 选项
     */
    private Map<String ,Option> optionMap;

    public CommandLineNoTypeParser(Option... options) {
        this(0, 0, options);
    }

    public CommandLineNoTypeParser(int minArgLength, Option... options) {
        this(minArgLength, minArgLength, options);
    }

    public CommandLineNoTypeParser(int minArgLength, int maxArgLength, Option @NotNull ... options) {
        this.minArgLength = minArgLength;
        this.maxArgLength = Math.max(maxArgLength, minArgLength);

        if (options.length > 0){
            this.optionMap = new HashMap<>();
            initializeOptionMap(options);
        }
    }

    private void initializeOptionMap(Option @NotNull [] options){
        for (Option option : options) {
            for (String keyword : option.getKeywords()) {
                optionMap.put(keyword, option);
            }
        }
    }

    @Override
    public CommandActionData parse(@NotNull SessionMessage message) {
        String text = message.getTextContents();
        String[] splitTexts = text.split(" +");
        int length = splitTexts.length;

        System.out.println("Source Text:" + text);

        // 长度等于1时, 若最少参为 0 可通过 , 否则不通过.
        if (length == 1){
            return minArgLength == 0 ? CommandActionData.empty(message) : CommandActionData.dissatisfy(message);
        }

        int argLength = 0, optLength = 0;

        // 先遍历一遍求出参数长度和选项长度
        for (int i = 1 ; i < splitTexts.length; i++) {
            String splitText = splitTexts[i];
            if (isOption(splitText)){
                optLength = splitTexts.length - i;
                break;
            }else {
                argLength++;
            }
        }

        System.out.println("argLength: " +argLength);
        System.out.println("optLength: " +optLength);

        // 校验参数长度.
        if (argLength < minArgLength){
            return CommandActionData.dissatisfy(message, CommandActionData.DissatisfyReason.ARGUMENT_TOO_SHORT);
        }

        if (argLength > maxArgLength){
            return CommandActionData.dissatisfy(message, CommandActionData.DissatisfyReason.ARGUMENT_TOO_LONG);
        }

        CommandActionData data = createActionData(message, argLength, optLength);

        Arrays.stream(Arrays.copyOfRange(splitTexts, 1, argLength+1))
                .forEachOrdered(data::addArgument);

        return optLength > 0 ? parse(Arrays.copyOfRange(splitTexts, argLength + 1, splitTexts.length), data) :
                data;
    }

    @Contract(pure = true)
    private CommandActionData parse(String @NotNull [] optionList, CommandActionData actionData){
        for (int i = 0; i < optionList.length ; i++) {
            String optionText = optionList[i];
            switch (optionType(optionText)){
                case ARGUMENT -> {
                    if (i+1 < optionList.length && !isOption(optionText)){
                        actionData.addOption(optionID(optionText), optionList[i+1]);
                        i++;
                    }
                    else {
                        return CommandActionData.dissatisfy(actionData.message(), CommandActionData.DissatisfyReason.PATTERN_ERROR);
                    }
                }

                case BOOLEAN -> actionData.addOption(optionID(optionText));
            }
        }

        return actionData ;
    }

    private CommandActionData createActionData(SessionMessage message, int argLength, int optLength){
        if (argLength > 0 && optLength > 0){
            return CommandActionData.withArgumentAndOptionData(message);
        }

        if (argLength > 0 && optLength == 0){
            return CommandActionData.withArgumentData(message);
        }

        if (argLength == 0 && optLength > 0){
            return CommandActionData.withOptionData(message);
        }

        return CommandActionData.dissatisfy(message);
    }

    private boolean isOption(String text){
        if (optionMap == null || optionMap.isEmpty()) return false ;
        return optionMap.get(text) != null ;
    }

    private OptionType optionType(String text){
        Option option = optionMap.get(text);
        return option == null ? OptionType.NONE : option.getType();
    }

    private String optionID(String text){
        return optionMap.get(text).getOptionId();
    }

}
