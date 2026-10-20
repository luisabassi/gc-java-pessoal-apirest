package com.example.apirestwithspring.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    private Integer id;
    private String title;

    public Product(String productName) {
        this.title = productName;
    }
}