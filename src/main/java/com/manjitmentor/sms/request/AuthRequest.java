package com.manjitmentor.sms.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString

public class AuthRequest implements Serializable {

    private String emailAddress;
    private String password;
}
