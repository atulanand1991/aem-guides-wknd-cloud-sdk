package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.imageio.ImageIO;
import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = { Servlet.class },
        property = {
                "sling.servlet.paths=/bin/pathBasedServletTwo",
                "sling.servlet.methods=GET"
        })
public class PathBasedServletTwo extends SlingSafeMethodsServlet {


    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {


        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"apachePdfBox.pdf\"");

        String description = "Hello World description";

        String content = "Hello World Content";
        String image = "/content/dam/wknd/Momentive_Logo.png";

        // Create PDF document
        try {
            // Create the PDFBOx Object
            // Create a new empty document
            PDDocument document = new PDDocument();
            // Create a document and add a page to it
            PDPage page = new PDPage();
            document.addPage( page );

            float leading = 1.5f * 14;

            PDRectangle mediabox = page.findMediaBox();
            float margin = 72;
            float width = mediabox.getWidth() - 2*margin;
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;

            // Create a new font object selecting one of the PDF base fonts
            PDFont fontPlain = PDType1Font.HELVETICA;
            PDFont fontBold = PDType1Font.HELVETICA_BOLD;
            // Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // add title
            contentStream.beginText();
            contentStream.setFont(fontBold, 18);
            contentStream.moveTextPositionByAmount(70, 700);
            contentStream.drawString("titleDrawString");
            contentStream.endText();


            // add description
            List<String> lines = new ArrayList<String>();
            lines = writeTextToPDF (description, 14, fontPlain,width );

            contentStream.beginText();
            contentStream.setFont(fontPlain, 14);
            contentStream.moveTextPositionByAmount(70, 680);
            for (String line: lines)
            {
                contentStream.drawString(line);
                contentStream.moveTextPositionByAmount(0, -leading);
            }
            contentStream.endText();


            // add an image
            // check the path to image if have %20 in path of image, replace it to " "
            boolean hasWhiteSpaceInPath =  image.contains("%20");
            if(hasWhiteSpaceInPath)
                image = image.replaceAll("%20"," ");

            ResourceResolver resourceResolver = request.getResourceResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            Node nodeImage = session.getNode(image);
            InputStream in = nodeImage.getNode("jcr:content/renditions/original/jcr:content").getProperty("jcr:data").getStream();
            try {

                BufferedImage awtImage = ImageIO.read(in);
                PDXObjectImage ximage = new PDPixelMap(document, awtImage);
                float scale = 0.8f; // alter this value to set the image size
                contentStream.drawXObject(ximage, 70, 410, ximage.getWidth()*scale + 50, ximage.getHeight()*scale);
            } catch (FileNotFoundException fnfex) {
            }



            // add content
            List<String> linesContent = new ArrayList<String>();
            content = content.replaceAll("                " , " ");
            linesContent = writeTextToPDF (content, 10, fontPlain,width );

            contentStream.beginText();
            contentStream.setFont(fontPlain, 10);
            contentStream.moveTextPositionByAmount(65, 380);
            for (String line: linesContent)
            {
                contentStream.drawString(line);
                contentStream.moveTextPositionByAmount(0, -leading);
            }
            contentStream.endText();

            // Make sure that the content stream is closed:
            contentStream.close();


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(out.toByteArray());
        } catch (Exception e) {
            response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF: " + e.getMessage());
        }

    }

    private List<String> writeTextToPDF (String text, float fontSize, PDFont pdfFont, float width){
        try {

            List<String> lines = new ArrayList<String>();
            int lastSpace = -1;
            while (text.length() > 0) {
                int spaceIndex = text.indexOf(' ', lastSpace + 1);
                if (spaceIndex < 0) {
                    lines.add(text);
                    text = "";
                } else {
                    String subString = text.substring(0, spaceIndex);
                    float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
                    if (size > width) {
                        if (lastSpace < 0) // So we have a word longer than the line... draw it anyways
                            lastSpace = spaceIndex;
                        subString = text.substring(0, lastSpace);
                        lines.add(subString);
                        text = text.substring(lastSpace).trim();
                        lastSpace = -1;
                    } else {
                        lastSpace = spaceIndex;
                    }
                }
            }
            return lines;
        }catch (IOException e) {
            return null;
        }

    }

}
