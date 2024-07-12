package com.adobe.aem.guides.wknd.core.workflow;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Iterator;
import java.util.Set;
@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label" + " = First Custom Workflow Process",
                Constants.SERVICE_VENDOR + " = AEM WKND",
                Constants.SERVICE_DESCRIPTION + " = First Custom workflow step"
        }
)
public class FirstCustomWorkflow implements WorkflowProcess {

    private static Logger LOGGER = LoggerFactory.getLogger(FirstCustomWorkflow.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {
        try {
            WorkflowData workflowData = workItem.getWorkflowData();
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class);
                String path = workflowData.getPayload().toString() + "/jcr:content";
                Node node = session.getNode(path);
                String[] processArgs = processArguments.get("PROCESS_ARGS", "string").toString().split(",");
                for (String wfArgs : processArgs) {
                    String[] args = wfArgs.split(":");
                    String prop = args[0];
                    String value = args[1];
                    if (node != null) {
                        node.setProperty(prop, value);
                    }
                }
                MetaDataMap wfd = workItem.getWorkflow().getWorkflowData().getMetaDataMap();
            }
        } catch (PathNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
