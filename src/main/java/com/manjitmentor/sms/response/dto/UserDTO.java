package com.manjitmentor.sms.response.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserDTO implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String contactNo;
    private String emailAddress;
    private Character isActive;
}
