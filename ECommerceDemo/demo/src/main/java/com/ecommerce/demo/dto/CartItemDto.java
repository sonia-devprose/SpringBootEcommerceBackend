package com.ecommerce.demo.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id; // The ID of the cart item itself
    private Long productId;
    private String productName; // Include product name for display convenience
    private Double productPrice; // Include product price for display convenience
    private Long customerId;
    private int quantity;
    private double itemTotal; // Calculated total for this line item (quantity * price)

    // Add a default constructor (Lombok's @Data usually handles this)
    public CartItemDto() {
    }

    // Constructor for convenience (optional)
    public CartItemDto(Long id, Long productId, String productName, Double productPrice, Long customerId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.customerId = customerId;
        this.quantity = quantity;
        this.itemTotal = productPrice * quantity; // Calculate total on creation
    }

    // Method to update itemTotal if quantity or price changes
    public void updateItemTotal() {
        if (this.productPrice != null) {
            this.itemTotal = this.productPrice * this.quantity;
        } else {
            this.itemTotal = 0.0;
        }
    }
}