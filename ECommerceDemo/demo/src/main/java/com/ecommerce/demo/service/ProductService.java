package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Marks this class as a service component (business logic layer)
@Service
public class ProductService {
    // Handles database operations for Product entities
    private final ProductRepository productRepository;

    // Constructor injection: Spring provides the repository
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ===========================
    // HELPER METHODS
    // ===========================

    // Helper method to convert a Product entity to ProductDto.
    // Used when reading data from the database to send to the client (read operations).
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        return dto;
    }

    // Helper method to convert a ProductDto to a Product entity.
    // Used for creating or updating products (write operations).
    private Product convertToEntity(ProductDto productDto) {
        Product product = new Product();
        // Set ID only if present (for updates)
        if (productDto.getId() != null) {
            product.setId(productDto.getId());
        }
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        return product;
    }

    // ===========================
    // MAIN SERVICE METHODS (CRUD)
    // ===========================

    // Get a list of all products as DTOs.
    // Used by admin to view/manage all products.
    @Transactional(readOnly = true)
    public List<ProductDto> getAllProducts() {
        // Fetch all Product entities, convert each to a DTO, and collect into a list
        return productRepository.findAll().stream()
                .map(this::convertToDto) // convert for read
                .collect(Collectors.toList());
    }

    // Get a single product by its ID.
    // Used by admin to view/manage a specific product.
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        // Try to find the product by ID, or throw an exception if not found
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        // Convert the found Product entity to a DTO for read
        return convertToDto(product);
    }

    // Create a new product using data from a ProductDto.
    // Only admin should be able to do this.
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        // Convert the DTO to a Product entity for saving (write)
        Product product = convertToEntity(productDto);
        // Save the new product to the database
        Product savedProduct = productRepository.save(product);
        // Convert the saved Product entity back to a DTO for read
        return convertToDto(savedProduct);
    }

    // Update an existing product by its ID, using data from a ProductDto.
    // Only admin should be able to do this.
    @Transactional
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        // Find the existing product, or throw an exception if not found
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Update the product's fields with the new values from the DTO
        existingProduct.setName(productDto.getName());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setDescription(productDto.getDescription());

        // Save the updated product back to the database
        Product savedProduct = productRepository.save(existingProduct);
        // Convert the updated Product entity to a DTO for read
        return convertToDto(savedProduct);
    }

    // Delete a product by its ID.
    // Only admin should be able to do this.
    @Transactional
    public void deleteProduct(Long id) {
        // Check if the product exists before trying to delete
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        // Delete the product from the database
        productRepository.deleteById(id);
    }
}
