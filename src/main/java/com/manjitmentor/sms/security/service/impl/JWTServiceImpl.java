package com.manjitmentor.sms.security.service.impl;

import com.manjitmentor.sms.dto.JwtDTO;
import com.manjitmentor.sms.security.service.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JWTServiceImpl implements JWTService<JwtDTO> {
    @Override
    public String generateToken(JwtDTO source) {
        String secretKey = "9z$C&F)J@NcRfUjXn2r5u8x!A%D*G-KaPdSgVkYp3s6v9y$B?E(H+MbQeThWmZq4";
        String check = Jwts.builder()
                .setClaims(source.getClaims())
                .setSubject(source.getEmailAddress())
                .setIssuer(source.getIssuer())
                .setId(source.getId().toString())
                .setExpiration(source.getExpiryAt())
                .setIssuedAt(source.getIssuedAt())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
        log.info("jwts Builder: {}", check);
        return Jwts.builder()
                .setClaims(source.getClaims())
                .setSubject(source.getEmailAddress())
                .setIssuer(source.getIssuer())
                .setId(source.getId().toString())
                .setExpiration(source.getExpiryAt())
                .setIssuedAt(source.getIssuedAt())
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    @Override
    public Boolean verifyToken(String token) {
        return null;
    }
}