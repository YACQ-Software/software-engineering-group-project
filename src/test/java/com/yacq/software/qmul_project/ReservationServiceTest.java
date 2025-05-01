package com.yacq.software.qmul_project;

import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.reservation.ReservationStatus;
import com.yacq.software.qmul_project.repositories.ReservationRepository;
import com.yacq.software.qmul_project.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation sampleReservation;

    @BeforeEach
    void setUp() {
        sampleReservation = new Reservation();
        sampleReservation.setReservationId("res123");
        sampleReservation.setCustomerId("cust001");
        sampleReservation.setTableId(5);
        sampleReservation.setDate(new Date());
        sampleReservation.setStatus(ReservationStatus.CONFIRMED);
        sampleReservation.setCreatedAt(new Date());
    }

    @Test
    void testIsTableAvailable_TableIsAvailable_ReturnsTrue() {
        when(reservationRepository.findByTableIdAndDate(anyInt(), any(Date.class)))
                .thenReturn(Collections.emptyList());

        boolean available = reservationService.isTableAvailable(1, new Date());
        assertTrue(available);
    }

    @Test
    void testIsTableAvailable_TableNotAvailable_ReturnsFalse() {
        when(reservationRepository.findByTableIdAndDate(anyInt(), any(Date.class)))
                .thenReturn(List.of(sampleReservation));

        boolean available = reservationService.isTableAvailable(1, new Date());
        assertFalse(available);
    }

    @Test
    void testCreateReservation_SetsDefaultsAndSaves() {
        Reservation reservation = new Reservation();
        reservation.setCustomerId("cust002");
        reservation.setTableId(3);
        reservation.setDate(new Date());

        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Reservation created = reservationService.createReservation(reservation);

        assertNotNull(created.getReservationId());
        assertNotNull(created.getCreatedAt());
        assertEquals(ReservationStatus.PROCESSING, created.getStatus());
    }

    @Test
    void testGetReservationById_Exists() {
        when(reservationRepository.findByReservationId("res123")).thenReturn(sampleReservation);

        Optional<Reservation> found = reservationService.getReservationById("res123");
        assertTrue(found.isPresent());
        assertEquals("res123", found.get().getReservationId());
    }

    @Test
    void testGetReservationById_NotFound() {
        when(reservationRepository.findByReservationId("res999")).thenReturn(null);

        Optional<Reservation> found = reservationService.getReservationById("res999");
        assertFalse(found.isPresent());
    }

    @Test
    void testGetReservationsByCustomer() {
        when(reservationRepository.findByCustomerId("cust001"))
                .thenReturn(List.of(sampleReservation));

        List<Reservation> reservations = reservationService.getReservationsByCustomer("cust001");
        assertEquals(1, reservations.size());
    }

    @Test
    void testDeleteReservation_Exists() {
        when(reservationRepository.existsByReservationId("res123")).thenReturn(true);
        doNothing().when(reservationRepository).deleteByReservationId("res123");

        boolean deleted = reservationService.deleteReservation("res123");
        assertTrue(deleted);
        verify(reservationRepository, times(1)).deleteByReservationId("res123");
    }

    @Test
    void testDeleteReservation_NotFound() {
        when(reservationRepository.existsByReservationId("res999")).thenReturn(false);

        boolean deleted = reservationService.deleteReservation("res999");
        assertFalse(deleted);
    }

    @Test
    void testGetUpcomingReservationsForTable() {
        when(reservationRepository.findByTableIdAndDateAfter(anyInt(), any(Date.class)))
                .thenReturn(List.of(sampleReservation));

        List<Reservation> results = reservationService.getUpcomingReservationsForTable(1);
        assertEquals(1, results.size());
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(sampleReservation));

        List<Reservation> results = reservationService.getAllReservations();
        assertEquals(1, results.size());
    }
}
