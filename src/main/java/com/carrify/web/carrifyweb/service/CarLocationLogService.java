package com.carrify.web.carrifyweb.service;


import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.CarLocationLog.CarLocationLog;
import com.carrify.web.carrifyweb.model.CarLocationLog.CarLocationLogDTO;
import com.carrify.web.carrifyweb.repository.CarLocationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY011_CODE;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY011_MSG;

@Service
@Slf4j
public class CarLocationLogService {

    private final CarLocationLogRepository carLocationLogRepository;

    public CarLocationLogService(CarLocationLogRepository carLocationLogRepository) {
        this.carLocationLogRepository = carLocationLogRepository;
    }

    public CarLocationLogDTO findLastCarLocationLog(Integer carId) {
        Optional<CarLocationLog> lastCarLocationLog = carLocationLogRepository.findTopByCar_IdOrderByIdDesc(carId);
        if(lastCarLocationLog.isPresent()) {
            return new CarLocationLogDTO(lastCarLocationLog.get());
        } else {
            throw new ApiNotFoundException(CARRIFY011_MSG, CARRIFY011_CODE);
        }
    }
}
