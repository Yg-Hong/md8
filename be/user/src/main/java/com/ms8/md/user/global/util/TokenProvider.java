package com.ms8.md.user.global.util;

import java.security.Key;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.ms8.md.user.feature.user.entity.User;
import com.ms8.md.user.feature.user.repository.UserRepository;
import com.ms8.md.user.feature.user.service.UserDetailServiceImpl;
import com.ms8.md.user.feature.user.service.UserDetailServiceImpl.CustomUserDetails;
import com.ms8.md.user.global.auth.dto.request.AuthInfo;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;

@Slf4j
@Component
// @RequiredArgsConstructor
public class TokenProvider {
	private final JwtProperty jwtProperty;
	private final UserDetailServiceImpl userDetailService;
	private final StringRedisTemplate redisTemplate;
	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;

	/**
	 * Lombok 어노테이션 RequiredArgs.. 사용해서 의존성 주입할 경우 @Lazy 적용 안되는현상이 보이는듯함.
	 * 생성자 주입을 통해 매개변수로 @Lazy 적용
	 */
	public TokenProvider(JwtProperty jwtProperty, UserDetailServiceImpl userDetailService,
		StringRedisTemplate redisTemplate, @Lazy AuthenticationManager authenticationManager, UserRepository userRepository) {
		this.jwtProperty = jwtProperty;
		this.userDetailService = userDetailService;
		this.redisTemplate = redisTemplate;
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
	}

	private Key key;

	/**
	 * JWT 토큰 생성시 필요한 Key 생성
	 */
	public void makeKey(String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * accessToken 생성
	 */
	public String createAccessToken(Authentication authentication) {
		// Claims claims = Jwts.claims().setSubject(authentication.getName());
		String accessToken = getToken(authentication, jwtProperty.getAccessExpirationTime());
		log.info("JWT accessToken = {}", accessToken);
		return accessToken;
	}


	/**
	 * refreshToken 생성
	 * 소셜로그인(카카오) 정보로 토큰 생성
	 * 리프레시 토큰이 생성되면 redis에 카카오이메일을 key로 한 데이터 저장 => key: email, value: 리프레시토큰
	 */
	@Transactional
	public void createRefreshToken(Authentication authentication, User user) {
		String refreshToken = getToken(authentication, jwtProperty.getRefreshExpirationTime());
		log.info("refreshToken = {}", refreshToken);
		redisTemplate.opsForValue()
			.set(user.getEmail(), refreshToken, Duration.ofSeconds(jwtProperty.getRefreshExpirationTime()));
		// redisTemplate.opsForValue().set(user.getEmail()+":"+user.getDomain(), refreshToken, Duration.ofSeconds(jwtProperty.getRefreshExpirationTime()));
	}

	private String getToken(Authentication authentication, Long expirationTime) {
		Claims claims = Jwts.claims().setSubject(authentication.getName());
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + expirationTime * 1000);
		makeKey(jwtProperty.getSecretKey());

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(expireDate)
			// .signWith(SignatureAlgorithm.HS256, jwtProperty.getSecretKey())
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}


	/**
	 * http 헤더로부터 bearer token 추출
	 */
	public String resolveToken(HttpServletRequest request) {
		log.info("resolveToken : {}", request.getHeader(HttpHeaders.AUTHORIZATION));
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	/**
	 * 토큰에 담겨있는 payload 추출 => subject (이메일)
	 */
	public String extractUserNameFromExpiredToken(String token) {
		JSONObject payload = getJsonObject(token);
		String userName = (String)payload.get("sub");
		log.info("extracted UserName from AccessToken = {}", userName);
		return userName;
	}

	public String getRefreshTokenFromRedis(String email) {
		return redisTemplate.opsForValue().get(email);
	}

	public LocalDateTime getRefreshTokenExpirationTime(String refreshToken) {
		Jws<Claims> claimsJws = Jwts.parserBuilder()
			.setSigningKey(jwtProperty.getSecretKey())
			.build()
			.parseClaimsJws(refreshToken);
		Date expiration = claimsJws.getBody().getExpiration();

		return expiration.toInstant()
			.atZone(ZoneId.systemDefault())
			.toLocalDateTime();
	}

	private static JSONObject getJsonObject(String token) {
		log.info("getJsonObject : 받은 token {}", token);
		String[] arr = token.split("\\.");
		byte[] decodedBytes = Base64.getDecoder().decode(arr[1]);

		JSONParser parser = new JSONParser();
		JSONObject payload;
		try {
			payload = (JSONObject)parser.parse(new String(decodedBytes));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return payload;
	}

	/**
	 * 리프레시토큰 갱신 기간이 1일 미만으로 남았는지 검증
	 */
	public boolean shouldRenewToken(LocalDateTime tokenExpirationTime) {
		long daysUntilExpiration = ChronoUnit.DAYS.between(LocalDateTime.now(), tokenExpirationTime);
		int remainDay = 1; // 만료 1일전 갱신
		return daysUntilExpiration < remainDay;
	}

	/**
	 * 다른 클래스에서 해당 클래스 호출해서 리프레시토큰 갱신
	 */
	@Transactional
	public void renewTokenIfNecessary(String refreshToken, String email){
		LocalDateTime tokenExpirationTime = getRefreshTokenExpirationTime(refreshToken);

		// #1. 리프레시 토큰 남은 기간 확인
		if (shouldRenewToken(tokenExpirationTime)) {
			AuthInfo authInfo = getAuthInfo(email);
			createRefreshToken(authInfo.authentication(), authInfo.user());
		}
	}

	public AuthInfo getAuthInfo(String email) {
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));
		Authentication authentication;
		authentication = getAuthenticationById(email);

		return new AuthInfo(user, authentication);
	}

	public Authentication getAuthentication(String token) {
		String email = Jwts.parserBuilder()
			.setSigningKey(jwtProperty.getSecretKey())
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();

		CustomUserDetails customUserDetails =
			(CustomUserDetails)userDetailService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
	}

	public Authentication getAuthenticationById(String email) {
		Authentication authentication;
		authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(email, null)
		);
		return authentication;
	}

}
