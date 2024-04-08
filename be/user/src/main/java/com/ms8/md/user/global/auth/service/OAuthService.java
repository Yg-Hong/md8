package com.ms8.md.user.global.auth.service;

import com.ms8.md.user.global.auth.dto.response.SignInResponse;

public interface OAuthService {
    // public SignInResDto socialLogin(String code, String registrationId);
    public SignInResponse socialLogin(String code, String registrationId);
}
