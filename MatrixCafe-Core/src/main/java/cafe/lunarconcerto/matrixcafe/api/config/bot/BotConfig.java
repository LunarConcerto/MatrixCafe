package cafe.lunarconcerto.matrixcafe.api.config.bot;

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
     * 注: Bot 唯一ID
     */
    private String id;

    /**
     * 注: 在对应协议平台中显示的名称
     */
    private String nickname;

    /**
     * 注: 在系统中显示的名称
     */
    private String systemName;

    /**
     * 注: Bot 处理预定任务的间隔
     */
    private float updateInterval;

}
