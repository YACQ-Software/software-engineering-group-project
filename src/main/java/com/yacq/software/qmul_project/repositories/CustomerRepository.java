package com.yacq.software.qmul_project.repositories;

import com.yacq.software.qmul_project.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    // Find customer by their unique ID
    Optional<Customer> findByCustomerId(String customerId);

    // Find customer by email (assuming it's unique)
    Optional<Customer> findByEmail(String email);

    // Find customer by phone number
    Optional<Customer> findByPhone(String phone);

    // Check if a customer exists by ID
    boolean existsByCustomerId(String customerId);

    // Check if a customer exists by email
    boolean existsByEmail(String email);

}
