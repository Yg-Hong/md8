package com.ms8.md.tracking.feature.tracking.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ms8.md.tracking.feature.amenities.dto.response.AmenitiesResponse;
import com.ms8.md.tracking.feature.tracking.dto.common.Route;
import com.ms8.md.tracking.feature.tracking.entity.Tracking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDetailResponse {
	private Long trackingId;
	private String title;
	private LocalDateTime createdAt;
	private List<Route> route;
	private String content;
	private String nickName;
	private Integer userLevel;
	private Integer distance;
	private Integer kcal;
	private Integer time;
	private Integer water;
	private Integer toilet;
	private Integer safe;
	private Boolean isRecommend;
	private String profile;

	public static TrackingDetailResponse toDto(Tracking tracking, int water, int toilet, int safe){
		TrackingDetailResponse response = new TrackingDetailResponse();
		if(tracking != null){
			response.setTrackingId(tracking.getTrackingId());
			response.setTitle(tracking.getTitle());
			response.setCreatedAt(tracking.getCreateAt());
			response.setRoute(tracking.getRoute());
			response.setContent(tracking.getContent());
			response.setNickName(tracking.getUser().getNickName());
			response.setUserLevel(tracking.getUser().getLevel());
			response.setDistance(tracking.getDistance());
			response.setKcal(tracking.getKcal());
			response.setTime(tracking.getTime());
			response.setIsRecommend(tracking.getIsRecommend());
			response.setWater(water);
			response.setToilet(toilet);
			response.setSafe(safe);
			response.setProfile(tracking.getUser().getProfile());
		}
		return  response;
	}
}
