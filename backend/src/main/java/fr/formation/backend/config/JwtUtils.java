package fr.formation.backend.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private final String jwtKey;

    public JwtUtils(@Value("${JWT_KEY}") String jwtKey) {
        this.jwtKey = jwtKey;
    }

    public String generate(Authentication auth) {
        String role = auth.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .findFirst()
                .orElse(RoleEnum.USER.getRole());

        return this.generate(auth, role);
    }

    public String generate(Authentication auth, String role) {
        Date now = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor(this.jwtKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(auth.getName())
                .claim("role", role)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + 3_600_000))
                .signWith(secretKey)
                .compact();
    }

    public Optional<String> validate(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(this.jwtKey.getBytes(StandardCharsets.UTF_8));

        try {
            return Optional.of(Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject()
            );
        } catch (Exception ex) {
            return Optional.empty();
        }
    }
}
