package com.manjitmentor.sms.service.impl;

import com.manjitmentor.sms.builder.ResponseBuilder;
import com.manjitmentor.sms.constant.MessagesConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.dto.JwtDTO;
import com.manjitmentor.sms.model.ApplicationUser;
import com.manjitmentor.sms.repository.ApplicationUserRepository;
import com.manjitmentor.sms.request.AuthRequest;
import com.manjitmentor.sms.response.AuthSuccessResponse;
import com.manjitmentor.sms.security.service.JWTService;
import com.manjitmentor.sms.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ApplicationUserRepository applicationUserRepository;
    private final JWTService jwtService;

    @Autowired
    public AuthServiceImpl(ApplicationUserRepository applicationUserRepository, JWTService jwtService) {
        this.applicationUserRepository = applicationUserRepository;
        this.jwtService = jwtService;
    }


    @Override
    public GenericResponse login(AuthRequest request) {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByEmailAddress(request.getEmailAddress());
        log.info("applicationUserOptional: {}", applicationUserOptional);
        if(!applicationUserOptional.isPresent()){
            log.info("IT says that the email address is not present!");
            return ResponseBuilder.buildFailure(MessagesConstants.EMAIL_PASSWORD_INCORRECT);
        }
        if(applicationUserOptional.get().getIsActive() == 'N'){
            return ResponseBuilder.buildFailure(MessagesConstants.USER_WAS_DELETED);
        }
        else{
            log.info("Email Address is found!");
            ApplicationUser applicationUser = applicationUserOptional.get();
            if(applicationUser.getPassword().equals(request.getPassword())) {
                log.info("Password matched!");
                Long currentTime = System.currentTimeMillis();
                Long expiryInSeconds = 300L;

                Map<String, Object> role = new HashMap<>();

                role.put("role", "user");

                final JwtDTO jwtData = JwtDTO
                        .builder()
                        .claims(role)
                        .issuedAt(new Date(currentTime))
                        .expiryAt(new Date(currentTime + expiryInSeconds * 1000))
                        .emailAddress(applicationUser.getEmailAddress())
                        .id(applicationUser.getId())
                        .build();

                String token = jwtService.generateToken(jwtData);
                return ResponseBuilder.buildSuccess(MessagesConstants.AUTH_SUCCESSFUL, new AuthSuccessResponse(token));
            }
            else{
                return ResponseBuilder.buildFailure(MessagesConstants.EMAIL_PASSWORD_INCORRECT);
            }
        }

    }
}
