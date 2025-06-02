package com.kubra.todolist.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTValidatorFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header}")
    private String authHeader;

    @Value("${jwt.bearer}")
    private String bearer;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    jakarta.servlet.http.HttpServletResponse response,
                                    jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(authHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith(bearer + " ")) {
            try {
                String token = authorizationHeader.substring(bearer.length() + 1);
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
                JWTVerifier verifier = JWT.require(algorithm)
                        .build();
                com.auth0.jwt.interfaces.DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext()
                        .setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            } catch (JWTVerificationException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setHeader("error", e.getMessage());
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
