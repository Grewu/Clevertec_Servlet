package org.example.controller;

import com.google.gson.Gson;
import org.example.dto.ProductDto;
import org.example.exception.PDFException;
import org.example.mapper.ProductMapper;
import org.example.util.json.JsonHandler;
import org.example.util.pdf.PDFWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/pdf")
public class PdfServlet {
    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public @ResponseBody String generatePdfPost(@RequestParam String json, HttpServletResponse resp) throws IOException {
        try {
            Gson gson = JsonHandler.getGsonFormat();

            ProductDto productDto = gson.fromJson(json, ProductDto.class);
            PDFWriter.createPdfWithBackground(productMapper.toProduct(productDto));

            resp.setStatus(200);
            return json;
        } catch (PDFException e) {
            resp.setStatus(500);
            return "Error processing the request: " + e.getMessage();
        }
    }
}
