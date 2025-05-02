package com.yacq.software.qmul_project.service;

import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.table.Table;
import com.yacq.software.qmul_project.model.table.TableSection;
import com.yacq.software.qmul_project.model.table.TableShape;
import com.yacq.software.qmul_project.repositories.TableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceTest {

    @Mock
    private TableRepository tableRepository;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private TableService tableService;

    private Table sampleTable;

    @BeforeEach
    void setUp() {
        sampleTable = new Table();
        sampleTable.setTableId(1);
        sampleTable.setSize(4);
        sampleTable.setShape(TableShape.RECTANGLE);
        sampleTable.setSection(TableSection.BAR);
    }

    private static final Date FIXED_DATE = new Date(1672531200000L); // Equivalent to 2023-01-01 00:00:00 UTC


    @Test
    void testGetAllTables_ReturnsAllTables() {
        List<Table> tables = Arrays.asList(sampleTable);
        when(tableRepository.findAll()).thenReturn(tables);

        List<Table> result = tableService.getAllTables();

        assertEquals(1, result.size());
        assertEquals(sampleTable, result.get(0));
        verify(tableRepository, times(1)).findAll();
    }

    @Test
    void testGetTableById_TableExists() {
        when(tableRepository.findByTableId(1)).thenReturn(sampleTable);

        Optional<Table> result = tableService.getTableById(1);

        assertTrue(result.isPresent());
        assertEquals(sampleTable, result.get());
        verify(tableRepository, times(1)).findByTableId(1);
    }

    @Test
    void testGetTableById_TableDoesNotExist() {
        when(tableRepository.findByTableId(999)).thenReturn(null);

        Optional<Table> result = tableService.getTableById(999);

        assertFalse(result.isPresent());
        verify(tableRepository, times(1)).findByTableId(999);
    }

    @Test
    void testSaveTable_CreatesOrUpdatesTable() {
        when(tableRepository.save(sampleTable)).thenReturn(sampleTable);

        Table result = tableService.saveTable(sampleTable);

        assertNotNull(result);
        assertEquals(sampleTable, result);
        verify(tableRepository, times(1)).save(sampleTable);
    }

    @Test
    void testDeleteTableById_TableExists() {
        when(tableRepository.existsByTableId(1)).thenReturn(true);
        doNothing().when(tableRepository).deleteByTableId(1);

        boolean result = tableService.deleteTableById(1);

        assertTrue(result);
        verify(tableRepository, times(1)).existsByTableId(1);
        verify(tableRepository, times(1)).deleteByTableId(1);
    }

    @Test
    void testDeleteTableById_TableDoesNotExist() {
        when(tableRepository.existsByTableId(999)).thenReturn(false);

        boolean result = tableService.deleteTableById(999);

        assertFalse(result);
        verify(tableRepository, times(1)).existsByTableId(999);
        verify(tableRepository, never()).deleteByTableId(999);
    }

    @Test
    void testGetTablesBySize_ReturnsMatchingTables() {
        List<Table> tables = Arrays.asList(sampleTable);
        when(tableRepository.findBySize(4)).thenReturn(tables);

        List<Table> result = tableService.getTablesBySize(4);

        assertEquals(1, result.size());
        assertEquals(sampleTable, result.get(0));
        verify(tableRepository, times(1)).findBySize(4);
    }

    @Test
    void testGetTablesByShape_ReturnsMatchingTables() {
        List<Table> tables = Arrays.asList(sampleTable);
        when(tableRepository.findByShape(TableShape.RECTANGLE)).thenReturn(tables);

        List<Table> result = tableService.getTablesByShape(TableShape.RECTANGLE);

        assertEquals(1, result.size());
        assertEquals(sampleTable, result.get(0));
        verify(tableRepository, times(1)).findByShape(TableShape.RECTANGLE);
    }

    @Test
    void testGetTablesBySection_ReturnsMatchingTables() {
        List<Table> tables = Arrays.asList(sampleTable);
        when(tableRepository.findBySection(TableSection.BAR)).thenReturn(tables);

        List<Table> result = tableService.getTablesBySection(TableSection.BAR);

        assertEquals(1, result.size());
        assertEquals(sampleTable, result.get(0));
        verify(tableRepository, times(1)).findBySection(TableSection.BAR);
    }

    @Test
    void testGetTablesWithMinSize_ReturnsMatchingTables() {
        List<Table> tables = Arrays.asList(sampleTable);
        when(tableRepository.findBySizeGreaterThanEqual(4)).thenReturn(tables);

        List<Table> result = tableService.getTablesWithMinSize(4);

        assertEquals(1, result.size());
        assertEquals(sampleTable, result.get(0));
        verify(tableRepository, times(1)).findBySizeGreaterThanEqual(4);
    }

    @Test
    void testExistsByTableId_TableExists() {
        when(tableRepository.existsByTableId(1)).thenReturn(true);

        boolean result = tableService.existsByTableId(1);

        assertTrue(result);
        verify(tableRepository, times(1)).existsByTableId(1);
    }

    @Test
    void testExistsByTableId_TableDoesNotExist() {
        when(tableRepository.existsByTableId(999)).thenReturn(false);

        boolean result = tableService.existsByTableId(999);

        assertFalse(result);
        verify(tableRepository, times(1)).existsByTableId(999);
    }

//    @Test
//    void testGetMostOptimalTable_FindsBestTable() {
//        Reservation reservation = new Reservation();
//        reservation.setTableId(1);
//        reservation.setDate(new Date());
//        reservation.setPartySize(4);
//        reservation.setSpecialRequests("I want to be near a window with a lot of daylight");
//
//        Table table1 = new Table();
//        table1.setTableId(1);
//        table1.setSize(4);
//        table1.setSection(TableSection.WINDOW);
//        table1.setShape(TableShape.RECTANGLE);
//
//        Table table2 = new Table();
//        table2.setTableId(2);
//        table2.setSize(6);
//        table2.setSection(TableSection.BAR);
//        table2.setShape(TableShape.CIRCLE);
//
//
//        when(reservationService.isTableAvailable(1, FIXED_DATE)).thenReturn(true);
//        when(tableRepository.findAll()).thenReturn(Arrays.asList(sampleTable));
//        when(tableService.getAvailableTables(reservation.getDate(), reservation.getPartySize(), reservation.getSpecialRequests()));
//
//        Optional<Table> result = tableService.getMostOptimalTable(reservation);
//
//        assertTrue(result.isPresent());
//        assertEquals(sampleTable, result.get());
//        verify(reservationService, times(1)).isTableAvailable(1, FIXED_DATE);
//        verify(tableRepository, times(1)).findAll();
//    }
}