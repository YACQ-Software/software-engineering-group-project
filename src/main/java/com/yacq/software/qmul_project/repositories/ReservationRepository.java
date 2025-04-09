package com.yacq.software.qmul_project.repositories;

import com.yacq.software.qmul_project.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    List<Reservation> findByCustomerId(String customerId);

}
