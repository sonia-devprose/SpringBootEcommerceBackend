package com.ecommerce.demo.controller;

import com.ecommerce.demo.dto.CartItemDto; // For output DTO
import com.ecommerce.demo.dto.CartItemDto; // For input DTO
import com.ecommerce.demo.service.CartItemService; // Corrected service name
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart") // Base URL for shopping cart endpoints
public class CartController {
    // Inject the now-correctly named CartItemService
    private final CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    // GET /api/cart/{customerId} - Get all cart items for a specific customer
    // This method now correctly returns a List of CartItemDto
    @GetMapping("/{customerId}")
    public ResponseEntity<List<CartItemDto>> getCustomerCart(@PathVariable Long customerId) {
        List<CartItemDto> cartItems = cartItemService.getCartItemsByCustomer(customerId);
        return ResponseEntity.ok(cartItems);
    }

    // POST /api/cart - Add a new item to cart or update quantity if it already exists
    // Accepts CartItemRequest DTO as input from the client
    @PostMapping
    public ResponseEntity<CartItemDto> addOrUpdateCartItem(@RequestBody CartItemDto cartItemRequest) {
        CartItemDto savedCartItem = cartItemService.addOrUpdateCartItem(cartItemRequest);
        return new ResponseEntity<>(savedCartItem, HttpStatus.CREATED);
    }

    // PUT /api/cart/items/{cartItemId}/quantity - Update the quantity of a specific cart item
    @PutMapping("/items/{cartItemId}/quantity")
    public ResponseEntity<CartItemDto> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) { // Use @RequestParam for simple quantity update

        CartItemDto updatedCartItem = cartItemService.updateCartItemQuantity(cartItemId, quantity);

        if (updatedCartItem == null) {
            // If the item was removed (quantity <= 0), return No Content
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(updatedCartItem);
    }

    // DELETE /api/cart/items/{cartItemId} - Remove a specific item from cart
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        cartItemService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    // DELETE /api/cart/{customerId} - Clear all items from a customer's cart
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartItemService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
