package com.manjitmentor.sms.service;

import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.request.AuthRequest;

public interface AuthService {
    GenericResponse login(AuthRequest request);
}
