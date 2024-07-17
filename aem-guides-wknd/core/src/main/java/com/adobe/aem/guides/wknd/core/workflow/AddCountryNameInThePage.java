package com.adobe.aem.guides.wknd.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Component(service = WorkflowProcess.class,
    immediate = true,
    property = {
        "process.label"+"=Add Country Name in the Page"
    }
)
public class AddCountryNameInThePage implements WorkflowProcess {
    @Override
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {

        WorkflowData workflowData = item.getWorkflowData();
        String payload = workflowData.getPayload().toString();
        String processArgs = args.get("PROCESS_ARGS", "string").toString();

        Session jrcSession = session.adaptTo(Session.class);
        try {
            Node jcrNode = jrcSession.getNode(payload + "/jcr:content");
            jcrNode.setProperty("country", processArgs + Math.random());
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }

    }
}
