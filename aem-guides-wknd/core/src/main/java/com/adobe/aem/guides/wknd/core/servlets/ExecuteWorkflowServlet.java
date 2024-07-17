
package com.adobe.aem.guides.wknd.core.servlets;

import com.adobe.aem.guides.wknd.core.service.WkndHelper;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.model.WorkflowModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = { Servlet.class })
@SlingServletPaths(
        value = {"/bin/executeworkflow", "/wknd/executeworkflow"})
@ServiceDescription("Execute WorkflowS Servlet")
public class ExecuteWorkflowServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;
    public static final Logger LOGGER = LoggerFactory.getLogger(ExecuteWorkflowServlet.class);

    @Reference
    WkndHelper wkndHelper;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resourceResolver = wkndHelper.getResourceResolver();
        //ResourceResolver resourceResolver = request.getResourceResolver();
        String payload = request.getRequestParameter("page").toString();

        try {
            WorkflowSession workflowSession = resourceResolver.adaptTo(WorkflowSession.class);
            WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/atul-page-versioning");
            WorkflowData workflowData = workflowSession.newWorkflowData("JCR_PATH", payload);

            workflowSession.startWorkflow(workflowModel, workflowData);
        } catch (WorkflowException e) {
            throw new RuntimeException(e);
        }
    }
}
