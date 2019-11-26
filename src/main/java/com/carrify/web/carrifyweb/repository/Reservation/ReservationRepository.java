package com.carrify.web.carrifyweb.repository.Reservation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Iterable<Reservation> findAllByUserUserId(Integer id);
    Iterable<Reservation> findAllByCar_Id(Integer id);

}
