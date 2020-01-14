package com.carrify.web.carrifyweb.model.User;

import com.carrify.web.carrifyweb.model.Card.Card;
import com.carrify.web.carrifyweb.model.DriverLicence.DriverLicence;
import com.carrify.web.carrifyweb.model.Rent.Rent;
import com.carrify.web.carrifyweb.model.Reservation.Reservation;
import com.carrify.web.carrifyweb.model.Role.Role;
import com.carrify.web.carrifyweb.model.Wallet.Wallet;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "personal_number", nullable = false, unique = true)
    private String personalNumber;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "email", nullable = false, unique = true, length = 64)
    private String email;

    @Column(name = "phone", length = 9, unique = true)
    private String phone;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "verified")
    private Integer verified;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<Card> cards = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<Rent> rents = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<Wallet> wallets = new ArrayList<>();

    public User(int id) {
        this.id = id;
    }
}

