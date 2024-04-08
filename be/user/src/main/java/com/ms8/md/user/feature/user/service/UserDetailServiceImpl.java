package com.ms8.md.user.feature.user.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ms8.md.user.feature.user.entity.User;
import com.ms8.md.user.feature.user.repository.UserRepository;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByEmail(username)
			// .orElseThrow(() -> new UserException(ResponseCode22.INVALID_USER_INFO));
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		// password -> null 넘길시 에러발생
		return new CustomUserDetails(user.getEmail(), "1234", grantedAuthorities);
	}

	public static class CustomUserDetails extends org.springframework.security.core.userdetails.User {
		public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
			super(username, password, authorities);
		}
	}
}
