package cafe.lunarconcerto.matrixcafe.api.schedule;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;

public final class CronHelper {

    private CronHelper() { }

    @Contract(" -> new")
    public static @NotNull CronBuilder newCronBuilder(){
        return CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));
    }

    /**
     * 每 n 秒执行一次作业
     * @param seconds n
     * @return cron 表达式
     */
    public static String everySeconds(int seconds){
        return newCronBuilder()
                .withYear(always())
                .withDoM(questionMark())
                .withMonth(always())
                .withDoW(always())
                .withHour(always())
                .withMinute(always())
                .withSecond(every(seconds))
                .instance()
                .asString();
    }

    /**
     * 每 n 分钟执行一次作业
     * @param minute n
     * @return cron 表达式
     */
    public static String everyMinute(int minute){
        return newCronBuilder()
                .withYear(always())
                .withDoM(questionMark())
                .withMonth(always())
                .withDoW(always())
                .withHour(always())
                .withMinute(every(minute))
                .withSecond(on(0))
                .instance()
                .asString();
    }

    /**
     * 每 n 小时执行一次作业
     * @param hour n
     * @return cron 表达式
     */
    public static String everyHour(int hour){
        return newCronBuilder()
                .withYear(always())
                .withDoM(questionMark())
                .withMonth(always())
                .withDoW(always())
                .withHour(every(hour))
                .withMinute(on(0))
                .withSecond(on(0))
                .instance()
                .asString();
    }

    /**
     * 现实时间的每天的特定时分执行一次作业,
     * 例:
     *      specificTimeEveryDay(10, 20) => 每天上午的 10点20分执行任务
     *      specificTimeEveryDay(20, 59) => 每天下午的 08点59分执行任务
     *      specificTimeEveryDay(0, 0)   => 每天凌晨的 12点00分执行任务
     * @param hour 范围 0 <= n < 24 的值
     * @param minute 范围 0 <= m < 60 的值
     * @return cron 表达式
     */
    public static String specificTimeEveryDay(int hour, int minute){
        String a = "0 0 0 * * ?";
        return newCronBuilder()
                .withYear(always())
                .withDoM(questionMark())
                .withMonth(always())
                .withDoW(always())
                .withHour(on(hour))
                .withMinute(on(minute))
                .withSecond(on(0))
                .instance()
                .asString();
    }

}
