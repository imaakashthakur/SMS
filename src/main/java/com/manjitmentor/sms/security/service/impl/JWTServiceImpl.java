package com.manjitmentor.sms.security.service.impl;

import com.manjitmentor.sms.dto.JwtDTO;
import com.manjitmentor.sms.security.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
public class JWTServiceImpl implements JWTService<JwtDTO> {
    String secretKey = "9z$C&F)J@NcRfUjXn2r5u8x!A%D*G-KaPdSgVkYp3s6v9y$B?E(H+MbQeThWmZq4";
    String base64EncodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    @Override
    public String generateToken(JwtDTO source) {
        log.info(String.valueOf(source));

        return Jwts.builder()
                .setClaims(source.getClaims())
                .setSubject(source.getEmailAddress())
                .setIssuer(source.getIssuer())
                .setId(source.getId().toString())
                .setExpiration(source.getExpiryAt())
                .setIssuedAt(source.getIssuedAt())
                .signWith(SignatureAlgorithm.HS256, base64EncodedSecretKey)
                .compact();


    }

    @Override
    public JwtDTO verifyToken(String token) {
        final JwtDTO.JwtDTOBuilder jwtDTOBuilder = JwtDTO.builder();

        try {
            final Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);

            final Claims body = claimsJws.getBody();

            final Date expiration = body.getExpiration();
            log.info("Expires in: {}", expiration);

            return jwtDTOBuilder.authenticated(true)
                    .claims(body)
                    .emailAddress(body.getSubject())
                    .issuedAt(body.getIssuedAt())
                    .issuer(body.getIssuer())
                    .expiryAt(expiration)
                    .id(Long.valueOf(body.getId()))
                    .build();
        } catch (Exception exception){
            log.debug("Exception Found!: {}", exception);
        }
        return JwtDTO.builder().build();
    }
}
