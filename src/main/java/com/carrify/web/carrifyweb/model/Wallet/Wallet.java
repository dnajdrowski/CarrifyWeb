package com.carrify.web.carrifyweb.model.Wallet;

import com.carrify.web.carrifyweb.model.Transaction.Transaction;
import com.carrify.web.carrifyweb.model.User.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_wallet")
public class Wallet {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "last_transaction")
    private LocalDateTime lastTransaction;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "wallet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Transaction> transactions = new ArrayList<>();

}
