package com.adobe.aem.guides.wknd.core.schedulers.one;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd = SchedulerOneOsgiConfig.class, factory = true)
public class SchedulerOne implements Runnable {

    private final Logger LOGGER = LoggerFactory.getLogger(SchedulerOne.class);
    private String countryName;
    private String apiPath;

    @Activate
    protected void activate(final SchedulerOneOsgiConfig schedulerOneOsgiConfig) {
        countryName = schedulerOneOsgiConfig.countryName();
        apiPath = schedulerOneOsgiConfig.apiPath();
    }

    @Override
    public void run() {
        LOGGER.info("=======Run-START=======");
        LOGGER.info(countryName +  " --- " +apiPath);
        LOGGER.info("=======Run-END=======");

    }
}
