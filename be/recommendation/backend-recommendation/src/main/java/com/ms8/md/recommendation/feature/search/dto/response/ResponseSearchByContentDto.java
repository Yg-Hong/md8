package com.ms8.md.recommendation.feature.search.dto.response;

import java.util.List;

import com.ms8.md.recommendation.feature.search.dto.common.TrackingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSearchByContentDto {
	private Long totalSize;
	private Integer totalPage;
	private List<TrackingDto> result;
}
