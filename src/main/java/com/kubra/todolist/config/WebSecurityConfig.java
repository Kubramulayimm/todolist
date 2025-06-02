package com.kubra.todolist.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] PUBLIC_ENDPOINTS = new String[]{"/user/**", "/auth/**", "/category/**", "/task/**",
            "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger*/**", "/actuator/health"};

    private final JWTValidatorFilter jwtValidatorFilter;

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        //@formatter:off
        var httpSecurity = http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                               .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                                   CorsConfiguration config = new CorsConfiguration();
                                   config.setAllowedOrigins(Collections.singletonList("*"));
                                   config.setAllowedMethods(Collections.singletonList("*"));
                                   config.setAllowCredentials(true);
                                   config.setAllowedHeaders(Collections.singletonList("*"));
                                   config.setMaxAge(3600L);
                                   return config;

                               }))
                               .csrf(AbstractHttpConfigurer::disable)
                               .authorizeHttpRequests(r-> r.requestMatchers(PUBLIC_ENDPOINTS).permitAll().anyRequest().authenticated())
                               .formLogin(AbstractHttpConfigurer::disable)
                               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                               .addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter.class);//@formatter:on

        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    // Kimlik doğrulama yöneticisi (AuthenticationManager) bean'i
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
