package com.adobe.aem.guides.wknd.core.schedulers.one;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "SchedulerOneOsgiConfig name", description = "SchedulerOneOsgiConfig description")
public @interface SchedulerOneOsgiConfig {

    @AttributeDefinition(name = "Cron-job expression")
    String scheduler_expression() default "0 0/1 * 1/1 * ? *";

    @AttributeDefinition(name = "Concurrent task",
            description = "Whether or not to schedule this task concurrently")
    boolean scheduler_concurrent() default false;

    @AttributeDefinition(name = "Country name",
            description = " Country description",
            type = AttributeType.STRING)
    String countryName() default "india";

    @AttributeDefinition(name = "apipath name",
            description = " apipath description",
            type = AttributeType.STRING)
     String apiPath() default "/api/sales/v2/export";
}
