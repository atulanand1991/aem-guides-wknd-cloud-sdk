package com.adobe.aem.guides.wknd.core.eventhandling;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventConstants;
import org.apache.sling.api.SlingConstants;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC+"=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_FILTER+"=(path=/content/wknd/us/en/*)"
        }
)
public class CreateJobUsingJobManager implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateJobUsingJobManager.class);

    @Reference
    JobManager jobManager;

    @Override
    public void handleEvent(Event event) {
        LOGGER.info(">>>CreateJobUsingJobManager-START>>>");
        Map<String, Object> jobMap = new HashMap<>();
        jobMap.put("event", event.getTopic());
        jobMap.put("path", event.getProperty(SlingConstants.PROPERTY_PATH));
        Job job = jobManager.addJob("atul/add", jobMap);
        LOGGER.info(">>>CreateJobUsingJobManager-END>>>");
    }
}
