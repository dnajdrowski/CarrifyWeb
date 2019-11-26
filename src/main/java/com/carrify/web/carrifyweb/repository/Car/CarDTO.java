package com.carrify.web.carrifyweb.repository.Car;
import com.carrify.web.carrifyweb.repository.CarLocationLog.CarLocationLogDTO;
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
    private String registrationNumber;
    private Integer serviceMode;
    private Integer mileage;
    private String lastService;
    private Integer carState;
    private CarLocationLogDTO lastLocation;

    public CarDTO(Car car) {
        this.id = car.getId();
        this.name = car.getName();
        this.fuelLevel = car.getFuelLevel();
        this.registrationNumber = car.getRegistrationNumber();
        this.serviceMode = car.getServiceMode();
        this.lastLocation = new CarLocationLogDTO(car.getLastLocation());
        this.mileage = car.getMileage();
        this.lastService = car.getLastService().toString();
        this.carState = car.getCarState();
    }
}
