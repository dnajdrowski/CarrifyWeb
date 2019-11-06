package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
}
