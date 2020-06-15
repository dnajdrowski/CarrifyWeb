package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.Reservation.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
    Iterable<Reservation> findAllByCar_Id(Integer id);

    Optional<Reservation> findOneByUserId(Integer userId);

    @Query(value = "SELECT r.* FROM reservation r WHERE (r.car_id = ?1 or r.user_id = ?2) and ?3 between r.created_at and r.finished_at", nativeQuery = true)
    Optional<Reservation> getActiveByCarIdOrUserId(Integer carId, Integer userId, LocalDateTime now);

    @Query(value = "SELECT r.* FROM reservation r WHERE r.car_id <> ?1 and r.user_id = ?2 and ?3 between r.created_at and r.finished_at", nativeQuery = true)
    Optional<Reservation> findActiveReservationByUserId(Integer userId, Integer carId, LocalDateTime now);

}
