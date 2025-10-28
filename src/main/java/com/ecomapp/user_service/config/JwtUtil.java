package com.ecomapp.user_service.config;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecomapp.user_service.constant.RoleEnum;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
  private final Key JWT_SECRET_KEY;
  private final long JWT_EXPIRATION_TIME;

  public JwtUtil(
        @Value("${jwt.secret}") String JWT_SECRET_KEY,
        @Value("${jwt.expiration}") long JWT_EXPIRATION_TIME) {
    this.JWT_SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET_KEY));
    this.JWT_EXPIRATION_TIME = JWT_EXPIRATION_TIME;
  }

  public String generateToken(UUID sub, RoleEnum role) {
    return Jwts.builder()
        .setSubject(String.valueOf(sub))
        .claim("role", role.name())
        .setIssuedAt(new Date())
        .setExpiration(new java.util.Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME))
        .signWith(JWT_SECRET_KEY, SignatureAlgorithm.HS256)
        .compact();
  }
}
