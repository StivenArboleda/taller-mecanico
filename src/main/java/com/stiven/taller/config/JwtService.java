package com.stiven.taller.config;

import com.stiven.taller.model.user.User;
import com.stiven.taller.repository.user.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;

@Service
public class JwtService {

    private final UserRepository userRepository;

    private final String SECRET_KEY = "ZXlKcGJtRnllU0kzVXpJdWJHZXNnV0o1S2hFZ1YtUm9UMFFTeVdTMWM4c1ByT1NQZHBpYzZ0cHJXbUZPV1pFWUhN";
    private final long EXPIRATION_TIME = 864_000_000; // 10 días en milisegundos


    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public User validateTokenAndRetrieveUser(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return userRepository.findByUsername(claims.getSubject())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }
}

