package com.yacq.software.qmul_project.controller;

import com.yacq.software.qmul_project.model.table.Table;
import com.yacq.software.qmul_project.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    @Autowired
    private TableRepository tableRepository;

    // Get all tables
    @GetMapping
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    // Get table by number
    @GetMapping("/{tableNumber}")
    public Table getTableByNumber(@PathVariable int tableNumber) {
        return tableRepository.findByTableNumber(tableNumber);
    }

    // Create new table
    @PostMapping
    public Table createTable(@RequestBody Table table) {
        return tableRepository.save(table);
    }

    // Update booking status
    @PutMapping("/{tableNumber}/book")
    public Table updateBookingStatus(@PathVariable int tableNumber, @RequestParam boolean booked) {
        Table table = tableRepository.findByTableNumber(tableNumber);

        if (table != null) {
            table.setBooked(booked);
            return tableRepository.save(table);
        }

        throw new RuntimeException("Table not found");
    }
}
