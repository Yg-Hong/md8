package com.ms8.md.user.global.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class SignInResponse {
    private Integer userId;
    private String email;
    private String accessToken;
    private String nickname;
    private String profile;
}
