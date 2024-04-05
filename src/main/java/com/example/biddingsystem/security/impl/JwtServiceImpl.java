package com.example.biddingsystem.security.impl;

import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public String generateToken(UserEntity userEntity) {
        return Jwts
                .builder()
                .subject(userEntity.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 15*60*1000))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    @Override
    public boolean isValid(String token, UserDetails user) {
        return user.getUsername().equals(extractUsername(token)) && !isTokenExpired(token);
    }
}