package com.adobe.aem.guides.wknd.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label"+ "=Atul Custom Process Step 2"
        }
)
public class SecondCustomWorkflow implements WorkflowProcess{

    public static final Logger LOGGER = LoggerFactory.getLogger(SecondCustomWorkflow.class);
    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) throws WorkflowException {

        try {
            WorkflowData workflowData = workItem.getWorkflowData();
            String payload = workflowData.getPayload().toString();
            String argument = args.get("PROCESS_ARGS", "String").toString();

            Session jcrSession = workflowSession.adaptTo(Session.class);
            Node pageNode = jcrSession.getNode(payload + "/jcr:content");
            pageNode.setProperty(argument, argument);






        } catch (Exception e) {
            LOGGER.error("Shit happened {}", e);
        }






        LOGGER.info("SecondCustomWorkflow -- End");
    }
}
