package org.example;


import com.google.gson.Gson;
import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.dto.ProductDto;
import org.example.mapper.ProductMapper;
import org.example.mapper.ProductMapperImpl;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet(value = "/controller")
public class ProductServlet extends HttpServlet {
    private final ProductMapper productMapper = new ProductMapperImpl();
    private final ProductDao productDao = new ProductDaoImpl(productMapper);
    private final ProductService productService = new ProductServiceImpl(productMapper, productDao);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        String json;
        if ("all".equals(uuid)) {
            json = new Gson().toJson(productService.getAll());
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
        ProductDto productDto = new Gson().fromJson(req.getReader().toString(), ProductDto.class);
        String json = new Gson().toJson(productDto);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        ProductDto productDto = new Gson().fromJson(req.getReader(), ProductDto.class);
        productService.update(UUID.fromString(uuid), productDto);
        String json = new Gson().toJson("Update");
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uuid = req.getParameter("uuid");
        productService.delete(UUID.fromString(uuid));
        String json = new Gson().toJson("Delete");
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(200);
        }
    }
}