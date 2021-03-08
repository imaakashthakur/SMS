package com.manjitmentor.sms.controller;


import com.manjitmentor.sms.constant.APIPathConstants;
import com.manjitmentor.sms.dto.GenericResponse;
import com.manjitmentor.sms.request.AuthRequest;
import com.manjitmentor.sms.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(APIPathConstants.LOGIN)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> login(@RequestBody AuthRequest authRequest){
        log.info("Login in Auth Controller Triggered!!..");
        log.info("authRequest: {}", authRequest);

        GenericResponse genericResponse = authService.login(authRequest);
        //This authService is an object of AuthService which is AuthService's object.

        return new ResponseEntity<>(genericResponse, HttpStatus.OK);
    }
}
