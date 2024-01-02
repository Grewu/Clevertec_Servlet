package org.example.config;

import org.example.dao.ProductDao;
import org.example.dao.ProductDaoImpl;
import org.example.db.ConnectionManager;
import org.example.mapper.ProductMapper;
import org.example.mapper.ProductMapperImpl;
import org.example.service.ProductService;
import org.example.service.ProductServiceImpl;
import org.example.servlet.PdfServlet;
import org.example.servlet.ProductServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig {
    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${cache}")
    private String cacheType;

    @Value("${capacity}")
    private int cacheCapacity;

    @Bean
    public PdfServlet pdfServlet(ProductMapper productMapper) {
        return new PdfServlet(productMapper);
    }

    @Bean
    public ProductServlet productServlet(ProductService productService) {
        return new ProductServlet(productService);
    }

    @Bean
    public ConnectionManager connectionManager() {
        return new ConnectionManager(dbUrl, dbUsername, dbPassword);
    }

    @Bean
    public ProductDao productDao(ConnectionManager connectionManager, ProductMapper mapper) {
        return new ProductDaoImpl(connectionManager, mapper);
    }

    @Bean
    public ProductService productService(ProductMapper mapper, ProductDao productDao) {
        return new ProductServiceImpl(mapper, productDao);
    }

    @Bean
    public ProductMapper productMapper() {
        return new ProductMapperImpl();
    }

}
