package com.carrify.web.carrifyweb.repository.Reservation;

import com.carrify.web.carrifyweb.repository.Car.Car;
import com.carrify.web.carrifyweb.repository.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "state", nullable = false)
    private Integer state;

    @Column(name = "can_extend", nullable = false)
    private Integer canExtend;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "finished_at", nullable = false)
    private LocalDateTime finishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;



    public Reservation(Integer state, Integer canExtend, LocalDateTime createdAt, LocalDateTime finishedAt, Car car, User user) {
        this.state = state;
        this.canExtend = canExtend;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.car = car;
        this.user = user;
    }
}
