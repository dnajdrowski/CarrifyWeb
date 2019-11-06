package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.repository.Car.Car;
import com.carrify.web.carrifyweb.repository.Car.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return StreamSupport.stream(carRepository.findAll().spliterator(), false)
                .collect(toList());
    }
}
