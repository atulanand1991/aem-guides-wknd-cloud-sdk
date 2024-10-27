package com.adobe.aem.guides.wknd.core.servlets;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDInlineImage;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.awt.image.BufferedImage;
import java.io.*;

@Component(service = { Servlet.class },
        property = {
                "sling.servlet.paths=/bin/pdfBoxTwoZeroTwoFourServlet",
                "sling.servlet.methods=GET"
        })
public class PdfBoxTwoZeroTwoFourServlet extends SlingSafeMethodsServlet {


    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"pdfBoxTwoZeroTwoFourServlet.pdf\"");

        try {
            PDDocument pdfDocument = new PDDocument();
            PDPage pdfPage = new PDPage();
            pdfDocument.addPage(pdfPage);
            PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, pdfPage);

            float margin = 50;
            float yStart = pdfPage.getMediaBox().getHeight() - margin;

            //Setting custom Font
            String fontPath = "/content/dam/momentive/fontfolder/myFont.ttf/jcr:content/renditions/original/jcr:content";
            ResourceResolver resolver = request.getResourceResolver();
            Resource fontResource = resolver.getResource(fontPath);
            InputStream fontStream = fontResource.adaptTo(InputStream.class);
            PDType0Font font = PDType0Font.load(pdfDocument, fontStream);

            //Setting images
            String logoPath = "/content/dam/momentive/images/logo.png/jcr:content/renditions/original/jcr:content";
            String stampPath = "/content/dam/momentive/images/stamp.png/jcr:content/renditions/original/jcr:content";
            Resource logoResource = resolver.getResource(logoPath);
            Resource stampResource = resolver.getResource(stampPath);

            InputStream logoStream = logoResource.adaptTo(InputStream.class);
            InputStream stampStream = stampResource.adaptTo(InputStream.class);

            File logoFile = inputStreamToFile(logoStream, "logo.png");
            PDImageXObject logo = PDImageXObject.createFromFileByContent(logoFile, pdfDocument);

            //File stampFile = inputStreamToFile(logoStream, "stamp.png");
            //PDImageXObject stamp = PDImageXObject.createFromFileByContent(stampFile, pdfDocument);




            //Row 1
            contentStream.drawImage(logo, margin - 30, yStart + 5, 192, 40);

            //Row 2
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 20);
            contentStream.setFont(font, 25);
            contentStream.showText("出荷証明書");
            contentStream.endText();

            //Row 3
            contentStream.beginText();
            contentStream.newLineAtOffset(pdfPage.getMediaBox().getWidth() - 100, yStart - 60);
            contentStream.setFont(font, 16);
            contentStream.showText("12-12-2024");
            contentStream.endText();

            //Row 4
            contentStream.beginText();
            contentStream.setLeading(25);
            contentStream.newLineAtOffset(margin - 30, yStart - 80);
            contentStream.setFont(font, 16);
            contentStream.showText("SubmittedFor1");
            contentStream.newLine();
            contentStream.showText("SubmittedFor2");
            contentStream.endText();

            //Row 5
            contentStream.beginText();
            contentStream.setLeading(20);
            contentStream.newLineAtOffset(pdfPage.getMediaBox().getWidth() - 250, yStart - 150);
            contentStream.setFont(font, 16);
            contentStream.showText("モメンティブ・パフォーマンス・");
            contentStream.newLine();
            contentStream.showText("カスタマーサービス");
            contentStream.newLine();
            contentStream.showText("〒373-8505");
            contentStream.newLine();
            contentStream.showText("群馬県太田市西新町133");
            contentStream.newLine();
            contentStream.showText("TEL　0120-99-4400");
            contentStream.endText();

            //Row 5 stamp
            //contentStream.drawImage(stamp, pdfPage.getMediaBox().getWidth() - 120, yStart - 250, 100, 100);

            //Table-Row-1-Column-1
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 300);
            contentStream.setFont(font, 16);
            contentStream.showText("工事名");
            contentStream.endText();

            //Table-Row-1-Column-2
            contentStream.beginText();
            contentStream.setLeading(20);
            contentStream.newLineAtOffset(margin + 60, yStart - 300);
            contentStream.setFont(font, 16);
            contentStream.showText("ContructionName1");
            contentStream.newLine();
            contentStream.showText("ContructionName2");
            contentStream.endText();
            //////////////////////////////////////////////////////////////////
            //Table-Row-2-Column-1
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 350);
            contentStream.setFont(font, 16);
            contentStream.showText("現場住所");
            contentStream.endText();

            //Table-Row-2-Column-2
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 60, yStart - 350);
            contentStream.setFont(font, 16);
            contentStream.showText("ContructionAddress");
            contentStream.endText();
            //////////////////////////////////////////////////////////////////
            //Table-Row-3-Column-1
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 380);
            contentStream.setFont(font, 16);
            contentStream.showText("建設会社");
            contentStream.endText();

            //Table-Row-3-Column-2
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 60, yStart - 380);
            contentStream.setFont(font, 16);
            contentStream.showText("ContructionCompany");
            contentStream.endText();
            //////////////////////////////////////////////////////////////////
            //Table-Row-4-Column-1
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 410);
            contentStream.setFont(font, 16);
            contentStream.showText("施工会社");
            contentStream.endText();

            //Table-Row-4-Column-2
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 60, yStart - 410);
            contentStream.setFont(font, 16);
            contentStream.showText("ContructionCompanyPractitionar");
            contentStream.endText();
            //////////////////////////////////////////////////////////////////
            //Table-Row-5-Column-1
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 440);
            contentStream.setFont(font, 16);
            contentStream.showText("出荷年月日");
            contentStream.endText();

            //Table-Row-5-Column-2
            contentStream.beginText();
            contentStream.newLineAtOffset(margin + 60, yStart - 440);
            contentStream.setFont(font, 16);
            contentStream.showText("ShippedDate");
            contentStream.endText();
            //////////////////////////////////////////////////////////////////

            //Table-Row-6
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 470);
            contentStream.setFont(font, 16);
            contentStream.showText("下記の通り出荷したことを証明します。");
            contentStream.endText();

            //Table-Row-7
            contentStream.beginText();
            contentStream.newLineAtOffset(margin - 30, yStart - 500);
            contentStream.setFont(font, 16);
            contentStream.showText("DocLineitem");
            contentStream.endText();

            contentStream.close();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pdfDocument.save(out);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(out.toByteArray());
        } catch (Exception e) {
            response.sendError(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF: " + e.getMessage());
        }

    }
    public static File inputStreamToFile(InputStream inputStream, String fileName) throws IOException {
        // Create a temporary file
        File tempFile = File.createTempFile(fileName, null);
        tempFile.deleteOnExit(); // Ensure the file is deleted on JVM exit

        // Write the InputStream to the temporary file
        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

}
