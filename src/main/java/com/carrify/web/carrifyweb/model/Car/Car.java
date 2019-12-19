package com.carrify.web.carrifyweb.model.Car;

import com.carrify.web.carrifyweb.model.CarLocationLog.CarLocationLog;
import com.carrify.web.carrifyweb.model.Rent.Rent;
import com.carrify.web.carrifyweb.model.Reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "fuel_level", nullable = false)
    private Integer fuelLevel;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "service_mode", nullable = false)
    private Integer serviceMode;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "last_service")
    private LocalDateTime lastService;

    @Column(name = "car_state", nullable = false)
    private Integer carState;

    @Builder.Default
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<CarLocationLog> carLocationLogs = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<Rent> rents = new ArrayList<>();

    @Transient
    private CarLocationLog lastLocation;

}
