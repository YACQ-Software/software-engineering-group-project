package com.yacq.software.qmul_project;

import com.yacq.software.qmul_project.controller.ReservationController;
import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationRepository reservationRepository;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    void setUp() {
        reservation1 = new Reservation();
        reservation1.setReservationId("res1");
        reservation1.setCustomerId("cust1");

        reservation2 = new Reservation();
        reservation2.setReservationId("res2");
        reservation2.setCustomerId("cust1");
    }

    @Test
    void getAllReservations_shouldReturnList() throws Exception {
        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);
        when(reservationRepository.findAll()).thenReturn(reservations);

        mockMvc.perform(get("/api/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getReservationsByCustomer_shouldReturnCustomerReservations() throws Exception {
        when(reservationRepository.findByCustomerId("cust1")).thenReturn(Arrays.asList(reservation1, reservation2));

        mockMvc.perform(get("/api/reservations/customer/cust1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void createReservation_shouldSaveAndReturnReservation() throws Exception {
        Reservation newReservation = new Reservation();
        newReservation.setReservationId("res3");
        newReservation.setCustomerId("cust2");

        when(reservationRepository.save(any(Reservation.class))).thenReturn(newReservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("res3"));
    }

    @Test
    void cancelReservation_shouldCallDeleteById() throws Exception {
        doNothing().when(reservationRepository).deleteById("res1");

        mockMvc.perform(delete("/api/reservations/res1"))
                .andExpect(status().isOk());

        verify(reservationRepository, times(1)).deleteById("res1");
    }
}
