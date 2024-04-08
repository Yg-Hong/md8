package com.ms8.md.recommendation.feature.reco.service;

import java.util.ArrayList;
import java.util.List;

import com.ms8.md.recommendation.feature.reco.entity.Amenities;
import com.ms8.md.recommendation.feature.reco.repository.AmenitiesRepository;
import org.springframework.stereotype.Service;

import com.ms8.md.recommendation.feature.reco.dto.common.TrackingDto;
import com.ms8.md.recommendation.feature.reco.dto.request.RequestRecoTrackingDto;
import com.ms8.md.recommendation.feature.reco.dto.response.ResponseRecoTrackingDto;
import com.ms8.md.recommendation.feature.reco.entity.Tracking;
import com.ms8.md.recommendation.feature.reco.repository.TrackingRepository;
import com.ms8.md.recommendation.feature.reco.utils.FaissClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecoServiceImpl implements RecoService{

	private final FaissClient faissClient;
	private final TrackingRepository trackingRepository;
	private final AmenitiesRepository amenitiesRepository;

	@Override
	public ResponseRecoTrackingDto recoTrackings(RequestRecoTrackingDto request) {
		List<Long> result = faissClient.faissReco(request.getLat(), request.getLon(), request.getTime(), request.getDist(), request.getNum());
		List<Tracking> allById = trackingRepository.findByIdInAndIsDeleted(result, false);

		List<TrackingDto> trackings = new ArrayList<>();
		for (Tracking tracking : allById) {
			TrackingDto build = TrackingDto.builder().id(tracking.getId())
					.title(tracking.getTitle())
					.time(tracking.getTime())
					.distance(tracking.getTime())
					.bookMark(tracking.getTrackingBookmarks().size())
					.kcal(tracking.getKcal())
					.water(false)
					.toilet(false)
					.safe(false)
					.createdAt(tracking.getCreatedAt())
					.build();
			if(tracking.getAmenitiesList() == null || tracking.getAmenitiesList().equals("")){
				trackings.add(build);
				continue;
			}

			String[] numbersArray = tracking.getAmenitiesList().split(",\\s*");
			List<Long> numberList = new ArrayList<>();
			for (String number : numbersArray) {
				numberList.add(Long.parseLong(number));
			}
			List<Amenities> amenitiesList = amenitiesRepository.findAllById(numberList);

			for (Amenities amenities : amenitiesList) {
				if(amenities.getAmenitiesCodeId() == 1){
					build.setWater(true);
				} else if (amenities.getAmenitiesCodeId() == 2) {
					build.setToilet(true);
				}else if (amenities.getAmenitiesCodeId() == 3){
					build.setSafe(true);
				}
			}

			trackings.add(build);
		}
		return ResponseRecoTrackingDto.builder().recoResult(trackings).build();
	}
}
