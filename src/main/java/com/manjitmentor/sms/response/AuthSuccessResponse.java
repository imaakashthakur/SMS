package com.manjitmentor.sms.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor

public class AuthSuccessResponse implements Serializable {
    private String token;
}
