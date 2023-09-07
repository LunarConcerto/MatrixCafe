package cafe.lunarconcerto.matrixcafe.api.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class Times {

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Field
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static final SimpleDateFormat toSecondFormatter =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat toDayFormatter =
            new SimpleDateFormat("yyyy-MM-dd");

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Container
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    private Times() {
    }

    /* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * ===================================================================================================================
     * Method
     * ===================================================================================================================
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

    public static @NotNull String format(long time) {

        return toSecondFormatter.format(time);
    }

    public static @NotNull String format(Date time) {
        return toSecondFormatter.format(time);
    }

    public static @NotNull String formattedNowTime() {
        return toSecondFormatter.format(new Date());
    }

    /**
     * 格式化为[HH小时-mm分]
     *
     * @param milliseconds 毫秒值
     * @return String
     */
    public static String formatMs(long milliseconds) {
        return String.format("%d小时-%d分",

                TimeUnit.MILLISECONDS.toHours(milliseconds),
                (TimeUnit.MILLISECONDS.toMinutes(milliseconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)))
        );
    }

    public static int gap(@NotNull Date oldDay, @NotNull Date newDay) {
        return millisecondsToDay((newDay.getTime() - oldDay.getTime()));
    }

    public static int gap(long oldDay, long newDay) {
        return gap(new Date(oldDay), new Date(newDay));
    }

    public static int millisecondsToDay(long millisecond) {
        return Math.toIntExact(millisecond / 1000 / 60 / 60 / 24);
    }

}
