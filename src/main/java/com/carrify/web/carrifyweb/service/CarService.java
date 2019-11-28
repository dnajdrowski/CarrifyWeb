package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.repository.Car.Car;
import com.carrify.web.carrifyweb.repository.Car.CarDTO;
import com.carrify.web.carrifyweb.repository.Car.CarRepository;
import com.carrify.web.carrifyweb.repository.CarLocationLog.CarLocationLog;
import com.carrify.web.carrifyweb.repository.CarLocationLog.CarLocationLogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY001_CODE;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY001_MSG;
import static java.util.stream.Collectors.toList;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final CarLocationLogRepository carLocationLogRepository;

    public CarService(CarRepository carRepository, CarLocationLogRepository carLocationLogRepository) {
        this.carRepository = carRepository;
        this.carLocationLogRepository = carLocationLogRepository;
    }

    public boolean existsCarWithCarId(Integer carId) {
        return carRepository.existsById(carId);
    }

    public List<CarDTO> getAllCars() {
        Iterable<Car> cars = carRepository.findAll();
        List<CarDTO> collectedCars = StreamSupport.stream(cars.spliterator(), false)
                .map(car -> {
                    Optional<CarLocationLog> lastCarLocation = carLocationLogRepository.findTopByCar_IdOrderByIdDesc(car.getId());
                    lastCarLocation.ifPresent(car::setLastLocation);
                    return new CarDTO(car);
                })
                .collect(toList());
        if(collectedCars.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY001_MSG, CARRIFY001_CODE);
        }

        return collectedCars;
    }
}
