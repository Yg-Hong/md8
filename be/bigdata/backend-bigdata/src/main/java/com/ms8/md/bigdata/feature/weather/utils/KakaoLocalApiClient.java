package com.ms8.md.bigdata.feature.weather.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoLocalApiClient", url = "https://dapi.kakao.com")
public interface KakaoLocalApiClient {
	@GetMapping("/v2/local/geo/coord2regioncode.json")
	String getRegionCode(@RequestParam("x") double longitude,
		@RequestParam("y") double latitude,
		@RequestHeader("Authorization") String apiKey);
}
