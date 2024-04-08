package com.ms8.md.recommendation.feature.reco.dto.response;

import java.util.List;

import com.ms8.md.recommendation.feature.reco.dto.common.TrackingDto;
import com.ms8.md.recommendation.feature.reco.entity.Tracking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRecoTrackingDto {
	List<TrackingDto> recoResult;
}