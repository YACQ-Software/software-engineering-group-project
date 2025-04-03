package com.yacq.software.qmul_project.repositories;

import com.yacq.software.qmul_project.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    // can define custom queries here later if needed

}
