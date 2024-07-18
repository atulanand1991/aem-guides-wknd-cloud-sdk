package com.adobe.aem.guides.wknd.core.schedulers.geek;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd=GeekSchedulerConfiguration.class)
public class GeekSchedulerRunnable implements Runnable { // by implementing Runnable interface

    private static final Logger LOGGER = LoggerFactory.getLogger(GeekSchedulerRunnable.class);

    @Reference
    private Scheduler scheduler;

    private int scheduleId;
    @Activate
    protected void activate(GeekSchedulerConfiguration config) {
        scheduleId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    private void addScheduler(GeekSchedulerConfiguration config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions.name(String.valueOf(scheduleId));
        scheduleOptions.canRunConcurrently(false);

        ScheduleOptions scheduleOptionsNow = scheduler.NOW(2,5); //period in seconds
        //ScheduleOptions scheduleOptionsNow = scheduler.NOW();

        scheduler.schedule(this, scheduleOptions);
        LOGGER.info("\n -----------Geek Schedule added-----------");
    }
    @Deactivate
    protected void deactivate() {
        scheduler.unschedule(String.valueOf(scheduleId));
        LOGGER.info("\n -----------Geek Schedule removed-----------");
    }

    @Override
    public void run() {
        LOGGER.info("\n -----------Geek Schedule Run method executing-----------");
    }
}
