package com.manjitmentor.sms.security.service;

import org.springframework.stereotype.Service;

public interface JWTService<I> {
    String generateToken(I data);
    Boolean verifyToken(String token);
}
