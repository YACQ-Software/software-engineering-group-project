package com.yacq.software.qmul_project.repositories;

import com.yacq.software.qmul_project.model.table.Table;
import com.yacq.software.qmul_project.model.table.TableSection;
import com.yacq.software.qmul_project.model.table.TableShape;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends MongoRepository<Table, String> {

    // Find by unique table ID
    Table findByTableId(int tableId);

    // Find all tables of a certain size
    List<Table> findBySize(int size);

    // Find all tables with a minimum size
    List<Table> findBySizeGreaterThanEqual(int size);

    // Find all tables by shape
    List<Table> findByShape(TableShape shape);

    // Find all tables in a given section
    List<Table> findBySection(TableSection section);

    // Find by multiple attributes
    List<Table> findBySizeAndSection(int size, TableSection section);

    // Optional: Find by section and shape
    List<Table> findBySectionAndShape(TableSection section, TableShape shape);

    // Deletion by table ID
    void deleteByTableId(int tableId);

    // Optional: If tableId is unique and used as MongoDB _id
    boolean existsByTableId(int tableId);

}
