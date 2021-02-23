package com.manjitmentor.sms.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UpdateUserRequest implements Serializable {

    private String firstName;
    private String lastName;
    private String address;
    private String contactNo;
    private String emailAddress;
    private String password;

}
