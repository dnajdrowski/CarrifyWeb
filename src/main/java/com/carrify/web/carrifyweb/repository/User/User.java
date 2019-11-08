package com.carrify.web.carrifyweb.repository.User;

import com.carrify.web.carrifyweb.repository.CarLocationLog.CarLocationLog;
import com.carrify.web.carrifyweb.repository.Card.Card;
import com.carrify.web.carrifyweb.repository.Role.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Integer userId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "personal_number", nullable = false, unique = true)
    private String personaNumber;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "Email", nullable = false, unique = true, length = 64)
    private String email;

    @Column(name = "phone", length = 9, unique = true)
    private String phone;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonManagedReference
    private List<Card> cards = new ArrayList<>();

}

