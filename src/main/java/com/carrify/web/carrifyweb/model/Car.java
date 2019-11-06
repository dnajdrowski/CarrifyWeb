package com.carrify.web.carrifyweb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "last_sync")
    private Date lastSync;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(name = "service_mode", nullable = false)
    private Integer serviceMode;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "last_service")
    private LocalDate lastService;
    @Column(name = "car_state", nullable = false)
    private Integer carState;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<CarLocationLog> carLocationLogs = new ArrayList<>();

}
