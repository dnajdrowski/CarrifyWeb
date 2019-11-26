package com.carrify.web.carrifyweb.service;


import com.carrify.web.carrifyweb.repository.Reservation.Reservation;
import com.carrify.web.carrifyweb.repository.Reservation.ReservationDTO;
import com.carrify.web.carrifyweb.repository.Reservation.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

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
        return StreamSupport.stream(reservations.spliterator(), false)
                .map(ReservationDTO::new)
                .collect(toList());
    }

    public Reservation addNewReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<ReservationDTO> getAllUserReservations(Integer userId) {
        Iterable<Reservation> reservations = reservationRepository.findAllByUserUserId(userId);
        return StreamSupport.stream(reservations.spliterator(), false)
                .map(ReservationDTO::new)
                .collect(toList());
    }

    public List<ReservationDTO> getAllCarReservations(Integer carId) {
        Iterable<Reservation> reservations = reservationRepository.findAllByCar_Id(carId);
        return StreamSupport.stream(reservations.spliterator(), false)
                .map(ReservationDTO::new)
                .collect(toList());
    }
}
