package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.model.Car;
import com.carrify.web.carrifyweb.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping({"","/"})
    public ResponseEntity<List<Car>> showAllCarsFleet() {
        return ResponseEntity.ok(carService.getAllCars());
    }
}
