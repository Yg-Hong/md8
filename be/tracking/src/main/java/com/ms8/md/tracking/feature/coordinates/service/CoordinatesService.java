package com.ms8.md.tracking.feature.coordinates.service;

import java.util.List;

import com.ms8.md.tracking.feature.coordinates.dto.request.CoordinatesGetRequest;

public interface CoordinatesService {

	// 좌표 등록
	public void create(Long ownerId, String ownerType, Double latitude, Double longitude);

	// 좌표별 리스트 조회
	public List<String> getAllByCoordinates(String ownerType, CoordinatesGetRequest request);

	// 시도 조회
	public String getSido(Double latitude, Double longitude);
}
