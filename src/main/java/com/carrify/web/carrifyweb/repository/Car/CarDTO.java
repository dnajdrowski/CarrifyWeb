package com.carrify.web.carrifyweb.repository.Car;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CarDTO {

    private Integer id;
    private String name;
    private Integer fuelLevel;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSync;
    private String registrationNumber;
    private Integer serviceMode;
    private Integer mileage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastService;
    private Integer carState;

    public CarDTO(Car car) {
        this.id = car.getId();
        this.name = car.getName();
        this.fuelLevel = car.getFuelLevel();
        this.lastSync = car.getLastSync();
        this.registrationNumber = car.getRegistrationNumber();
        this.serviceMode = car.getServiceMode();
        this.mileage = car.getMileage();
        this.lastService = car.getLastService();
        this.carState = car.getCarState();
    }
}
