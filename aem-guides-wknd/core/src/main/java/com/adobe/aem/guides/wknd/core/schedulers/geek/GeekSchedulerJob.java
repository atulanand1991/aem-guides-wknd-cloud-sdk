package com.adobe.aem.guides.wknd.core.schedulers.geek;

import org.apache.sling.commons.scheduler.Job;
import org.apache.sling.commons.scheduler.JobContext;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Job.class, immediate = true)
@Designate(ocd = GeekSchedulerConfiguration.class, factory = true)
public class GeekSchedulerJob implements Job { // by implementing Job interface

    private static final Logger LOGGER = LoggerFactory.getLogger(GeekSchedulerJob.class);
    private int schedulerJobId;

    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(GeekSchedulerConfiguration config) {
        schedulerJobId = config.schedulerName().hashCode();
        addScheduleJob(config);
    }

    private void addScheduleJob(GeekSchedulerConfiguration config) {
        ScheduleOptions scheduleOptions = scheduler.EXPR(config.cronExpression());
        scheduleOptions.name(String.valueOf(schedulerJobId));
        scheduler.schedule(this, scheduleOptions);
    }
    @Deactivate
    protected void deactivate() {
        scheduler.unschedule(String.valueOf(schedulerJobId));
    }

    @Override
    public void execute(JobContext context) {
        LOGGER.info("\n GeekSchedulerJob - execute method");
    }
}
