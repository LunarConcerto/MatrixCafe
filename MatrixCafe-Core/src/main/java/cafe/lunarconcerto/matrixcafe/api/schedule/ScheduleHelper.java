package cafe.lunarconcerto.matrixcafe.api.schedule;

import org.quartz.*;

public final class ScheduleHelper {

    private ScheduleHelper() { }

    public static JobDetail simpleJobDetail(Class<? extends Job> jobClass){
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobClass.getName())
                .build();
    }

    public static JobDetail simpleJobDetail(Class<? extends Job> jobClass, String identity){
        return JobBuilder.newJob(jobClass)
                .withIdentity(identity)
                .build();
    }

    public static Trigger simpleTrigger(String cron, String identity){
        return TriggerBuilder.newTrigger()
                .withIdentity(identity)
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }

}
