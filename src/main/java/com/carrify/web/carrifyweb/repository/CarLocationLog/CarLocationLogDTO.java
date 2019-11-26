package com.carrify.web.carrifyweb.repository.CarLocationLog;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CarLocationLogDTO {

    private Integer id;
    private Double latitude;
    private Double longitude;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;


    public CarLocationLogDTO(CarLocationLog carLocationLog) {
        this.id = carLocationLog.getId();
        this.latitude = carLocationLog.getLatitude();
        this.longitude = carLocationLog.getLongitude();
        this.createdAt = carLocationLog.getCreatedAt();
    }
}
