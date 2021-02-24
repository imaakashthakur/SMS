package com.manjitmentor.sms.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SaveCourseRequest implements Serializable {
    private String name;
    private String description;
    private String code;
}
