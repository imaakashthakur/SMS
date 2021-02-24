package com.manjitmentor.sms.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
@Builder

public class JwtDTO {
    private Map<String, Object> claims;
    private String issuer = "SMS";
    private Long id;
    private String emailAddress;
    private Date issuedAt;
    private Date expiryAt;

}
