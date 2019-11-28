package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.repository.User.User;
import com.carrify.web.carrifyweb.repository.User.UserRepository;
import com.carrify.web.carrifyweb.exception.ApiErrorConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsUserWithPhoneNumber(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public boolean existsUserWithPersonalNumber(String personalNumber) { return userRepository.existsByPersonalNumber(personalNumber);}

    public boolean existsUserWithUserId(Integer userId) {
        return userRepository.existsById(userId);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User saveUserToken(Integer userId, String token) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new ApiNotFoundException(ApiErrorConstants.CARRIFY009_MSG, ApiErrorConstants.CARRIFY009_CODE));

        user.setToken(token);
        return userRepository.save(user);
    }

    public boolean checkIfTokenMatchesToUser(String token, Integer userId) {
        Optional<User> user = userRepository.findByToken(token);
        return user.filter(value -> userId.equals(value.getId())).isPresent();
    }
}
