package com.ms8.md.user.feature.user.service;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final UserDetailServiceImpl userDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();

		UserDetails userDetails = userDetailService.loadUserByUsername(username);
		if (userDetails == null) {
			throw new BadCredentialsException("유저 못찾음");
		}

		return new UsernamePasswordAuthenticationToken(
			userDetails, null, userDetails.getAuthorities()
		);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// 이 AuthenticationProvider가 UsernamePasswordAuthenticationToken 타입의 인증을 지원하는지 여부를 반환
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
