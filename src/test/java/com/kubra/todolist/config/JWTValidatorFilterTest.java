package com.kubra.todolist.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JWTValidatorFilterTest {

    private JWTValidatorFilter filter;
    private final String secret = "testsecret1234567890";
    private final String header = "Authorization";
    private final String bearer = "Bearer";

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    private String generateValidToken() {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        return JWT.create()
                .withSubject("testuser@example.com")
                .sign(algorithm);
    }

    @BeforeEach
    void setUp() {
        filter = new JWTValidatorFilter();

        // Set @Value fields manually using ReflectionTestUtils
        ReflectionTestUtils.setField(filter, "secret", secret);
        ReflectionTestUtils.setField(filter, "authHeader", header);
        ReflectionTestUtils.setField(filter, "bearer", bearer);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test
    void shouldAuthenticateRequestWithValidToken() throws Exception {
        String token = generateValidToken();
        when(request.getHeader(header)).thenReturn(bearer + " " + token);

        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
        assertNotNull(filter);
    }

    @Test
    void shouldNotAuthenticateWhenNoHeader() throws Exception {
        when(request.getHeader(header)).thenReturn(null);

        filter.doFilterInternal(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void shouldRejectInvalidToken() throws Exception {
        String invalidToken = "invalid.token.value";
        when(request.getHeader(header)).thenReturn(bearer + " " + invalidToken);

        filter.doFilterInternal(request, response, chain);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response).setHeader(eq("error"), anyString());
        verify(chain, never()).doFilter(request, response);
    }
}