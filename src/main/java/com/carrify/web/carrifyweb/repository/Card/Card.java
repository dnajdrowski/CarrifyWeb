package com.carrify.web.carrifyweb.repository.Card;

import com.carrify.web.carrifyweb.repository.User.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Integer id;

    @Column(name = "card_number", nullable = false, unique = true)
    private String cardNumber;

    @Column(name = "expire_year", nullable = false, length = 4)
    private String expireYear;

    @Column(name = "expire_month", nullable = false, length = 2)
    private String expireMonth;

    @Column(name = "card_cvv", nullable = false, length = 3)
    private String cardCVV;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

}
