package com.ms8.md.user.global.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms8.md.user.feature.user.entity.User;
import com.ms8.md.user.feature.user.repository.UserRepository;
import com.ms8.md.user.global.auth.dto.request.AuthInfo;
import com.ms8.md.user.global.auth.dto.request.SignInRequest;
import com.ms8.md.user.global.auth.dto.request.SignUpRequest;
import com.ms8.md.user.global.auth.dto.response.SignInResponse;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;
import com.ms8.md.user.global.util.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final TokenProvider tokenProvider;

	// 회원 가입 여부 확인
	public boolean isExistUser(String email) {
		return userRepository.findByEmail(email).isPresent();
	}

	// 회원가입
	@Transactional
	public void signUp(SignUpRequest signupRequest) {
		log.info("new User SignUp = {}", signupRequest.getEmail());

		boolean existUser = userRepository.findByEmail(signupRequest.getEmail()).isPresent();
		if (existUser) {
			throw new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO);
			// throw new UserException(ResponseCode22.USER_ALREADY_EXIST_ERROR);
		}

		User user = User.builder()
			.email(signupRequest.getEmail())
			.nickName(signupRequest.getNickName())
			.profile(signupRequest.getProfile())
			.build();

		userRepository.save(user);
	}

	// 로그인
	@Transactional
	public SignInResponse signIn(SignInRequest signinRequest) {
		AuthInfo authInfo = tokenProvider.getAuthInfo(signinRequest.getEmail());
		String accessToken = tokenProvider.createAccessToken(authInfo.authentication());

		tokenProvider.createRefreshToken(authInfo.authentication(), authInfo.user());

		return SignInResponse.builder()
			.userId(authInfo.user().getId())
			.email(authInfo.user().getEmail())
			.accessToken(accessToken)
			.nickname(authInfo.user().getNickName())
			.profile(authInfo.user().getProfile())
			.build();
	}

	// 로그아웃
	// @Transactional
	// public void signOut(HttpServletRequest request) {
	//     String requestToken = jwtUtil.resolveToken(request);
	//     String email = jwtUtil.extractUserNameFromExpiredToken(requestToken);
	//     String domain = jwtUtil.extractUserDomainFromExpiredToken(requestToken);
	//     log.info("signOut = {} / {}", email, domain);
	//
	//     User user = userRepository.findByEmailAndDomain(email, domain)
	//         .orElseThrow(() -> new UserException(
	//             ResponseCode.INVALID_USER_INFO));
	//     redisTemplate.delete(user.getEmail()+":"+user.getDomain());
	// }
}
