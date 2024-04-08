package com.ms8.md.recommendation.feature.search.dto.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDto {
	Long id;
	String title;
	Integer distance;
	Integer time;
	Integer kcal;
	Integer bookMark;
}
