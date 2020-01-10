package com.carrify.web.carrifyweb.controller;


import com.carrify.web.carrifyweb.model.Car.CarDTO;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import com.carrify.web.carrifyweb.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@Api(tags = "Cars")
@RequestMapping("api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @ApiOperation(value = "View a list of available cars", response = CarDTO.class, responseContainer = "List",
            produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY001_CODE + "\n" + "msg: " + CARRIFY001_MSG,
                    response = ApiErrorResponse.class)
    })
    @GetMapping
    public ResponseEntity<List<CarDTO>> showAllCarsFleet() {
        return ResponseEntity.ok(carService.getAllCars());
    }
}
