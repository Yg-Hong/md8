package com.ms8.md.bigdata.feature.weather.repository;

import com.ms8.md.bigdata.feature.weather.dto.response.ResponseAddressDto;
import com.ms8.md.bigdata.feature.weather.dto.response.ResponseConditionDto;
import com.ms8.md.bigdata.feature.weather.dto.response.ResponseDustDto;

public interface WeatherService {
	ResponseConditionDto getCondition(Double lat, Double lon);
	ResponseDustDto getDust(Double lat, Double lon);
	ResponseAddressDto getAddress(Double lat, Double lon);

}
