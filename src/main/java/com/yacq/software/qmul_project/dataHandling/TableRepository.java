package com.yacq.software.qmul_project.dataHandling;

import com.yacq.software.qmul_project.model.Table;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TableRepository extends MongoRepository<Table, String> {
}
