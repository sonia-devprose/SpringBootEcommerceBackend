package com.ecommerce.demo.entity;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity // Marks this class as a JPA entity
@Table(name = "customers") // Explicitly names the table
public class Customer {
    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments ID
    private Long id;
    private String name;
    private String email;

    // In a real e-commerce app, you'd typically have a @OneToMany relationship here:
    // @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    // private Set<CartItem> cartItems = new HashSet<>();
    // However, for "simpler" code and to match previous requests not to involve cartItems in Customer for now, we omit it.
}