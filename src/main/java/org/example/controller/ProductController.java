package org.example.controller;


import org.example.dto.InfoProductDto;
import org.example.dto.ProductDto;
import org.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController(value = "/controller")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public @ResponseBody List<InfoProductDto> getAllProducts(
            @RequestParam int page,
            @RequestParam int pageSize) {
        return productService.getAll(page, pageSize);
    }

    @GetMapping("/{uuid}")
    public @ResponseBody InfoProductDto getProduct(@PathVariable UUID uuid) {
        return productService.get(uuid);
    }

    @PostMapping
    public @ResponseBody ProductDto createProduct(@RequestBody ProductDto productDto) {
        productService.create(productDto);
        return productDto;
    }

    @PutMapping
    public @ResponseBody ProductDto updateProduct(@RequestBody ProductDto productDto) {
        productService.update(productDto.uuid(), productDto);
        return productDto;
    }

    @DeleteMapping("/{uuid}")
    public @ResponseBody String deleteProduct(@PathVariable UUID uuid) {
        productService.delete(uuid);
        return "Product with uuid " + uuid + " has been deleted";
    }
}