package com.adobe.aem.guides.wknd.core.eventhandling;

import org.apache.sling.api.SlingConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = EventHandler.class,
        immediate = true,
        property = {
                EventConstants.EVENT_TOPIC+"=org/apache/sling/api/resource/Resource/ADDED",
                EventConstants.EVENT_TOPIC+"=org/apache/sling/api/resource/Resource/CHANGED",
                EventConstants.EVENT_FILTER+"=(path=/content/wknd/us/en/*)",
        })
public class OsgiBasedEventHandling implements EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(OsgiBasedEventHandling.class);

    @Override
    public void handleEvent(Event event) {
        LOGGER.info("\n Resource event: {} at: {}", event.getTopic(), event.getProperty(SlingConstants.PROPERTY_PATH));
        for(String prop : event.getPropertyNames()) {
            LOGGER.info("\n Property: {}, Value: {}", prop, event.getProperty(prop));
        }
    }
}
