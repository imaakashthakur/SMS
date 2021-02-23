package com.manjitmentor.sms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor

public class GenericResponse implements Serializable {
    private Boolean status;
    private String resultDescription;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;
}
