package cafe.lunarconcerto.matrixcafe.api.bot;

import cafe.lunarconcerto.matrixcafe.api.util.Strings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LunarConcerto
 * @time 2023/9/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotConfig {

    /**
     * Bot 的ID
     * 用于索引
     */
    private String id = Strings.EMPTY;

    /**
     * 目标适配器.
     * <p>
     * 若不指定, 则分配给任意适配器.
     */
    private String targetAdapter = Strings.EMPTY ;

    /**
     * Bot 的昵称
     * <p>
     * 在某些场合下有用.
     */
    private String nickname = Strings.EMPTY;

    /**
     * Bot 处理预定任务的间隔 (秒)
     */
    private float updateInterval = 60;

    /**
     * 启用自动任务处理.
     * <p>
     * 启用自动任务处理的情况下, Bot 在指定时间运行某些给定的任务
     * @see cafe.lunarconcerto.matrixcafe.api.bot.BotAction
     */
    private boolean enableBotAction = false ;

}
