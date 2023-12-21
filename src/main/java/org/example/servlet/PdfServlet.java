package org.example.servlet;

import com.google.gson.Gson;
import org.example.dto.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.mapper.ProductMapperImpl;
import org.example.util.json.JsonHandler;
import org.example.util.pdf.PDFWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/pdf")
public class PdfServlet extends HttpServlet {
    private final ProductMapper productMapper = new ProductMapperImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String json = JsonHandler.getJson(req);
            Gson gson = JsonHandler.getGsonFormat();

            ProductDto productDto = gson.fromJson(json, ProductDto.class);
            PDFWriter.createPdfWithBackground(productMapper.toProduct(productDto));
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
                resp.setStatus(200);
            }
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Error processing the request: " + e.getMessage());
        }
    }
}
