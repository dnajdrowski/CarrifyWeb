package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.Rent.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentRepository  extends CrudRepository<Rent, Integer> {
    Iterable<Rent> findAllByCar_Id(Integer carId);
    Iterable<Rent> findAllByUser_Id(Integer userId);
    Rent save(Rent rent);
    Optional<Rent> findFirstByUser_IdAndEndAtIsNull(Integer userId);
    Optional<Rent> findFirstByCar_IdAndEndAtIsNull(Integer carId);
}
