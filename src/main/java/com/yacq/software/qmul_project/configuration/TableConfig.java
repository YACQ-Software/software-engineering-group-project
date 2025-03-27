package com.yacq.software.qmul_project.configuration;

import com.yacq.software.qmul_project.dataHandling.TableRepository;
import com.yacq.software.qmul_project.model.Table;
import com.yacq.software.qmul_project.dataHandling.TableRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;
import java.util.List;

@Configuration
public class TableConfig {

    @Autowired
    private TableRepository tableRepository;

    @PostConstruct
    public void initTables() {
        List<Table> tables = Arrays.asList(
                new Table(1, 1),
                new Table(2, 1),
                new Table(3, 1),
                new Table(4, 15),
                new Table(5, 5),
                new Table(6, 5),
                new Table(7, 5),
                new Table(8, 5),
                new Table(9, 5),
                new Table(10, 4),
                new Table(11, 4),
                new Table(12, 4),
                new Table(13, 4),
                new Table(14, 4),
                new Table(15, 2),
                new Table(16, 2)
        );
        tableRepository.saveAll(tables);
    }
}