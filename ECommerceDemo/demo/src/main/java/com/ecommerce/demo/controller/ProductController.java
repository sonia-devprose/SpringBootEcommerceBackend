package com.ecommerce.demo.controller;
import com.ecommerce.demo.dto.ProductDto;
import com.ecommerce.demo.entity.Product;
import com.ecommerce.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Declares the class as a REST controller, enabling automatic serialization of 
// return values (typically to JSON) and simplifying response handling
@RestController
// Sets a base URI for all endpoints in this controller. All methods map to URLs starting with /api/products.
@RequestMapping("/products")
public class ProductController {
    // The controller depends on the service layer for business logic.
    private final ProductService productService;
    // Dependency injection
    // The ProductService is injected via the constructor, ensuring immutability and clear dependency declaration—a best practice in Spring
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // Get product by ID
    // Maps HTTP GET requests with a URL like /api/products/{id} to this method.
    @GetMapping("/{id}")
    // ResponseEntity is a powerful and flexible class in Spring Framework used to represent the entire HTTP response. 
    // It allows you to control not just the response body, but also the HTTP status code and headers.
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

   // Create a new product
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) { // CHANGE
        ProductDto createdProduct = productService.createProduct(productDto); // CHANGE
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    //Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) { // CHANGE
        ProductDto updatedProduct = productService.updateProduct(id, productDto); // CHANGE
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product of a particular id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}