package com.ms8.md.user.global.auth.dto.request;

import org.springframework.security.core.Authentication;

import com.ms8.md.user.feature.user.entity.User;

public record AuthInfo(User user, Authentication authentication) {

}

