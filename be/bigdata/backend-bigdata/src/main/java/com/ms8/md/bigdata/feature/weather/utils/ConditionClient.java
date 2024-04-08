package com.ms8.md.bigdata.feature.weather.utils;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weatherClient", url = "https://api.openweathermap.org/data/2.5")
public interface ConditionClient {
	@GetMapping("/weather")
	String getCondition(@RequestParam("lat") double lat,
		@RequestParam("lon") double lon,
		@RequestParam("lang") String lang,
		@RequestParam("appid") String apiKey,
		@RequestParam("units") String units);
}
