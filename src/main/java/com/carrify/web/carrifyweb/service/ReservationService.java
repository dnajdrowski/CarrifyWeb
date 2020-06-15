package com.carrify.web.carrifyweb.service;


import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.Reservation.Reservation;
import com.carrify.web.carrifyweb.model.Reservation.ReservationDTO;
import com.carrify.web.carrifyweb.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY008_CODE;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY008_MSG;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationDTO> getAllReservations() {
        Iterable<Reservation> reservations = reservationRepository.findAll();
        List<ReservationDTO> reservationsCollected = StreamSupport.stream(reservations.spliterator(), false)
                .map(ReservationDTO::new)
                .collect(toList());

        if (reservationsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY008_MSG, CARRIFY008_CODE);
        }
        return reservationsCollected;
    }

    public Reservation addNewReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation getUserReservation(Integer userId) {
        Optional<Reservation> optionalReservation = reservationRepository.findOneByUserId(userId);
        if (optionalReservation.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY008_MSG, CARRIFY008_CODE);
        }
        return optionalReservation.get();
    }

    public List<ReservationDTO> getAllCarReservations(Integer carId) {
        Iterable<Reservation> reservations = reservationRepository.findAllByCar_Id(carId);
        List<ReservationDTO> reservationsCollected = StreamSupport.stream(reservations.spliterator(), false)
                .map(ReservationDTO::new)
                .collect(toList());

        if (reservationsCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY008_MSG, CARRIFY008_CODE);
        }
        return reservationsCollected;
    }

    public boolean existsReservationOnCarOrUser(Integer carId, Integer userId) {
        Optional<Reservation> optionalReservation = reservationRepository.getActiveByCarIdOrUserId(carId, userId, LocalDateTime.now());
        return optionalReservation.isEmpty();
    }

    public boolean existsReservationOnOtherCarByUserId(Integer userId, Integer carId) {
        Optional<Reservation> optionalReservation = reservationRepository.findActiveReservationByUserId(userId, carId, LocalDateTime.now());
        return optionalReservation.isPresent();
    }
}
