package com.manjitmentor.sms.response.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private String contactNo;
    private String emailAddress;
    private Character isActive;
}
