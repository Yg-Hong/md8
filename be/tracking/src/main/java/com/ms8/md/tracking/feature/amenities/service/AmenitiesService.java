package com.ms8.md.tracking.feature.amenities.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ms8.md.tracking.feature.amenities.dto.request.AmenitiesCreateRequest;
import com.ms8.md.tracking.feature.amenities.dto.response.AmenitiesResponse;
import com.ms8.md.tracking.feature.coordinates.dto.request.CoordinatesGetRequest;

public interface AmenitiesService {
	// 좌표별 편의시설 목록 조회
	List<AmenitiesResponse> getAmenitiesListByCoordinates(CoordinatesGetRequest request, PageRequest pageRequest);
}
