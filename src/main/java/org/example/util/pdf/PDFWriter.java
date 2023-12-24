package org.example.util.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.entity.Product;
import org.example.exception.PDFException;

import java.io.FileOutputStream;
import java.io.IOException;

public class PDFWriter {
    private static final String INPUT_PDF_PATH = "Clevertec_Template.pdf";
    private static final String OUTPUT_PDF_PATH = "check.pdf";

    private PDFWriter() {
    }

    public static void createPdfWithBackground(Product product) {
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(OUTPUT_PDF_PATH));
            document.open();

            addBackgroundPdf(writer);
            addProductText(writer, product);

            document.close();

        } catch (Exception e) {
            throw new PDFException(e);
        }
    }

    private static void addBackgroundPdf(PdfWriter writer) {
        try {
            PdfReader reader = new PdfReader(INPUT_PDF_PATH);
            PdfImportedPage page = writer.getImportedPage(reader, 1);
            PdfContentByte canvas = writer.getDirectContentUnder();
            canvas.addTemplate(page, 0, 0);
        } catch (IOException e) {
            throw new PDFException(e);
        }
    }

    private static void addProductText(PdfWriter writer, Product product) {
        try {
            PdfContentByte canvas = writer.getDirectContent();

            ColumnText columnText = new ColumnText(canvas);
            columnText.setSimpleColumn(10, 580, 450, 500);


            String productText = createParagraph(product);
            Paragraph paragraph = new Paragraph(productText);
            columnText.addElement(paragraph);

            columnText.go();

        } catch (DocumentException e) {
            throw new PDFException(e);
        }
    }

    private static String createParagraph(Product product) {
        return "Product Name:                                                 " + product.getName() + "\n" +
                "Description:                                               " + product.getDescription() + "\n" +
                "Price: $                                                  " + product.getPrice() + "\n" +
                "Created:                                                  " + product.getCreated() + "\n\n";
    }


}
