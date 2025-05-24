//package com.nttdata.customer.security;
//
//import com.nttdata.customer.security.utils.JwtUtil;
//import com.nttdata.customer.utils.constans.ConstansConfig;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.Collections;
//import java.util.List;
//
///**
// * JwtAuthenticationFilter.
// *
// * @author Joseph Magallanes
// * @since 2025-05-23
// */
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter implements ServerAuthenticationConverter {
//
//    private final JwtUtil jwtUtil;
//
//    @Override
//    public Mono<Authentication> convert(ServerWebExchange exchange) {
//        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7);
//            try {
//                String username = jwtUtil.extractUsername(token);
//                if (username != null && jwtUtil.validateToken(token)) {
//                    Authentication auth = new UsernamePasswordAuthenticationToken(username, null, List.of());
//                    return chain.filter(exchange)
//                        .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
//                }
//            } catch (Exception e) {
//                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT"));
//            }
//        }
//
//        return chain.filter(exchange);
//    }
//}
