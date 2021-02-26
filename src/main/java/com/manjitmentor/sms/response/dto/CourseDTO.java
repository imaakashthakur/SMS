package com.manjitmentor.sms.response.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString

public class CourseDTO implements Serializable {
    private String id;
    private String name;
    private String description;
    private String code;
    private Character isActive;
}
