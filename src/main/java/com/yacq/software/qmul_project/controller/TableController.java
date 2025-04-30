package com.yacq.software.qmul_project.controller;

import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.table.Table;
import com.yacq.software.qmul_project.model.table.TableSection;
import com.yacq.software.qmul_project.model.table.TableShape;
import com.yacq.software.qmul_project.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping
    public ResponseEntity<Table> saveTable(@RequestBody Table table) {
        Table saved = tableService.saveTable(table);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {
        return ResponseEntity.ok(tableService.getAllTables());
    }

    @GetMapping("/available")
    public ResponseEntity<List<Table>> getAvailableTables(
            @RequestParam Date date,
            @RequestParam(required = false) Integer minSize,
            @RequestParam(required = false) TableSection section
    ) {
        List<Table> available = tableService.getAvailableTables(date, minSize, section);
        return ResponseEntity.ok(available);
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<Table> getTableById(@PathVariable int tableId) {
        Optional<Table> table = tableService.getTableById(tableId);
        return table.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable int tableId) {
        boolean deleted = tableService.deleteTableById(tableId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<Table>> getTablesBySize(@PathVariable int size) {
        return ResponseEntity.ok(tableService.getTablesBySize(size));
    }

    @GetMapping("/min-size/{size}")
    public ResponseEntity<List<Table>> getTablesWithMinSize(@PathVariable int size) {
        return ResponseEntity.ok(tableService.getTablesWithMinSize(size));
    }

    @GetMapping("/shape/{shape}")
    public ResponseEntity<List<Table>> getTablesByShape(@PathVariable TableShape shape) {
        return ResponseEntity.ok(tableService.getTablesByShape(shape));
    }

    @GetMapping("/section/{section}")
    public ResponseEntity<List<Table>> getTablesBySection(@PathVariable TableSection section) {
        return ResponseEntity.ok(tableService.getTablesBySection(section));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Table>> getTablesBySizeAndSection(
            @RequestParam int size,
            @RequestParam TableSection section) {
        return ResponseEntity.ok(tableService.getTablesBySizeAndSection(size, section));
    }

    @GetMapping("/filter/shape")
    public ResponseEntity<List<Table>> getTablesBySectionAndShape(
            @RequestParam TableSection section,
            @RequestParam TableShape shape) {
        return ResponseEntity.ok(tableService.getTablesBySectionAndShape(section, shape));
    }

    @PostMapping("/auto-optimise-booking")
    public ResponseEntity<Optional<Table>> autoOptimiseBooking(@RequestBody Reservation reservation) {
        Optional<Table> optimisedTables = tableService.getMostOptimalTable(reservation);
        return ResponseEntity.ok(optimisedTables);
    }
}
