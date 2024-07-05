package cafe.lunarconcerto.matrixcafe.api.schedule;

import cafe.lunarconcerto.matrixcafe.api.application.Bus;
import cafe.lunarconcerto.matrixcafe.api.data.event.time.HeartBeatEvent;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class HeartBeatJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        Bus.postSystemEvent(new HeartBeatEvent());
    }

}
