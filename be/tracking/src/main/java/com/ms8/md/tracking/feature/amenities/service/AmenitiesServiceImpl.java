package com.ms8.md.tracking.feature.amenities.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms8.md.tracking.feature.coordinates.dto.request.CoordinatesGetRequest;
import com.ms8.md.tracking.feature.coordinates.service.CoordinatesService;
import com.ms8.md.tracking.feature.amenities.dto.request.AmenitiesCreateRequest;
import com.ms8.md.tracking.feature.amenities.dto.response.AmenitiesResponse;
import com.ms8.md.tracking.feature.amenities.entity.Amenities;
import com.ms8.md.tracking.feature.tracking.entity.Tracking;
import com.ms8.md.tracking.feature.amenities.repository.AmenitiesRepository;
import com.ms8.md.tracking.feature.tracking.service.TrackingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AmenitiesServiceImpl implements AmenitiesService {
	private final AmenitiesRepository amenitiesRepository;
	private final CoordinatesService coordinatesService;

	@Override
	public List<AmenitiesResponse> getAmenitiesListByCoordinates(CoordinatesGetRequest request, PageRequest pageRequest) {
		List<Long> idxList = coordinatesService.getAllByCoordinates("amenities", request)
			.stream()
			.map(Long::parseLong)
			.toList();
		return amenitiesRepository.selectAllByCoordinates(idxList, pageRequest).stream().map(AmenitiesResponse::new).toList();
	}
}
