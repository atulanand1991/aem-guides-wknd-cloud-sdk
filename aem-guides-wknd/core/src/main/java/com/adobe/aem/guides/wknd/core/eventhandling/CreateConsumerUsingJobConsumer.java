package com.adobe.aem.guides.wknd.core.eventhandling;

import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.EventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = JobConsumer.class,
        immediate = true,
        property = {
             JobConsumer.PROPERTY_TOPICS+"=atul/add"
        }
)
public class CreateConsumerUsingJobConsumer implements JobConsumer {

        private static final Logger LOGGER = LoggerFactory.getLogger(CreateConsumerUsingJobConsumer.class);

        @Override
        public JobResult process(Job job) {
                LOGGER.info(">>>CreateConsumerUsingJobConsumer-START>>>");
                String path = job.getProperty("path").toString();
                String event = job.getProperty("event").toString();
                LOGGER.info(">>>CreateConsumerUsingJobConsumer-END>>>");
                //JobResult.FAILED
                //JobResult.CANCEL
                return JobResult.OK;
        }
}
