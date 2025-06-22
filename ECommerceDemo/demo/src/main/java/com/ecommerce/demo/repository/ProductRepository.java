package com.ecommerce.demo.repository;
import com.ecommerce.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByPriceGreaterThan(double price);
    List<Product> findByPriceBetween(double minPrice, double maxPrice);
}