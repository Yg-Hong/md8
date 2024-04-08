package com.ms8.md.bigdata.feature.weather.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "airQualityApi", url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc")
public interface AirQualityClient {
	@GetMapping("/getMsrstnAcctoRltmMesureDnsty")
	String getAirQuality(@RequestParam("serviceKey") String serviceKey,
		@RequestParam("stationName") String stationName,
		@RequestParam("dataTerm") String dataTerm,
		@RequestParam("returnType") String returnType);
}
