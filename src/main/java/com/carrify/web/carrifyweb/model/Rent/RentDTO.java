package com.carrify.web.carrifyweb.model.Rent;

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
    private Integer carId;

    public RentDTO(Rent rent) {
        this.id = rent.getId();
        this.distance = rent.getDistance();
        this.amount = rent.getAmount();
        this.createdAt = rent.getCreatedAt().toString();
        if(rent.getEndAt() != null)
            this.endAt = rent.getEndAt().toString();
        this.userId = rent.getUser().getId();
        this.carId = rent.getCar().getId();
    }
}
