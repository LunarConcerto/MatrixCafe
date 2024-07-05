package cafe.lunarconcerto.matrixcafe.api.schedule;

import cafe.lunarconcerto.matrixcafe.api.application.Bus;
import cafe.lunarconcerto.matrixcafe.api.data.event.time.ChimeEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 准点报时任务.
 * <p>
 * 以现实时间为基准, 按现实时钟的每个小时的 00 分或 30 分时, 寄送一次该事件.
 */
public class ChimeJob implements Job {

    public static final String CRON = "0 0/30 * * * ?";

    @Override
    public void execute(JobExecutionContext context) {
        Bus.postSystemEvent(new ChimeEvent());
    }

}
