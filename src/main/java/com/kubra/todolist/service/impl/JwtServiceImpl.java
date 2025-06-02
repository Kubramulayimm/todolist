package com.kubra.todolist.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.kubra.todolist.model.User;
import com.kubra.todolist.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.bearer}")
    private String bearer;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                  .withSubject(user.getEmail())
                  .withIssuedAt(new Date())
                  .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                  .sign(algorithm);
    }

}

