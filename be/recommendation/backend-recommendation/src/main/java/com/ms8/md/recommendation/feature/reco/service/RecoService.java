package com.ms8.md.recommendation.feature.reco.service;

import com.ms8.md.recommendation.feature.reco.dto.request.RequestRecoTrackingDto;
import com.ms8.md.recommendation.feature.reco.dto.response.ResponseRecoTrackingDto;

public interface RecoService {
	ResponseRecoTrackingDto recoTrackings(RequestRecoTrackingDto request);
}
