package com.ms8.md.user.global.auth.service;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.ms8.md.user.global.auth.dto.request.SignInRequest;
import com.ms8.md.user.global.auth.dto.request.SignUpRequest;
import com.ms8.md.user.global.auth.dto.response.SignInResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {

	private final Environment env;
	private final AuthService authService;

	/*
	 * Google 승인 서버로 HTTP 요청을 보내기 위한 RestTemplate
	 * */
	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public SignInResponse socialLogin(String code, String registrationId) {
		String accessToken = getAccessToken(code, registrationId);
		JsonNode userResourceNode = getUserResource(accessToken, registrationId);
		log.info("userResourceNode = {}", userResourceNode);

		String email = "";
		String nickName = "";
		String profile = "";
		if (registrationId.equals("kakao")) {
			// id = userResourceNode.get("id").asText();
			email = userResourceNode.get("kakao_account").get("email").asText();
			nickName = userResourceNode.get("properties").get("nickname").asText();
			profile = userResourceNode.get("properties").get("profile_image").asText();
		}
		// log.info("id = {}", id);
		log.info("email = {}", email);
		log.info("name = {}", nickName);
		log.info("profile = {}", profile);

		if (!authService.isExistUser(email)) {
			authService.signUp(
				// SignUpReqDto.builder()
				SignUpRequest.builder()
					.email(email)
					.nickName(nickName)
					.profile(profile)
					.build());
		}

		return authService.signIn(
			SignInRequest.builder()
				.email(email)
				.build()
		);
	}

	/*
	 * 응답받은 Authorization Code를 이용하여 OAuth access token을 발급받는 메소드
	 * RestTemplate을 이용하여 토큰 요청
	 * */
	private String getAccessToken(String authorizationCode, String registrationId) {
		String clientId = env.getProperty("oauth." + registrationId + ".client-id");
		String clientSecret = "";
		String tokenUri = env.getProperty("oauth." + registrationId + ".token-uri");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", authorizationCode);
		params.add("client_id", clientId);
		if (registrationId.equals("kakao")) {
			String redirectUri = env.getProperty("oauth." + registrationId + ".redirect-uri");
			params.add("redirect_uri", redirectUri);
		}
		params.add("grant_type", "authorization_code");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		HttpEntity entity = new HttpEntity(params, headers);

		ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity,
			JsonNode.class);
		JsonNode accessTokenNode = responseNode.getBody();
		log.info("response = {}", accessTokenNode);
		log.info("refreshtoken = {}", accessTokenNode.get("refresh_token"));
		return accessTokenNode.get("access_token").asText();
	}

	private JsonNode getUserResource(String accessToken, String registrationId) {
		String resourceUri = env.getProperty("oauth." + registrationId + ".resource-uri");

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		HttpEntity entity = new HttpEntity(headers);

		return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
	}
}
