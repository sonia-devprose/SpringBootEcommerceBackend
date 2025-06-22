package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.CustomerDto;
import com.ecommerce.demo.entity.Customer;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

// Marks this class as a Spring service (business logic layer)
@Service
public class CustomerService {
    // Repository for database operations related to Customer entities
    private final CustomerRepository customerRepository;

    // Constructor injection: Spring provides the repository instance
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // ===========================
    // HELPER METHODS
    // ===========================

    // Helper method to convert a Customer entity to CustomerDto.
    // Used when reading customer data from the database to send to the client.
    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    // Helper method to convert a CustomerDto to a Customer entity.
    // Used for creating or updating customers in the database.
    private Customer convertToEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        // Set ID only if present (for updates)
        if (customerDto.getId() != null) {
            customer.setId(customerDto.getId());
        }
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }

    // ===========================
    // MAIN SERVICE METHODS (CRUD)
    // ===========================

    // Get a list of all customers as DTOs.
    // Typically used by admin to view/manage all customers.
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        // Fetch all Customer entities, convert each to a DTO, and collect into a list
        return customerRepository.findAll().stream()
                .map(this::convertToDto) // convert for read
                .collect(Collectors.toList());
    }

    // Get a single customer by their ID.
    // Used to view or manage a specific customer.
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Long id) {
        // Try to find the customer by ID, or throw an exception if not found
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        // Convert the found Customer entity to a DTO for read
        return convertToDto(customer);
    }

    // Create a new customer using data from a CustomerDto.
    // Used when registering a new customer or admin creates a record.
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        // Convert the DTO to a Customer entity for saving (write)
        Customer customer = convertToEntity(customerDto);
        // Save the new customer to the database
        Customer savedCustomer = customerRepository.save(customer);
        // Convert the saved Customer entity back to a DTO for read
        return convertToDto(savedCustomer);
    }

    // Update an existing customer by their ID, using data from a CustomerDto.
    // Used by admin or customer (for profile update).
    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        // Find the existing customer, or throw an exception if not found
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        // Update the customer's fields with the new values from the DTO
        existingCustomer.setName(customerDto.getName());
        existingCustomer.setEmail(customerDto.getEmail());

        // Save the updated customer back to the database
        Customer savedCustomer = customerRepository.save(existingCustomer);
        // Convert the updated Customer entity to a DTO for read
        return convertToDto(savedCustomer);
    }

    // Delete a customer by their ID.
    // Typically used by admin to remove a customer record.
    @Transactional
    public void deleteCustomer(Long id) {
        // Check if the customer exists before trying to delete
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        // Delete the customer from the database
        customerRepository.deleteById(id);
    }
}
