package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import java.io.*;
import java.util.stream.IntStream;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

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

        // Create PDF document
        try {

            //ResourceResolver resourceResolver = request.getResourceResolver();
            //Session session = resourceResolver.adaptTo(Session.class);
            //Node nodeTemplate = session.getNode(templatePath);
            //InputStream inputStream = nodeTemplate.getNode("jcr:content/renditions/original/jcr:content").getProperty("jcr:data").getStream();
            //String htmlString = convert(inputStream);

            //htmlString = htmlString.replace("@SubmittedFor ", "John Deo");

            ITextRenderer renderer = new ITextRenderer();

            String htmlContent = "<html><body><h1>Hello, AEM PDF!</h1><p>This is a PDF generated using Flying Saucer.</p></body></html>";
            // Convert HTML to XHTML
            //String htmlToXhtml = htmlToXhtml(htmlContent);
            renderer.setDocumentFromString(htmlContent);

            // Render the document to PDF
            renderer.layout();

            renderer.createPDF(response.getOutputStream());




        } catch (Exception e) {
            response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF: " + e.getMessage());
        }

    }

    private static String convert(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString().trim(); // trim to remove the last newline
    }
    private static String htmlToXhtml(String html) {
        // Convert HTML to XHTML
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }


}
