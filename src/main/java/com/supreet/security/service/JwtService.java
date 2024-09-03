package com.supreet.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtService {

    private static final String SECRET = "82ABE8AED2DF6615C102DDAA2C948BA256A598FDCDB7A1175B2260C9479BD55CF5FBE48AC122699402170C062854AC77F5D3D42B5E0404A798672FCDA73D63DB";
    private static final Long VALIDITY = TimeUnit.MINUTES.toMillis(15);

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .findFirst()          // Get the first role
                        .orElse(""))
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .signWith(generateKey())
                .compact();
    }

    public SecretKey generateKey() {
        byte[] decodeKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodeKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getSubject();
    }

    private Claims getClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(jwt).getPayload();
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        Claims claims = getClaims(jwt);
        String username = claims.getSubject();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        Claims claims = getClaims(jwt);
        return claims.getExpiration().before(Date.from(Instant.now()));
    }
}
