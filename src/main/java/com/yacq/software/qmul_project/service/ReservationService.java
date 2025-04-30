package com.yacq.software.qmul_project.service;

import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.reservation.ReservationStatus;
import com.yacq.software.qmul_project.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public boolean isTableAvailable(int tableId, Date date) {
        List<Reservation> reservations = reservationRepository.findByTableIdAndDate(tableId, date);
        return reservations.isEmpty();
    }

    //  CREATE a reservation
    public Reservation createReservation(Reservation reservation) {
        // Auto-generate ID if not set
        if (reservation.getReservationId() == null || reservation.getReservationId().isEmpty()) {
            reservation.setReservationId(UUID.randomUUID().toString());
        }

        // Set creation time
        reservation.setCreatedAt(new Date());

        // Set default status if not set
        if (reservation.getStatus() == null) {
            reservation.setStatus(ReservationStatus.PROCESSING);
        }

        return reservationRepository.save(reservation);
    }

    //  GET reservation by ID
    public Optional<Reservation> getReservationById(String reservationId) {
        return Optional.ofNullable(reservationRepository.findByReservationId(reservationId));
    }

    //  GET all reservations for a customer
    public List<Reservation> getReservationsByCustomer(String customerId) {
        return reservationRepository.findByCustomerId(customerId);
    }

    //  GET all reservations for a table on a specific date
    public List<Reservation> getReservationsByTableAndDate(int tableId, Date date) {
        return reservationRepository.findByTableIdAndDate(tableId, date);
    }

    //  DELETE a reservation
    public boolean deleteReservation(String reservationId) {
        if (reservationRepository.existsByReservationId(reservationId)) {
            reservationRepository.deleteByReservationId(reservationId);
            return true;
        }
        return false;
    }

    //  GET all upcoming reservations for a table
    public List<Reservation> getUpcomingReservationsForTable(int tableId) {
        return reservationRepository.findByTableIdAndDateAfter(tableId, new Date());
    }

    //  GET all reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}
