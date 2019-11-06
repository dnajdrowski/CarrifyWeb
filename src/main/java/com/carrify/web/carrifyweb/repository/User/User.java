package com.carrify.web.carrifyweb.repository.User;

import com.carrify.web.carrifyweb.repository.Card.Card;
import com.carrify.web.carrifyweb.repository.Role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Integer userId;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "Email", nullable = false, unique = true, length = 64)
    private String email;
    @Column(name = "phone", length = 9, unique = true)
    private String phone;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Card card;

}

