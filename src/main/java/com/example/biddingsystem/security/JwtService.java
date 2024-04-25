package com.example.biddingsystem.security;

import com.example.biddingsystem.models.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String generateToken(UserEntity userEntity);
    SecretKey getSignInKey();
    String extractUsername(String token);
    boolean validateToken(String token);
}
