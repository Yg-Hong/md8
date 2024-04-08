package com.ms8.md.gateway.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {

	private final CorsConfig corsConfig;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges
				.pathMatchers("/**").permitAll()
				.anyExchange().authenticated()
			)
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
			.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
		return http.build();
	}
}
