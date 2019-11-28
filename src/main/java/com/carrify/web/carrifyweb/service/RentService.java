package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.repository.Car.Car;
import com.carrify.web.carrifyweb.repository.Car.CarRepository;
import com.carrify.web.carrifyweb.repository.Rent.Rent;
import com.carrify.web.carrifyweb.repository.Rent.RentDTO;
import com.carrify.web.carrifyweb.repository.Rent.RentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static java.util.stream.Collectors.toList;


@Service
public class RentService {

    private final RentRepository rentRepository;
    private final CarRepository carRepository;

    public RentService(RentRepository rentRepository, CarRepository carRepository) {
        this.rentRepository = rentRepository;
        this.carRepository = carRepository;
    }

    public List<RentDTO> getAllRents() {
        Iterable<Rent> rents = rentRepository.findAll();
        List<RentDTO> rentsCollected = StreamSupport.stream(rents.spliterator(), false)
                .map(RentDTO::new)
                .collect(toList());

        if(rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    public List<RentDTO> getAllUserRents(String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
           throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }
        Iterable<Rent> rents = rentRepository.findAllByUser_Id(id);
        List<RentDTO> rentsCollected = StreamSupport.stream(rents.spliterator(), false)
                .map(RentDTO::new)
                .collect(toList());

        if(rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    public RentDTO getUserActiveRent(String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }
        Optional<Rent> rentOptional = rentRepository.findFirstByUser_IdAndEndAtIsNull(id);
        if(rentOptional.isPresent()) {
            return new RentDTO(rentOptional.get());
        } else {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
    }

    public List<RentDTO> getAllCarRents(String carId) {
        int id;
        try {
            id = Integer.parseInt(carId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY013_MSG, CARRIFY013_CODE);
        }
        Iterable<Rent> rents = rentRepository.findAllByCar_Id(id);
        List<RentDTO> rentsCollected = StreamSupport.stream(rents.spliterator(), false)
                .map(RentDTO::new)
                .collect(toList());

        if(rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    public RentDTO getRentById(String rentId) {
        int id;
        try {
            id = Integer.parseInt(rentId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Optional<Rent> rent = rentRepository.findById(id);
        if(rent.isPresent()) {
            return new RentDTO(rent.get());
        } else {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }
    }

    public RentDTO finishRent(String rentId) {
        int id;
        try {
            id = Integer.parseInt(rentId);
        } catch (NumberFormatException e){
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Optional<Rent> rentOptional = rentRepository.findById(id);
        if(rentOptional.isPresent()) {
            Rent rent = rentOptional.get();
            if (rent.getEndAt() == null) {
                Optional<Car> carOptional = carRepository.findById(rent.getCar().getId());
                if(carOptional.isPresent()) {
                    Car car = carOptional.get();
                    car.setCarState(1);
                    carRepository.save(car);
                    rent.setEndAt(LocalDateTime.now());
                    rent.setDistance(1100.0);
                    rent.setAmount(1100 * 15 + 20);
                    rentRepository.save(rent);
                    return new RentDTO(rent);
                } else {
                    throw new ApiNotFoundException(CARRIFY013_MSG, CARRIFY013_CODE);
                }
            } else {
                throw new ApiBadRequestException(CARRIFY015_MSG, CARRIFY015_CODE);
            }
        } else {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }
    }

    public RentDTO startRent(Integer userId, Integer carId) {
        if(rentRepository.findFirstByUser_IdAndEndAtIsNull(userId).isPresent()) {
            throw new ApiBadRequestException(CARRIFY016_MSG, CARRIFY016_CODE);
        }

        if(rentRepository.findFirstByCar_IdAndEndAtIsNull(carId).isPresent()) {
            throw new ApiBadRequestException(CARRIFY017_MSG, CARRIFY017_CODE);
        }

        Rent rent = new Rent(LocalDateTime.now(), carId, userId);
        rent.setDistance(0.0);
        Rent savedRent = rentRepository.save(rent);
        if(savedRent == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }
        return new RentDTO(savedRent);
    }
}
