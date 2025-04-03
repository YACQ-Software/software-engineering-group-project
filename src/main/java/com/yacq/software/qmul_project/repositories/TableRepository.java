package com.yacq.software.qmul_project.repositories;

import com.yacq.software.qmul_project.model.Customer;
import com.yacq.software.qmul_project.model.Table;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends MongoRepository<Table, Integer> {

    Table findByTableNumber(int tableNumber);

}
