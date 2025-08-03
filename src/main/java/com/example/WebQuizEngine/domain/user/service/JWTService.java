package com.example.WebQuizEngine.domain.user.service;

import com.example.WebQuizEngine.domain.user.models.UserEntity;
import com.example.WebQuizEngine.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cglib.core.internal.Function;
import org.springframework.core.codec.Encoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private final UserRepository userRepository;
    private final KeyGenerator keyGenerator;
    private String secretKey;

    public JWTService(UserRepository userRepository) throws NoSuchAlgorithmException {
        this.userRepository = userRepository;
        keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGenerator.generateKey();
        secretKey = "eyJhbGciOiJIUzI1NiJ9eyJpZCI6IjFkMGI5ZGQyLWEwM2MtNDQ1Ni1iZDNmLTg5NDZjMGI1NGFiNSIsInN1YiI6Im15IGZpcnN0IHVzZXIiLCJpYXQiOjE3NTQxOTQwOTcsImV4cCI6MTc1NDI4MDQ5N30p1tldqihPHAdGInl87kHM7MFlsFHi85K2kbOvpBj1s";

    }

    public String generateToken(String userName) {
        UserEntity userEntity = userRepository.findByNameQueryNative(userName);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userEntity.getId());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
                .and()
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractName(token);
        return userDetails.getUsername().equals(userName) && !isTokenExpired(token);
    }
}
