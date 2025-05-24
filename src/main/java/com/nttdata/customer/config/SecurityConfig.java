//package com.nttdata.customer.config;
//
//import com.nttdata.customer.security.JwtAuthenticationFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.ReactiveAuthenticationManager;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationFilter;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import reactor.core.publisher.Mono;
//
///**
// * SecurityConfig.
// *
// * @author Joseph Magallanes
// * @since 2025-05-23
// */
//@Configuration
////@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Autowired
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//            .csrf(ServerHttpSecurity.CsrfSpec::disable)
//            .authorizeExchange(exchange -> exchange
//                .pathMatchers("/api/v1/customers/public/**").permitAll()
//                .anyExchange().authenticated()
//            )
//            .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
//            .build();
//    }
//}
