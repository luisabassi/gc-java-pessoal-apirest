package com.example.apirestwithspring.services;

import com.example.apirestwithspring.entities.Product;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestClient;

@Service
public class ProductService {

    private final RestClient client;

    public ProductService(RestClient.Builder builder) {
        this.client = builder.baseUrl("https://dummyjson.com").build();
    }


    @Transactional
    public Product addANewProduct (Product product){
       return client.post()
                .uri("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(product)
                .retrieve()
                .body(Product.class);
    }

    @Transactional
    public void deleteNewProduct(Long id){
        client.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }


}
