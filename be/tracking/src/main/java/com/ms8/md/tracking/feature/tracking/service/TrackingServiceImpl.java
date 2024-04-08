package com.ms8.md.tracking.feature.tracking.service;

import static com.ms8.md.tracking.global.common.CalorieCalculator.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ms8.md.tracking.feature.amenities.entity.Amenities;
import com.ms8.md.tracking.feature.amenities.repository.AmenitiesRepository;

import com.ms8.md.tracking.feature.tracking.dto.response.GetTrackingListByCreatorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.ms8.md.tracking.feature.kafka.service.KafkaProducerService;
import com.ms8.md.tracking.feature.tracking.dto.request.TrackingCreateRequest;
import com.ms8.md.tracking.feature.tracking.dto.request.TrackingModifyRequest;
import com.ms8.md.tracking.feature.tracking.dto.response.TrackingCreationResponse;
import com.ms8.md.tracking.feature.tracking.dto.response.TrackingDetailResponse;
import com.ms8.md.tracking.feature.tracking.dto.common.TrackingResponse;
import com.ms8.md.tracking.feature.tracking.entity.Tracking;
import com.ms8.md.tracking.feature.tracking.repository.TrackingRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TrackingServiceImpl implements TrackingService {

	/**
	 * TODO
	 * 에러 처리 로직 추가
	 * token 처리 추가
	 */
	private static final String TOPIC = "tracking";

	private final TrackingRepository trackingRepository;
	private final KafkaProducerService kafkaProducerService;
	private final AmenitiesRepository amenitiesRepository;

	@Transactional
	@Override
	public TrackingCreationResponse create(TrackingCreateRequest request, Integer userId) {
		// 산책로 저장
		Tracking tracking = request.toEntity(request, userId);
		tracking.updateGps(request.getLatitude(), request.getLongitude());
		tracking.updateKcal(calculateCalories(30, 'M', request.getDistance(), request.getTime()));
		Tracking createdTracking = trackingRepository.save(tracking);

		if(request.getIsRecommend()){
			// Kafka 메시지 전송
			String message = createMessage(createdTracking);
			this.kafkaProducerService.sendMessageToKafka(TOPIC, message);
		}

		return TrackingCreationResponse.builder().trackingId(createdTracking.getTrackingId()).build();
	}

	@Transactional
	@Override
	public void modify(Long trackingId, TrackingModifyRequest request) {
		Tracking tracking = trackingRepository.findByNotIsDeleted(trackingId)
			.orElseThrow(() -> new IllegalArgumentException("해당 산책로가 존재하지 않습니다."));
		tracking.update(request);
		trackingRepository.save(tracking);
	}

	@Transactional
	@Override
	public void remove(Long trackingId) {
		Tracking tracking = trackingRepository.findByNotIsDeleted(trackingId)
			.orElseThrow(() -> new IllegalArgumentException("해당 산책로가 존재하지 않습니다."));
		tracking.deleteData();
		trackingRepository.save(tracking);
	}

	@Override
	public TrackingDetailResponse getDetail(Long trackingId) {
		Tracking tracking = trackingRepository.findByNotIsDeleted(trackingId)
			.orElseThrow(() -> new IllegalArgumentException("해당 산책로가 존재하지 않습니다."));

		if(tracking.getAmenitiesList().equals("")){
			return TrackingDetailResponse.toDto(tracking, 0, 0, 0);
		}

		String[] numbersArray = tracking.getAmenitiesList().split(",\\s*");
		List<Long> numberList = new ArrayList<>();

		for (String number : numbersArray) {
			numberList.add(Long.parseLong(number));
		}

		List<Amenities> amenitiesList = amenitiesRepository.findAllById(numberList);
		int water = 0;
		int safe = 0;
		int toilet = 0;
		for (Amenities amenities : amenitiesList) {
			if (amenities.getAmenitiesCodeId() == 1)water++;
			else if (amenities.getAmenitiesCodeId() == 2)toilet++;
			else safe++;
		}

		return TrackingDetailResponse.toDto(tracking, water, toilet, safe);
	}

	@Override
	public GetTrackingListByCreatorResponse getAllByCreator(Integer userId, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "trackingId"));
		Page<Tracking> byUserIdIn = trackingRepository.findByUserIdIn(Collections.singleton(userId), pageable);
		List<TrackingResponse> result = new ArrayList<>();
		for (Tracking tracking : byUserIdIn) {
			if (!tracking.isDeleted()){
				TrackingResponse trackingResponse = new TrackingResponse(tracking);
				result.add(trackingResponse);
			}
		}
		return GetTrackingListByCreatorResponse.builder()
				.totalPage(byUserIdIn.getTotalPages())
				.totalSize(byUserIdIn.getTotalElements())
				.trackingResponses(result)
				.build();
	}


	private String createMessage(Tracking tracking) {
		return tracking.getTrackingId()
			+ ","
			+ tracking.getLatitude()
			+ ","
			+ tracking.getLongitude()
			+ ","
			+ tracking.getDistance()
			+ ","
			+ tracking.getTime();
	}

}
