package com.adobe.aem.guides.wknd.core.eventhandling;

import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

@Component(service = EventListener.class, immediate = true)
public class JcrBasedEventListener implements EventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(JcrBasedEventListener.class);
    private Session session;

    @Reference
    SlingRepository slingRepository;

    @Activate
    public void activate() throws RepositoryException {
        String[] nodetypes={"cq:PageContent"};
        session = slingRepository.loginService("wkndSystemUser", null);
        session.getWorkspace().getObservationManager().addEventListener(
                this,
                Event.NODE_ADDED | Event.PROPERTY_ADDED,
                "/content/wknd/us/en/itc-infotech",
                true,
                null,
                nodetypes,
                false);
    }

    @Override
    public void onEvent(EventIterator eventIterator) {
        try {
            while (eventIterator.hasNext()){
                LOGGER.info("\n Path : {} ",eventIterator.nextEvent().getPath());
            }
        } catch(Exception e){
            LOGGER.error("\n Error while processing events : {} ",e.getMessage());
        }
    }
}
