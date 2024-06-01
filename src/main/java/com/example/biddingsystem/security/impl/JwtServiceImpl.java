package com.example.biddingsystem.security.impl;

import com.example.biddingsystem.exceptions.ValidationException;
import com.example.biddingsystem.models.UserEntity;
import com.example.biddingsystem.security.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret}")
    private String SECRET;

    @Override
    public String generateToken(UserEntity userEntity) {
        return Jwts
                .builder()
                .subject(userEntity.getUsername())
                .claim("role", userEntity.getRole().name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .signWith(getSignInKey())
                .compact();
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new ValidationException("Invalid JWT Token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new ValidationException("Expired JWT token");
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw new ValidationException("Unsupported JWT token");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new ValidationException("Jwt claims string is null or empty");
        }
    }
}