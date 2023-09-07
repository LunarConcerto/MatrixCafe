package cafe.lunarconcerto.matrixcafe.api.schedule;

import com.google.inject.Singleton;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;

@Singleton
public class ScheduleRegistry {

    public static final String MATRIX_CAFE_SCHEDULER_NAME = "MatrixCafeScheduler";

    public static final String MATRIX_CAFE_SCHEDULE_INSTANCE_ID = "MATRIX_CAFE" ;

    protected Scheduler scheduler;

    public ScheduleRegistry() {
        try {
            DirectSchedulerFactory schedulerFactory = DirectSchedulerFactory.getInstance();
            schedulerFactory.createScheduler(
                    MATRIX_CAFE_SCHEDULER_NAME, MATRIX_CAFE_SCHEDULE_INSTANCE_ID,
                    new SimpleThreadPool(2, 1), new RAMJobStore());
            scheduler = schedulerFactory.getScheduler(MATRIX_CAFE_SCHEDULER_NAME);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(JobDetail detail, Trigger trigger) throws SchedulerException {
        scheduler.scheduleJob(detail, trigger);
    }

    public void start() throws SchedulerException {
        scheduler.start();
    }

    public void stop() throws SchedulerException {
        scheduler.clear();
    }


}