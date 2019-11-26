package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.repository.Car.Car;
import com.carrify.web.carrifyweb.repository.Reservation.Reservation;
import com.carrify.web.carrifyweb.repository.Reservation.ReservationDTO;
import com.carrify.web.carrifyweb.repository.User.User;
import com.carrify.web.carrifyweb.request.ReservationRequest;
import com.carrify.web.carrifyweb.service.CarService;
import com.carrify.web.carrifyweb.service.ReservationService;
import com.carrify.web.carrifyweb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;

@Slf4j
@Controller
@RequestMapping("api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;
    private final CarService carService;

    public ReservationController(ReservationService reservationService, UserService userService, CarService carService) {
        this.reservationService = reservationService;
        this.userService = userService;
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> showAllReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        if (!reservations.isEmpty()) {
            return ResponseEntity.ok(reservations);
        } else {
            throw new ApiNotFoundException(CARRIFY008_MSG, CARRIFY008_CODE);
        }
    }

    @PostMapping("/new-reservation")
    public ResponseEntity addNewReservation(@Valid @RequestBody ReservationRequest reservationRequest, BindingResult bindingResult) {
        System.out.println(reservationRequest.getCarId());
        System.out.println(reservationRequest.getUserId());
        if (bindingResult.hasErrors()) {
            throw new ApiNotFoundException(CARRIFY001_MSG, CARRIFY001_CODE);
        } else {
            if (userService.existsUserWithUserId(reservationRequest.getUserId())) {
                if (carService.existsCarWithCarId(reservationRequest.getCarId())) {
                    Car car = new Car();
                    car.setId(reservationRequest.getCarId());
                    User user = new User();
                    user.setUserId(reservationRequest.getUserId());
                    Reservation reservation = new Reservation(1, 1, LocalDateTime.now(),
                            LocalDateTime.now().plusMinutes(15), car, user);
                    reservationService.addNewReservation(reservation);
                    return ResponseEntity.ok().build();
                } else {
                    throw new ApiNotFoundException(CARRIFY001_MSG, CARRIFY001_CODE);
                }
            } else {
                throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
            }
        }
    }

    @GetMapping("/user/{id}/all")
    public ResponseEntity<List<ReservationDTO>> showAllUserReservations(@PathVariable("id") String id) {
        int userId;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }
        if (userService.existsUserWithUserId(userId)) {
            List<ReservationDTO> reservations = reservationService.getAllUserReservations(userId);
            if (!reservations.isEmpty()) {
                return ResponseEntity.ok(reservations);
            } else {
                throw new ApiNotFoundException(CARRIFY008_MSG, CARRIFY008_CODE);
            }
        } else {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }
    }

    @GetMapping("/car/{id}/all")
    public ResponseEntity<List<ReservationDTO>> showAllCarReservations(@PathVariable("id") String id) {
        int carId;
        try {
            carId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY001_MSG, CARRIFY001_CODE);
        }
        if (userService.existsUserWithUserId(carId)) {
            List<ReservationDTO> reservations = reservationService.getAllCarReservations(carId);
            if (!reservations.isEmpty()) {
                return ResponseEntity.ok(reservations);
            } else {
                throw new ApiNotFoundException(CARRIFY008_MSG, CARRIFY008_CODE);
            }
        } else {
            throw new ApiNotFoundException(CARRIFY001_MSG, CARRIFY001_CODE);
        }
    }
}
