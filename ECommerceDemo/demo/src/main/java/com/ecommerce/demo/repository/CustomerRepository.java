package com.ecommerce.demo.repository;

import com.ecommerce.demo.entity.Customer;
import com.ecommerce.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    List<Customer> findByNameStartingWith(String prefix);
    List<Customer> findByNameContainingIgnoreCase(String keyword);
}