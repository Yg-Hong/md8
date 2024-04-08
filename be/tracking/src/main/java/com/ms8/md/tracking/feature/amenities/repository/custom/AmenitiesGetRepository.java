package com.ms8.md.tracking.feature.amenities.repository.custom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.ms8.md.tracking.feature.amenities.entity.Amenities;

public interface AmenitiesGetRepository {
	// 좌표별 편의시설 리스트 조회
	List<Amenities> selectAllByCoordinates(List<Long> idxList, Pageable pageable);

}
