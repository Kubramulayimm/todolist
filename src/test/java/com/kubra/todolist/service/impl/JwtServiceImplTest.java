package com.kubra.todolist.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kubra.todolist.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceImplTest {

    private JwtServiceImpl jwtService;

    private final String secret = "testSecretKey123456789";
    private final long expiration = 3600000L; // 1 saat

    private User testUser;

    @BeforeEach
    void setUp() {
        jwtService = new JwtServiceImpl();

        ReflectionTestUtils.setField(jwtService, "secret", secret);
        ReflectionTestUtils.setField(jwtService, "expiration", expiration);

        testUser = User.builder()
                .id("1")
                .email("test@example.com")
                .password("pass")
                .build();
    }

    @Test
    void testGenerateToken_containsCorrectSubject() {
        String token = jwtService.generateToken(testUser);

        assertNotNull(token);

        DecodedJWT decodedJWT = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC256(secret.getBytes()))
                .build()
                .verify(token);

        assertEquals(testUser.getEmail(), decodedJWT.getSubject());
        assertNotNull(decodedJWT.getIssuedAt());
        assertNotNull(decodedJWT.getExpiresAt());
    }
}