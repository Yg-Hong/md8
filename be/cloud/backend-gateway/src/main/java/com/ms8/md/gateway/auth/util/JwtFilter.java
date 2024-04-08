package com.ms8.md.gateway.auth.util;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms8.md.gateway.auth.dto.response.AccessTokenResponse;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtFilter implements GlobalFilter {
	private final static String HEADER_PREFIX = "Bearer ";
	// public static final String USER_REISSUE_URL = "http://localhost:8090/api/users/access_token/reissue";
	public static final String USER_REISSUE_URL = "http://j10a208.p.ssafy.io:8443/api/users/access_token/reissue";

	@Value("${oauth.kakao.redirect-uri}")
	private String redirectURL;

	@Value("${oauth.kakao.client-id}")
	private String clientId;
	private String kakaoLoginUrl;
	@PostConstruct
	public void init() {
		kakaoLoginUrl = "https://kauth.kakao.com/oauth/authorize?"
			+ "client_id=" + clientId + "&"
			+ "redirect_uri=" + redirectURL + "&"
			+ "response_type=code";
	}

	private final WebClient.Builder webClientBuilder;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		Route route = (Route) exchange.getAttributes().get("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayRoute");
		log.info("Request made to route: {}",route);

		log.info("Method >> JwtFilter.filter");

		ServerHttpRequest request = exchange.getRequest();
		ServerHttpResponse response = exchange.getResponse();
		log.info("URI >> {}", request.getURI());
		String path = request.getURI().getPath();

		String first = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		log.info("AUTHORIZATION >> {}", first);

		if (isUnfilteredPath(path)) {
			return chain.filter(exchange);
		}

		log.info("JWT 검증 시작");
		// 1. Header Authorization 가져오기
		String token = resolveToken(request);

		// 2. token 정보 없는 경우 login 페이지 redirect
		if (!StringUtils.hasText(token)) {
			log.info("토큰 정보 없음");
			return redirectLoginPage(response);
		}

		JwtUtil.Status validAccessToken = jwtUtil.isValidAccessToken(token);

		switch (validAccessToken) {
			// 3. AccessToken 유효하지 않는 경우 login 페이지 redirect
			case INVALID -> {
				log.info("액세스 토큰 유효하지 않음= {}", token);
				return redirectLoginPage(response);
			}

			// 4. AccessToken 만료된경우 user server reissue 요청
			case EXPIRED -> {
				log.info("액세스 토큰 만료= {}", token);
				// String newAccessToken = getNewAccessToken(token);
				return getNewAccessToken(token)
					.flatMap(newAccessToken -> {
						if (StringUtils.hasText(newAccessToken)) {
							log.info("리프레시 토큰 유효 새로받은 액세스 토큰= {}", newAccessToken);

							try {
								AccessTokenResponse accessTokenResponse = objectMapper.readValue(newAccessToken,
									AccessTokenResponse.class);
								ServerHttpRequest httpRequest = exchange.getRequest()
									.mutate()
									.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenResponse.getAccessToken())
									.build();

								return chain.filter(exchange.mutate()
									.request(httpRequest)
									.build());
							} catch (JsonProcessingException e) {
								log.error("to Parse JSON Token: {}", e.getMessage());
								e.getMessage();
							}
						} else {
							log.info("리프레시 토큰 만료= {}", newAccessToken);
							return redirectLoginPage(response);
						}

						return Mono.empty();
					});
			}

			case VALID -> log.info("액세스 토큰 유효= {}", token);
		}

		return chain.filter(exchange);
	}

	public boolean isUnfilteredPath(String path) {
		return path.startsWith("/api/codes")
			|| path.startsWith("/login/oauth");
	}

	private Mono<String> getNewAccessToken(String token) {
		// TODO  url -> 확정되면 변경하기
		log.info("넘긴 토큰 {}", token);
		return webClientBuilder.build()
			.get()
			.uri(USER_REISSUE_URL)
			.header(HttpHeaders.AUTHORIZATION, HEADER_PREFIX + token)
			.retrieve()
			.bodyToMono(String.class);
	}

	private Mono<Void> redirectLoginPage(ServerHttpResponse response) {
		log.info("redirectURL >> {}", kakaoLoginUrl);

		response.setStatusCode(HttpStatus.TEMPORARY_REDIRECT);
		response.getHeaders().setLocation(URI.create(kakaoLoginUrl));
		return response.setComplete();
	}

	private String resolveToken(ServerHttpRequest request) {
		String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
			return bearerToken.substring(7);
		}
		return null;
	}

}
