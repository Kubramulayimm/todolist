package com.kubra.todolist.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import({WebSecurityConfig.class, JWTValidatorFilter.class})
class WebSecurityConfigTest {

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    private JWTValidatorFilter jwtValidatorFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Test
    void testPasswordEncoderBean() {
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.matches("test", passwordEncoder.encode("test")));
    }

    @Test
    void testAuthenticationManagerBean() throws Exception {
        assertNotNull(authenticationManager);
    }

}