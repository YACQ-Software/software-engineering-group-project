package com.yacq.software.qmul_project.controller;

import com.yacq.software.qmul_project.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/greeting")
    public String getGreeting() {
        return restaurantService.getGreeting();
    }
}