package com.carrify.web.carrifyweb.model.Reservation;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationDTO {

    private Integer id;
    private Integer state;
    private Integer canExtend;
    private String createdAt;
    private String finishedAt;
    private Integer carId;
    private Integer userId;

    public ReservationDTO(Reservation reservation) {
        this.id = reservation.getId();
        this.state = reservation.getState();
        this.canExtend = reservation.getCanExtend();
        this.createdAt = reservation.getCreatedAt().toString();
        this.finishedAt = reservation.getFinishedAt().toString();
        this.carId = reservation.getCar().getId();
        this.userId = reservation.getUser().getId();
    }
}
