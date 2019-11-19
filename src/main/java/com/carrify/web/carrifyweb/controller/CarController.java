package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.repository.Car.CarDTO;
import com.carrify.web.carrifyweb.exception.ApiErrorConstants;
import com.carrify.web.carrifyweb.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<CarDTO>> showAllCarsFleet(){
        List<CarDTO> cars = carService.getAllCars();
        if(!cars.isEmpty()) {
            return ResponseEntity.ok(cars);
        } else {
            throw new ApiNotFoundException(ApiErrorConstants.CARRIFY001_MSG, ApiErrorConstants.CARRIFY001_CODE);
        }
    }
}
