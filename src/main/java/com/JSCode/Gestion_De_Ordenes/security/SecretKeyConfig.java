package com.JSCode.Gestion_De_Ordenes.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Configuration
public class SecretKeyConfig {

    @Value("${SECRET_STRING}")
    private String secret;

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}