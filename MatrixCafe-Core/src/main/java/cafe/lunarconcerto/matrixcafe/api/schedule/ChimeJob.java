package cafe.lunarconcerto.matrixcafe.api.schedule;

import cafe.lunarconcerto.matrixcafe.api.common.Bus;
import cafe.lunarconcerto.matrixcafe.api.data.event.time.ChimeEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class ChimeJob implements Job {

    public static final String CRON = "0 0/30 * * * ?";

    @Override
    public void execute(JobExecutionContext context) {
        Bus.postSystemEvent(new ChimeEvent());
    }

}
