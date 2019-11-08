package com.carrify.web.carrifyweb.repository.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    User save(User user);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPersonalNumber(String personalNumber);
    boolean existsByPhone(String phone);
}
