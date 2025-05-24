//package com.nttdata.customer.security.utils;
//
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//import static javax.crypto.Cipher.SECRET_KEY;
//
///**
// * JwtUtil.
// *
// * @author Joseph Magallanes
// * @since 2025-05-23
// */
//@ConfigurationProperties
//public class JwtUtil {
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    private final long jwtExpirationMs = 86400000; // 1 día
//
//    public String extractUsername(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        return !extractAllClaims(token).getExpiration().before(new Date());
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//            .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
//    }
//}
