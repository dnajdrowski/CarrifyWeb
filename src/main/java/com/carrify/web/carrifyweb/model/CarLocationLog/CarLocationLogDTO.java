package com.carrify.web.carrifyweb.model.CarLocationLog;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarLocationLogDTO {

    private Integer id;
    private Double latitude;
    private Double longitude;
    private String createdAt;


    public CarLocationLogDTO(CarLocationLog carLocationLog) {
        this.id = carLocationLog.getId();
        this.latitude = carLocationLog.getLatitude();
        this.longitude = carLocationLog.getLongitude();
        this.createdAt = carLocationLog.getCreatedAt().toString();
    }
}
