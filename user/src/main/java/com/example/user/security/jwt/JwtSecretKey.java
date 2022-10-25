package com.example.user.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    private final SecretKey secretKey;

    public JwtSecretKey() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Bean
    public SecretKey secretKey() {
        return secretKey;
    }
}
