package com.ms8.md.user.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class JwtProperty {

    @Value("${jwt.secretkey}")
    private String secretKey;

    @Value("${jwt.accessExpirationTime}")
    private Long accessExpirationTime;

    @Value("${jwt.refreshExpirationTime}")
    private Long refreshExpirationTime;
}
