package com.adobe.aem.guides.wknd.core.eventhandling;

import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Component(
    service = ResourceChangeListener.class,
    immediate = true,
    property = {
        ResourceChangeListener.PATHS+"=/content/wknd/us/en/resourcechangelistenerhome",
        ResourceChangeListener.CHANGES+"=ADDED",
        ResourceChangeListener.CHANGES+"=REMOVED",
        ResourceChangeListener.CHANGES+"=CHANGED"
    })
public class ResourceChangeListenerHandling implements ResourceChangeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceChangeListenerHandling.class);

    @Override
    public void onChange(List<ResourceChange> changes) {
        for (ResourceChange rc : changes) {
            LOGGER.info("\n Event : {}, Resource : {}", rc.getType(), rc.getPath());
        }
    }
}
