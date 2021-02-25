package com.manjitmentor.sms.response.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SubjectDTO implements Serializable {
    private String name;
    private String description;
    private String code;
    private Character isActive;
}
