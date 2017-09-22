package com.snapchat.jobs;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by apolonxviii on 22.09.17.
 */
public class QuartzHandler {

    private static final long EXECUTE_EVERY_MILLISECONDS = 1000;

    public static void shedule(Class<? extends org.quartz.Job> className) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(className)
                .withIdentity("Validate Image", "image group")
                //.usingJobData("path", path)
                .build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();
        scheduler.scheduleJob(job, createTriger());
    }

    private static Trigger createTriger() {
        return TriggerBuilder
                .newTrigger()
                .withIdentity("Validate Image", "image group")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMilliseconds(EXECUTE_EVERY_MILLISECONDS).repeatForever())
                .build();
    }
}
