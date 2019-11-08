package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.repository.User.User;
import com.carrify.web.carrifyweb.repository.User.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsUserWithPhoneNumber(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public boolean existsUserWithPersonalNumber(String personalNumber) {
        return userRepository.existsByPersonalNumber(personalNumber);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
