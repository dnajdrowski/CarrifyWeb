package com.carrify.web.carrifyweb.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(2)
                .email("daniel.najder@@wp.pl")
                .build();
    }

    @Test
    void checkValidUserId() {
        Integer expected = 3;
        assertEquals(expected, user.getId());
    }

    @Test
    void checkInvalidEmail() {
        String validEmail = "daniel.najder@wp.pl";
        assertNotEquals(validEmail, user.getEmail());
    }
}