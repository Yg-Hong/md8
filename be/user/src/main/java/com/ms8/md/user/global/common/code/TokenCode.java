package com.ms8.md.user.global.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenCode {
	EXPIRED_ACCESS_TOKEN(1100, "액세스 토큰 만료"),
	EXPIRED_REFRESH_TOKEN(1101, "리프레쉬 토큰 만료"),
	INVALID_TOKEN_INFO(1102, "유효하지 않은 토큰 입니다."),
	;

	private final int code;
	private final String message;
	}
