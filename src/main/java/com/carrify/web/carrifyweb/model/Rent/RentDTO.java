package com.carrify.web.carrifyweb.model.Rent;

import com.carrify.web.carrifyweb.formatter.CarrifyDateTimeFormatter;
import com.carrify.web.carrifyweb.model.Car.CarDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentDTO {

    private Integer id;
    private Integer distance;
    private Integer amount;
    private String createdAt;
    private String endAt;
    private Integer userId;
    private CarDTO car;

    public RentDTO(Rent rent) {
        this.id = rent.getId();
        this.distance = rent.getDistance();
        this.amount = rent.getAmount();
        this.createdAt = CarrifyDateTimeFormatter.formatDate(rent.getCreatedAt());
        if(rent.getEndAt() != null)
            this.endAt = CarrifyDateTimeFormatter.formatDate(rent.getEndAt());
        this.userId = rent.getUser().getId();
        this.car = new CarDTO(rent.getCar());
    }
}
