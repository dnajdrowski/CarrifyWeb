package com.carrify.web.carrifyweb.repository.CarLocationLog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarLocationLogRepository extends CrudRepository<CarLocationLog, Integer> {
    Optional<CarLocationLog> findTopByCar_IdOrderByIdDesc(Integer carId);
}
