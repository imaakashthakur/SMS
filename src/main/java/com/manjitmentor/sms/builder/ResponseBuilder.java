package com.manjitmentor.sms.builder;

import com.manjitmentor.sms.dto.GenericResponse;

public class ResponseBuilder {
    public static GenericResponse buildSuccess(String respDesc, Object data){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setData(data);
        genericResponse.setResultDescription(respDesc);
        genericResponse.setStatus(true);
        return genericResponse;
    }

    public static GenericResponse buildFailure(String respDesc){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setData(null);
        genericResponse.setResultDescription(respDesc);
        genericResponse.setStatus(false);
        return genericResponse;
    }

    public static GenericResponse buildSuccess(String respDesc){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setResultDescription(respDesc);
        genericResponse.setStatus(true);
        return genericResponse;
    }
}
