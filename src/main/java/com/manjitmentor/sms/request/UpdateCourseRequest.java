package com.manjitmentor.sms.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCourseRequest {
    private String name;
    private String description;
    private String code;
}
