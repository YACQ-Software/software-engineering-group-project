package com.yacq.software.qmul_project.controller;

import com.yacq.software.qmul_project.model.Reservation;
import com.yacq.software.qmul_project.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    // Get all reservations
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Get reservations by customer ID
    @GetMapping("/customer/{customerId}")
    public List<Reservation> getReservationsByCustomer(@PathVariable String customerId) {
        return reservationRepository.findByCustomerId(customerId);
    }

    // Create a new reservation
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    // Cancel a reservation
    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable String id) {
        reservationRepository.deleteById(id);
    }
}
