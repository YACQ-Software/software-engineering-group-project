package com.yacq.software.qmul_project.service;

import com.yacq.software.qmul_project.model.Customer;
import com.yacq.software.qmul_project.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //  Create or update a customer
    public Customer saveCustomer(Customer customer) {
        // Assign unique ID if not provided
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            customer.setCustomerId(UUID.randomUUID().toString());
        }

        // Optional: avoid duplicate emails
        if (customerRepository.existsByEmail(customer.getEmail())) {
            Optional<Customer> existing = customerRepository.findByEmail(customer.getEmail());
            customer.setCustomerId(existing.get().getCustomerId());  // Update instead of duplicate
        }

        return customerRepository.save(customer);
    }

    //  Get customer by ID
    public Optional<Customer> getCustomerById(String customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    //  Get customer by email
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    //  Get customer by phone
    public Optional<Customer> getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    //  Check if customer exists
    public boolean customerExists(String customerId) {
        return customerRepository.existsByCustomerId(customerId);
    }
}
