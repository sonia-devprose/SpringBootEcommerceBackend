package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.CartItemDto; // For output to client
import com.ecommerce.demo.dto.CartItemDto; // For input from client
import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.entity.Customer;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.repository.CartItemRepository;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemService { // Renamed from CartService to CartItemService for clarity
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public CartItemService(CartItemRepository cartItemRepository,
                           ProductRepository productRepository,
                           CustomerRepository customerRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    // Helper method: Convert CartItem entity to CartItemDto for sending data to the client
    private CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cartItem.getId()); // Include CartItem ID
        dto.setProductId(cartItem.getProduct().getId());
        dto.setProductName(cartItem.getProduct().getName()); // Include product name
        dto.setProductPrice(cartItem.getProduct().getPrice()); // Include product price
        dto.setQuantity(cartItem.getQuantity());
        dto.setCustomerId(cartItem.getCustomer().getId());
        dto.updateItemTotal(); // Calculate total
        return dto;
    }

    // Helper method: Convert CartItemRequest DTO to CartItem entity for saving/updating in the database
    // This is typically handled within the specific service method, as it involves fetching related entities.
    // So, no general convertToEntity for CartItemDto is needed here, as CartItemRequest is for input.

    // Get all cart items for a specific customer as DTOs
    @Transactional(readOnly = true)
    public List<CartItemDto> getCartItemsByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        // Use the custom repository method to eagerly load product and customer details
        List<CartItem> cartItems = cartItemRepository.findByCustomerWithProductAndCustomer(customer);

        return cartItems.stream()
                .map(this::convertToDto) // Convert each entity to a DTO
                .collect(Collectors.toList());
    }

    // Add a product to the customer's cart, or update quantity if already present
    @Transactional
    public CartItemDto addOrUpdateCartItem(CartItemDto request) { // Accepts CartItemRequest DTO
        // 1. Fetch the Customer and Product entities using their IDs
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + request.getCustomerId()));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        // 2. Check if this product is already in this customer's cart
        Optional<CartItem> existingCartItem = cartItemRepository.findByCustomerAndProduct(customer, product);

        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            // If item exists, update its quantity
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            // If item does not exist, create a new CartItem
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }

        // 3. Save or update the cart item in the database
        CartItem savedCartItem = cartItemRepository.save(cartItem);

        // 4. Convert the saved entity back to a DTO for the response
        return convertToDto(savedCartItem);
    }

    // Update the quantity of a specific cart item
    @Transactional
    public CartItemDto updateCartItemQuantity(Long cartItemId, int newQuantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + cartItemId));

        if (newQuantity <= 0) {
            // If quantity is 0 or less, remove the item
            cartItemRepository.deleteById(cartItemId);
            return null; // Or throw a specific exception, or return a status indicating deletion
        } else {
            cartItem.setQuantity(newQuantity);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            return convertToDto(savedCartItem);
        }
    }

    // Remove a cart item by its ID
    @Transactional
    public void removeCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new ResourceNotFoundException("Cart item not found with id: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }

    // Clear all cart items for a customer
    @Transactional
    public void clearCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
        // Use the custom repository method to delete all items for the customer
        cartItemRepository.deleteByCustomer(customer);
    }
}
