package com.ms8.md.recommendation.feature.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ms8.md.recommendation.feature.search.dto.common.TrackingDto;
import com.ms8.md.recommendation.feature.reco.entity.Tracking;
import com.ms8.md.recommendation.feature.reco.repository.TrackingRepository;
import com.ms8.md.recommendation.feature.search.dto.response.ResponseSearchByContentDto;
import com.ms8.md.recommendation.feature.search.dto.response.ResponseSearchByTitleDto;
import com.ms8.md.recommendation.feature.search.entity.TrackingDocument;
import com.ms8.md.recommendation.feature.search.repository.TrackingSearchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

	private final TrackingRepository trackingRepository;
	private final TrackingSearchRepository trackingSearchRepository;

	@Override
	public ResponseSearchByTitleDto searchByTitle(String title, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "_score"));
		Page<TrackingDocument> elsQueryResult = trackingSearchRepository.findByTitleContainingAndIsDeleted(title, false, pageable);
		List<Long> ids = new ArrayList<>();
		for (TrackingDocument trackingDocument : elsQueryResult) {
			ids.add(trackingDocument.getId());
		}

		List<Tracking> queryResult = trackingRepository.findByIdIn(ids);
		List<Tracking> allById = ids.stream()
			.map(id -> queryResult.stream()
				.filter(tracking -> tracking.getId().equals(id))
				.findFirst()
				.orElse(null))
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

		List<TrackingDto> trackings = new ArrayList<>();
		for (Tracking tracking : allById) {
			TrackingDto build = TrackingDto.builder().id(tracking.getId())
				.title(tracking.getTitle())
				.time(tracking.getTime())
				.distance(tracking.getTime())
				.bookMark(tracking.getTrackingBookmarks().size())
				.build();
			trackings.add(build);
		}

		return ResponseSearchByTitleDto.builder().totalPage(elsQueryResult.getTotalPages()).totalSize(elsQueryResult.getTotalElements()).result(trackings).build();
	}

	@Override
	public ResponseSearchByContentDto searchByContent(String content, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size,Sort.by(Sort.Direction.DESC, "_score"));
		Page<TrackingDocument> elsQueryResult = trackingSearchRepository.findByContentContainingAndIsDeleted(content, false, pageable);
		List<Long> ids = new ArrayList<>();
		for (TrackingDocument trackingDocument : elsQueryResult) {
			ids.add(trackingDocument.getId());
		}

		List<Tracking> queryResult = trackingRepository.findByIdIn(ids);
		List<Tracking> allById = ids.stream()
			.map(id -> queryResult.stream()
				.filter(tracking -> tracking.getId().equals(id))
				.findFirst()
				.orElse(null))
			.filter(Objects::nonNull)
			.collect(Collectors.toList());

		List<TrackingDto> trackings = new ArrayList<>();
		for (Tracking tracking : allById) {
			TrackingDto build = TrackingDto.builder().id(tracking.getId())
				.title(tracking.getTitle())
				.time(tracking.getTime())
				.distance(tracking.getTime())
				.bookMark(tracking.getTrackingBookmarks().size())
				.build();
			trackings.add(build);
		}

		return ResponseSearchByContentDto.builder().totalPage(elsQueryResult.getTotalPages()).totalSize(elsQueryResult.getTotalElements()).result(trackings).build();
	}
}
