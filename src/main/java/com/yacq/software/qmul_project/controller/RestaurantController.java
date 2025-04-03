package com.yacq.software.qmul_project.controller;

import com.yacq.software.qmul_project.model.Customer;
import com.yacq.software.qmul_project.repositories.CustomerRepository;
import com.yacq.software.qmul_project.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/greeting")
    public String getGreeting() {
        return restaurantService.getGreeting();
    }


    // GET /api/get-customers
    @GetMapping("/get-customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // POST /api/create-customer
    @PostMapping("/create-customer")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
}