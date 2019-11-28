package com.carrify.web.carrifyweb.repository.Rent;

import com.carrify.web.carrifyweb.repository.Car.Car;
import com.carrify.web.carrifyweb.repository.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    @JsonBackReference
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public Rent(Double distance, Integer amount, LocalDateTime createdAt, LocalDateTime endAt, Car car, User user) {
        this.distance = distance;
        this.amount = amount;
        this.createdAt = createdAt;
        this.endAt = endAt;
        this.car = car;
        this.user = user;
    }

    public Rent(LocalDateTime createdAt, Integer carId, Integer userId) {
        this.createdAt = createdAt;
        this.car = new Car(carId);
        this.user = new User(userId);
    }
}
