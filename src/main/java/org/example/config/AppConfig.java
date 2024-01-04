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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "org.example")
@EnableAspectJAutoProxy
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
    @Autowired
    private Environment environment;

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

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        String profile = environment.getProperty("spring.profiles.active");
        PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource[]{
                new ClassPathResource("application.yml"),
                new ClassPathResource(String.format("application-%s.yml", profile))
        });
        Properties yamlObject = Objects.requireNonNull(yaml.getObject(), "Could not found");
        propertySources.setProperties(yamlObject);
        return propertySources;
    }

}
