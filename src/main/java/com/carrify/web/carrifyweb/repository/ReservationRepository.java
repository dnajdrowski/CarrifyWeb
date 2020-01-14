package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.Reservation.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Iterable<Reservation> findAllByUser_Id(Integer id);
    Iterable<Reservation> findAllByCar_Id(Integer id);

}
