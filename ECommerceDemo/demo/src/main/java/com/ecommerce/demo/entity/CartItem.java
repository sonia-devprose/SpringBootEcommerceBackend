package com.ecommerce.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "cart_items") // Explicitly names the table
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many cart items can belong to one product
    @JoinColumn(name = "product_id", nullable = false) // Foreign key column, cannot be null
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) // Many cart items can belong to one customer
    @JoinColumn(name = "customer_id", nullable = false) // Foreign key column, cannot be null
    private Customer customer;

    private int quantity;
}