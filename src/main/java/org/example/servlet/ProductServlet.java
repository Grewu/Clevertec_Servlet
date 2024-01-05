package org.example.servlet;


import com.google.gson.Gson;
import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.service.ProductService;
import org.example.util.json.JsonHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

@Component
@RequestMapping("/controller")
public class ProductServlet extends HttpServlet {

    private final ProductService productService;

    @Autowired
    public ProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @GetMapping("/uuid")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String json;
        int page = Integer.parseInt(req.getParameter("page"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        if ("all".equals(uuid)) {
            List<InfoProductDto> products = productService.getAll(page, pageSize);
            json = new Gson().toJson(products);
        } else {
            json = new Gson().toJson(productService.get(UUID.fromString(uuid)));
        }
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = JsonHandler.getJson(req);
        Gson gson = JsonHandler.getGsonFormat();

        ProductDto productDto = gson.fromJson(json, ProductDto.class);
        productService.create(productDto);

        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = JsonHandler.getJson(req);
        Gson gson = JsonHandler.getGsonFormat();

        ProductDto productDto = gson.fromJson(json, ProductDto.class);
        productService.update(productDto.uuid(), productDto);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        productService.delete(UUID.fromString(uuid));
        String json = new Gson().toJson("Product with uuid " + uuid + "has been deleted");
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }
}