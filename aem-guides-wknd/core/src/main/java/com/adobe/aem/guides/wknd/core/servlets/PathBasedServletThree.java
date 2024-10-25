package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component(service = { Servlet.class },
        property = {
                "sling.servlet.paths=/bin/pathBasedServletThree",
                "sling.servlet.methods=GET"
        })
public class PathBasedServletThree extends SlingSafeMethodsServlet {


    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {

        String templatePath = "/content/dam/wknd/htmlfolder/JP_Sealant_Template.html";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"flyingSaucer.pdf\"");
        //response.setContentType("text/plain");
        //response.getWriter().write("Title = " + htmlString);

        try {
            // Use Flying Saucer to create PDF from HTML
            String htmlContent = "<html><body><h1>Hello, PDF!</h1></body></html>"; // Replace with dynamic content
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

}