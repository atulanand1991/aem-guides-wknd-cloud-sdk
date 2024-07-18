package com.adobe.aem.guides.wknd.core.schedulers.geek;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "GeekSchedulerConfiguration", description = "GeekSchedulerConfiguration description")
public @interface GeekSchedulerConfiguration {

    @AttributeDefinition(
            name = "Scheduler name",
            description = "Scheduler description",
            type = AttributeType.STRING)
    public String schedulerName() default "Custom geek schedule config";

    @AttributeDefinition(
            name = "Cron Expression",
            description = "Cron Expression used by scheduler",
            type = AttributeType.STRING)
    public String cronExpression() default "0/20 * * * * ?";
}
