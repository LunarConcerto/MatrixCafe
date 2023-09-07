package cafe.lunarconcerto.matrixcafe.api.responder.command;

import cafe.lunarconcerto.matrixcafe.api.data.message.SessionMessage;
import cafe.lunarconcerto.matrixcafe.api.responder.action.DefaultActionData;
import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 *
 * @author LunarConcerto
 * @time 2023/6/12
 */
public class CommandActionData extends DefaultActionData {

    public enum DissatisfyReason {

        /**
         * 参数少于需求的最少参
         */
        ARGUMENT_TOO_SHORT,

        /**
         * 参数多于需求的最多参
         */
        ARGUMENT_TOO_LONG,

        /**
         * 格式错误, 一般是参数选项未获取到参数
         */
        PATTERN_ERROR,

        NONE

    }

    private List<String> argumentList ;

    private Map<String, String> optionMap;

    private boolean dissatisfy = false ;

    private DissatisfyReason reason = DissatisfyReason.NONE;

    @Contract("_ -> new")
    public static @NotNull CommandActionData empty(SessionMessage message){
        return new CommandActionData(message);
    }

    public static @NotNull CommandActionData dissatisfy(SessionMessage message){
        return new CommandActionData(message).withDissatisfy(DissatisfyReason.NONE);
    }

    public static @NotNull CommandActionData dissatisfy(SessionMessage message, DissatisfyReason reason){
        return new CommandActionData(message).withDissatisfy(reason);
    }

    public static CommandActionData withArgumentData(SessionMessage message){
        return new CommandActionData(message).withArgument();
    }

    public static CommandActionData withOptionData(SessionMessage message){
        return new CommandActionData(message).withOption();
    }

    public static CommandActionData withArgumentAndOptionData(SessionMessage message){
        return new CommandActionData(message).withArgument().withOption();
    }

    private CommandActionData(SessionMessage message) {
        super(message);
        optionMap = new HashMap<>(1);
    }

    private CommandActionData withArgument(){
        argumentList = new ArrayList<>(1);
        return this;
    }

    private CommandActionData withOption(){
        optionMap = new HashMap<>(1);
        return this;
    }

    private CommandActionData withDissatisfy(DissatisfyReason reason){
        dissatisfy = true ;
        this.reason = reason;
        return this;
    }

    public CommandActionData addArgument(String arg){
        argumentList.add(arg);
        return this;
    }

    public CommandActionData addOption(String opt){
        optionMap.put(opt, Strings.EMPTY);
        return this;
    }

    public CommandActionData addOption(String opt, String arg){
        optionMap.put(opt, arg);
        return this;
    }

    public Optional<String> getArgument(int index){
        return argumentList.size() > index ? Optional.ofNullable(argumentList.get(index)) : Optional.empty();
    }

    public Optional<String> getOption(String optionID){
        return Optional.ofNullable(optionMap.get(optionID));
    }

    public boolean hasOption(String optionID){
        return optionMap.get(optionID) != null;
    }

    public boolean hasArgument(){
        return argumentList != null && argumentList.size() > 0;
    }

    public boolean hasOption(){
        return optionMap != null && optionMap.size() > 0;
    }

    public int getArgumentLength(){
        if (hasArgument()) return argumentList.size();
        else return 0;
    }

    public int getOptionLength(){
        if (hasOption()) return optionMap.size() ;
        else return 0;
    }

    public boolean isDissatisfy() {
        return dissatisfy;
    }

    public DissatisfyReason getDissatisfyReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "CommandActionData{" +
                "argumentList=" + argumentList +
                ", optionMap=" + optionMap +
                ", dissatisfy=" + dissatisfy +
                ", reason=" + reason +
                '}';
    }
}
