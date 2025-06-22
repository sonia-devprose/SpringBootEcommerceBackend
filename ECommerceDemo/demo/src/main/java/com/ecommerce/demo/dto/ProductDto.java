package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private double price;
    private String description;
}