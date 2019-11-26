package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.repository.CarLocationLog.CarLocationLog;
import com.carrify.web.carrifyweb.repository.CarLocationLog.CarLocationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class CarLocationLogService {

    private final CarLocationLogRepository carLocationLogRepository;

    public CarLocationLogService(CarLocationLogRepository carLocationLogRepository) {
        this.carLocationLogRepository = carLocationLogRepository;
    }

    public Optional<CarLocationLog> findLastCarLocationLog(Integer carId) {
        return carLocationLogRepository.findTopByCar_IdOrderByIdDesc(carId);
    }
}
