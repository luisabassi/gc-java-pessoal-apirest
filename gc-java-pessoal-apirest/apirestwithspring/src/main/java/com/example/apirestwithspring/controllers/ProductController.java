package com.example.apirestwithspring.controllers;

import com.example.apirestwithspring.entities.Product;
import com.example.apirestwithspring.services.ProductService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productApi")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping("/product/add/{product_name}")
    public Product createProduct(@PathVariable String product_name) {
       return service.addANewProduct(new Product(product_name));
    }

    @DeleteMapping("/product/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        service.deleteNewProduct(id);
    }

    

}
