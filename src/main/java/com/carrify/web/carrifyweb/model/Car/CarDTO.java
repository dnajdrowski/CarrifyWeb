package com.carrify.web.carrifyweb.model.Car;

import com.carrify.web.carrifyweb.formatter.CarrifyDateTimeFormatter;
import com.carrify.web.carrifyweb.model.CarLocationLog.CarLocationLogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
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
        if(car.getLastLocation() != null)
            this.lastLocation = new CarLocationLogDTO(car.getLastLocation());
        this.mileage = car.getMileage();
        this.lastService = CarrifyDateTimeFormatter.formatDate(car.getLastService());
        this.carState = car.getCarState();
    }
}
