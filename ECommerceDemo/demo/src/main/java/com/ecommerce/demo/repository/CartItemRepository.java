package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.CartItem;
import com.ecommerce.demo.entity.Customer;
import com.ecommerce.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import org.springframework.stereotype.Service;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCustomer(Customer customer);
    List<CartItem> findByProduct(Product product);
    Optional<CartItem> findByCustomerAndProduct(Customer customer, Product product); // Use Optional for single result

    @Modifying
    @Transactional
    void deleteByCustomer(Customer customer);

    // Custom query to find all cart items for a customer, including product details
    // Using JOIN FETCH to eager load product and customer to avoid N+1 problem when displaying cart
    @Query("SELECT ci FROM CartItem ci JOIN FETCH ci.product JOIN FETCH ci.customer WHERE ci.customer = :customer")
    List<CartItem> findByCustomerWithProductAndCustomer(@Param("customer") Customer customer);
}