package com.ms8.md.gateway.auth.util;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log4j2
@Component
public class JwtUtil {

	private static final Logger kafkaLogger = LoggerFactory.getLogger("kafkaLogger");

	@Value("${jwt.secretkey}")
	private String secretKey;

	private Key key;

	@PostConstruct
	public void init() {
		makeKey();
	}

	public enum Status {
		VALID, EXPIRED, INVALID
	}

	public void makeKey() {
		log.info("makeKey >> 시크릿키= {}", secretKey);
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public Status isValidAccessToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);

			String email = claimsJws.getBody()
				.getSubject();

			kafkaLogger.info("email : {}", email);
			log.info("isValidAccessToken jwt subject >> {}", email);

			log.info("JwtUtil.isValidAccessToken >> 정상 토큰");
			return Status.VALID;
		} catch (ExpiredJwtException e) {
			log.info("JwtUtil.isValidAccessToken >> 토큰 만료 message= {}", e.getMessage());
			return Status.EXPIRED;
		} catch (Exception e) {
			log.info("JwtUtil.isValidAccessToken >> 토큰 그 외 오류 message= {}", e.getMessage());
			return Status.INVALID;
		}
	}
}
