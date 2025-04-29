package com.yacq.software.qmul_project.repositories;

import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.reservation.ReservationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    // Find by reservation ID
    Reservation findByReservationId(String reservationId);

    // Find all reservations for a specific table
    List<Reservation> findByTableId(int tableId);

    // Find all reservations for a specific customer
    List<Reservation> findByCustomerId(String customerId);

    // Find all reservations for a specific date
    List<Reservation> findByDate(Date date);

    // Find reservations within a date range
    List<Reservation> findByDateBetween(Date start, Date end);

    // Find reservations for a table on a specific date
    List<Reservation> findByTableIdAndDate(int tableId, Date date);

    // Find reservations by status
    List<Reservation> findByStatus(ReservationStatus status);

    // Find all upcoming reservations for a table
    List<Reservation> findByTableIdAndDateAfter(int tableId, Date afterDate);

    // Delete reservation by ID
    void deleteByReservationId(String reservationId);

    // Check if a reservation exists
    boolean existsByReservationId(String reservationId);

}
