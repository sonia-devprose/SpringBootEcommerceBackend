package com.ecommerce.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "products")
public class Product {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String description;
}