package com.ms8.md.bigdata.feature.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseConditionDto {
	private Double temp;
	private String description;
	private String icon;
}
