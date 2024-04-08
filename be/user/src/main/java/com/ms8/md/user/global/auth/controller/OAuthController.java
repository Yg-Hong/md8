package com.ms8.md.user.global.auth.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ms8.md.user.global.auth.dto.response.SignInResponse;
import com.ms8.md.user.global.auth.service.OAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/login/oauth")
@RequiredArgsConstructor
public class OAuthController {

	private final OAuthService oAuthService;
	/*
	 * Google로 로그인 후 Google 승인 서버로 부터 Authorization Code를 응답받는 컨트롤러
	 * */
	// @GetMapping("/code/{registrationId}")
	// public ResponseEntity<SignInResponse> socialLogin(@RequestParam(name = "code", required = false) String code
	//     , @PathVariable(name = "registrationId") String registrationId) {
	//     log.info("code = {}", code);
	//
	//     log.info("registrationId = {}", registrationId);
	//     SignInResponse signInResponse = oAuthService.socialLogin(code, registrationId);
	//
	//     log.info("accessToken = {}", signInResponse.getAccessToken());
	//     log.info("email = {}", signInResponse.getEmail());
	//     log.info("profile = {}", signInResponse.getProfile());
	//
	//     return ResponseEntity.ok().body(signInResponse);
	// }

	@GetMapping("/code/{registrationId}")
	public RedirectView socialLogin(@RequestParam(name = "code", required = false) String code
		, @PathVariable(name = "registrationId") String registrationId) {
		log.info("code = {}", code);

		log.info("registrationId = {}", registrationId);
		SignInResponse signInResponse = oAuthService.socialLogin(code, registrationId);

		log.info("accessToken = {}", signInResponse.getAccessToken());
		log.info("email = {}", signInResponse.getEmail());
		log.info("profile = {}", signInResponse.getProfile());

		String redirectUrl = String.format("myapp://callback?token=%s&userId=%d&email=%s&nickname=%s&profile=%s",
			signInResponse.getAccessToken(), signInResponse.getUserId(),
			URLEncoder.encode(signInResponse.getEmail(), StandardCharsets.UTF_8),
			URLEncoder.encode(signInResponse.getNickname(), StandardCharsets.UTF_8),
			URLEncoder.encode(signInResponse.getProfile(), StandardCharsets.UTF_8));

		log.info("redirectUrl ={}", redirectUrl);

		return new RedirectView(redirectUrl);
	}
}
