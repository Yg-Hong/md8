package com.ms8.md.user.global.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfig {

	private final ObjectFactory<HttpMessageConverters> messageConverters;

	@Bean
	public Encoder feginEncoder() {
		return new SpringFormEncoder(new SpringEncoder(messageConverters));
	}
}
