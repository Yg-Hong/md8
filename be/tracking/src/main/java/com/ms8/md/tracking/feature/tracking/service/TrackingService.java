package com.ms8.md.tracking.feature.tracking.service;

import java.util.List;

import com.ms8.md.tracking.feature.tracking.dto.request.TrackingCreateRequest;
import com.ms8.md.tracking.feature.tracking.dto.request.TrackingModifyRequest;
import com.ms8.md.tracking.feature.tracking.dto.response.GetTrackingListByCreatorResponse;
import com.ms8.md.tracking.feature.tracking.dto.response.TrackingCreationResponse;
import com.ms8.md.tracking.feature.tracking.dto.response.TrackingDetailResponse;
import com.ms8.md.tracking.feature.tracking.dto.common.TrackingResponse;

public interface TrackingService {
	
	// 산책로 기록 저장
	TrackingCreationResponse create(TrackingCreateRequest request, Integer userId);

	// 산책로 기록 수정 (제목, 내용, 추천여부)
	void modify(Long trackingId, TrackingModifyRequest request);

	// 산책로 기록 삭제
	void remove(Long trackingId);

	// 산책로 기록 상세 조회
	TrackingDetailResponse getDetail(Long trackingId);

	// 유저별 산책로 기록 전체 조회
	GetTrackingListByCreatorResponse getAllByCreator(Integer userId, Integer page, Integer size);
}
