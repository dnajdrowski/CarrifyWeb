package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.Car.Car;
import com.carrify.web.carrifyweb.model.Variable.Variable;
import com.carrify.web.carrifyweb.repository.CarRepository;
import com.carrify.web.carrifyweb.model.Rent.Rent;
import com.carrify.web.carrifyweb.model.Rent.RentDTO;
import com.carrify.web.carrifyweb.repository.RentRepository;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.repository.UserRepository;
import com.carrify.web.carrifyweb.repository.VariableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
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
    private final UserRepository userRepository;
    private final VariableRepository variableRepository;

    public RentService(RentRepository rentRepository, CarRepository carRepository, UserRepository userRepository, VariableRepository variableRepository) {
        this.rentRepository = rentRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.variableRepository = variableRepository;
    }

    @Transactional
    public List<RentDTO> getAllRents() {
        Iterable<Rent> rents = rentRepository.findAll();
        List<RentDTO> rentsCollected = StreamSupport.stream(rents.spliterator(), false)
                .map(RentDTO::new)
                .collect(toList());

        if (rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    @Transactional
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

        if (rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    @Transactional
    public RentDTO getUserActiveRent(String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<Rent> rentOptional = rentRepository.findFirstByUser_IdAndEndAtIsNull(id);
        if (rentOptional.isPresent()) {
            return new RentDTO(rentOptional.get());
        } else {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
    }

    @Transactional
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

        if (rentsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY012_MSG, CARRIFY012_CODE);
        }
        return rentsCollected;
    }

    @Transactional
    public RentDTO getRentById(String rentId) {
        int id;
        try {
            id = Integer.parseInt(rentId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Optional<Rent> rent = rentRepository.findById(id);
        if (rent.isPresent()) {
            return new RentDTO(rent.get());
        } else {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }
    }

    @Transactional
    public RentDTO finishRent(String rentId) {
        int id;
        try {
            id = Integer.parseInt(rentId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Optional<Rent> rentOptional = rentRepository.findById(id);
        if (rentOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY014_MSG, CARRIFY014_CODE);
        }

        Rent rent = rentOptional.get();

        if (rent.getEndAt() != null) {
            throw new ApiBadRequestException(CARRIFY015_MSG, CARRIFY015_CODE);
        }

        Optional<Car> carOptional = carRepository.findById(rent.getCar().getId());

        if (carOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY013_MSG, CARRIFY013_CODE);
        }

        Optional<Variable> pricePerMinuteOptional = variableRepository.findByName("pricePerMinute");

        if(pricePerMinuteOptional.isEmpty()) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        Optional<Variable> pricePerKmOptional = variableRepository.findByName("pricePerKm");

        if(pricePerKmOptional.isEmpty()) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        int pricePerMinute;
        int pricePerKm;

        try {
            pricePerKm = Integer.parseInt(pricePerKmOptional.get().getValue());
            pricePerMinute = Integer.parseInt(pricePerMinuteOptional.get().getValue());
        } catch (NumberFormatException e) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        Car car = carOptional.get();
        car.setCarState(1);
        carRepository.save(car);

        LocalDateTime rentalStartTime = rent.getCreatedAt();
        LocalDateTime rentalFinishTime = LocalDateTime.now();
        long rentTimeInMinutes = Duration.between(rentalStartTime, rentalFinishTime).toMinutes();
        rent.setEndAt(rentalFinishTime);
        rent.setDistance(2000);
        rent.setAmount(2000 / 1000 * pricePerKm + (int) rentTimeInMinutes * pricePerMinute);
        rentRepository.save(rent);

        return new RentDTO(rent);
    }

    @Transactional
    public RentDTO startRent(Integer userId, Integer carId) {

        if (carRepository.findById(carId).isEmpty()) {
            throw new ApiNotFoundException(CARRIFY013_MSG, CARRIFY013_CODE);
        }

        if (userRepository.findById(userId).isEmpty()) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }
        if (rentRepository.findFirstByUser_IdAndEndAtIsNull(userId).isPresent()) {
            throw new ApiBadRequestException(CARRIFY016_MSG, CARRIFY016_CODE);
        }

        if (rentRepository.findFirstByCar_IdAndEndAtIsNull(carId).isPresent()) {
            throw new ApiBadRequestException(CARRIFY017_MSG, CARRIFY017_CODE);
        }

        Rent rent = Rent.builder()
                .distance(0)
                .createdAt(LocalDateTime.now())
                .car(Car.builder().id(carId).build())
                .user(User.builder().id(userId).build())
                .build();

        Rent savedRent = rentRepository.save(rent);
        if (savedRent == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }
        return new RentDTO(savedRent);
    }
}
