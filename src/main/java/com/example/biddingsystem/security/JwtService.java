package com.example.biddingsystem.security;

import com.example.biddingsystem.models.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String generateToken(UserEntity userEntity);
    String extractUsername(String token);
    Date extractExpiration(String token);
    boolean isTokenExpired(String token);
    SecretKey getSignInKey();
    Claims extractAllClaims(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
    boolean isValid(String token, UserDetails user);

}
