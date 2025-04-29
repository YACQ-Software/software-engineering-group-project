package com.yacq.software.qmul_project;

import com.yacq.software.qmul_project.model.Customer;
import com.yacq.software.qmul_project.model.reservation.Reservation;
import com.yacq.software.qmul_project.model.reservation.ReservationStatus;
import com.yacq.software.qmul_project.model.table.Table;
import com.yacq.software.qmul_project.model.table.TablePosition;
import com.yacq.software.qmul_project.model.table.TableSection;
import com.yacq.software.qmul_project.model.table.TableShape;
import com.yacq.software.qmul_project.repositories.CustomerRepository;
import com.yacq.software.qmul_project.repositories.ReservationRepository;
import com.yacq.software.qmul_project.repositories.TableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(
            CustomerRepository customerRepository,
            TableRepository tableRepository,
            ReservationRepository reservationRepository
    ) {
        return args -> {
            if (1 == 1) {
                return;
            }

            // Clear existing data (optional)
            customerRepository.deleteAll();
            tableRepository.deleteAll();
            reservationRepository.deleteAll();

            // Create customers
            Customer customer1 = new Customer();
            customer1.setCustomerId(UUID.randomUUID().toString());
            customer1.setName("Alice Smith");
            customer1.setEmail("alice@example.com");
            customer1.setPhone("1234567890");
            customer1.setPreferences("Vegetarian");

            Customer customer2 = new Customer();
            customer2.setCustomerId(UUID.randomUUID().toString());
            customer2.setName("Bob Johnson");
            customer2.setEmail("bob@example.com");
            customer2.setPhone("0987654321");
            customer2.setPreferences("Quiet seating");

            customerRepository.saveAll(List.of(customer1, customer2));

            // Create tables
            Table table1 = new Table();
            table1.setTableId(101);
            table1.setSize(4);
            table1.setShape(TableShape.RECTANGLE);
            table1.setSection(TableSection.WINDOW);
            TablePosition pos1 = new TablePosition();
            pos1.setX(2);
            pos1.setY(5);
            table1.setPosition(pos1);

            Table table2 = new Table();
            table2.setTableId(102);
            table2.setSize(2);
            table2.setShape(TableShape.CIRCLE);
            table2.setSection(TableSection.CENTRE);
            TablePosition pos2 = new TablePosition();
            pos2.setX(4);
            pos2.setY(3);
            table2.setPosition(pos2);

            Table table3 = new Table();
            table3.setTableId(103);
            table3.setSize(6);
            table3.setShape(TableShape.RECTANGLE);
            table3.setSection(TableSection.EVENT);
            TablePosition pos3 = new TablePosition();
            pos3.setX(1);
            pos3.setY(9);
            table3.setPosition(pos3);

            tableRepository.saveAll(List.of(table1, table2, table3));


            // Create reservations
            Reservation res1 = new Reservation();
            res1.setReservationId(UUID.randomUUID().toString());
            res1.setCustomerId(customer1.getCustomerId());
            res1.setTableId(101);
            res1.setDate(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
            res1.setPartySize(4);
            res1.setStatus(ReservationStatus.CONFIRMED);
            res1.setSpecialRequests("Anniversary setup");
            res1.setCreatedAt(new Date());

            Reservation res2 = new Reservation();
            res2.setReservationId(UUID.randomUUID().toString());
            res2.setCustomerId(customer2.getCustomerId());
            res2.setTableId(102);
            res2.setDate(new Date(System.currentTimeMillis() + 172800000)); // In 2 days
            res2.setPartySize(2);
            res2.setStatus(ReservationStatus.PROCESSING);
            res2.setSpecialRequests("Near the bar");
            res2.setCreatedAt(new Date());

            reservationRepository.saveAll(List.of(res1, res2));

            System.out.println("Database seeded with test customers, tables, and reservations.");
        };
    }
}
