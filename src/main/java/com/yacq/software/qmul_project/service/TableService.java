package com.yacq.software.qmul_project.service;

import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.table.Table;
import com.yacq.software.qmul_project.model.table.TableSection;
import com.yacq.software.qmul_project.model.table.TableShape;
import com.yacq.software.qmul_project.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    private final TableRepository tableRepository;
    private final ReservationService reservationService;

    @Autowired
    public TableService(TableRepository tableRepository, ReservationService reservationService) {
        this.tableRepository = tableRepository;
        this.reservationService = reservationService;
    }

    public List<Table> getAvailableTables(Date date, Integer minSize, TableSection section) {
        List<Table> allTables = tableRepository.findAll();
        return allTables.stream()
                .filter(t -> (minSize == null || t.getSize() >= minSize))
                .filter(t -> (section == null || t.getSection() == section))
                .filter(t -> reservationService.isTableAvailable(t.getTableId(), date))
                .toList();
    }

    // Get all tables
    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    // Get table by tableId
    public Optional<Table> getTableById(int tableId) {
        return Optional.ofNullable(tableRepository.findByTableId(tableId));
    }

    // Create or update a table
    public Table saveTable(Table table) {
        return tableRepository.save(table);
    }

    // Delete a table by tableId
    public boolean deleteTableById(int tableId) {
        if (tableRepository.existsByTableId(tableId)) {
            tableRepository.deleteByTableId(tableId);
            return true;
        }
        return false;
    }

    // Get tables by size
    public List<Table> getTablesBySize(int size) {
        return tableRepository.findBySize(size);
    }

    // Get tables by shape
    public List<Table> getTablesByShape(TableShape shape) {
        return tableRepository.findByShape(shape);
    }

    // Get tables by section
    public List<Table> getTablesBySection(TableSection section) {
        return tableRepository.findBySection(section);
    }

    // Get tables by minimum size
    public List<Table> getTablesWithMinSize(int size) {
        return tableRepository.findBySizeGreaterThanEqual(size);
    }

    // Get tables by size and section
    public List<Table> getTablesBySizeAndSection(int size, TableSection section) {
        return tableRepository.findBySizeAndSection(size, section);
    }

    // Get tables by section and shape
    public List<Table> getTablesBySectionAndShape(TableSection section, TableShape shape) {
        return tableRepository.findBySectionAndShape(section, shape);
    }

    public Optional<Table> getMostOptimalTable(Reservation reservationRequest) {
        // Step 1: Fetch available tables based on time and size
        List<Table> availableTables = getAvailableTables(
                reservationRequest.getDate(),
                reservationRequest.getPartySize(),
                reservationRequest.getSpecialRequests() != null ?
                        TableOptimisationService.analysePreference(reservationRequest.getSpecialRequests()).orElse(null) : null
        );

        // Step 2: Check if there are any available tables
        if (availableTables.isEmpty()) {
            return Optional.empty(); // No tables available
        }

        // Step 3: Rank tables by score
        List<Table> rankedTables = TableOptimisationService.rankTablesByScore(availableTables, reservationRequest);

        // Step 4: Return the table with the highest score (first in the list)
        return rankedTables.stream().findFirst();
    }


    // Check if a table exists
    public boolean existsByTableId(int tableId) {
        return tableRepository.existsByTableId(tableId);
    }
}
