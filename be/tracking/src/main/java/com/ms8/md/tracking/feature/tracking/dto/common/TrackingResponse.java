package com.ms8.md.tracking.feature.tracking.dto.common;

import java.time.LocalDateTime;
import java.util.List;

import com.ms8.md.tracking.feature.amenities.dto.response.AmenitiesResponse;
import com.ms8.md.tracking.feature.tracking.dto.common.Route;
import com.ms8.md.tracking.feature.tracking.entity.Tracking;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TrackingResponse {
	private Long trackingId;
	private String title;
	private Integer distance;
	private Integer time;
	private Integer kcal;
	private LocalDateTime createdAt;
	private Integer bookmark;

	@Builder
	public TrackingResponse(Tracking tracking) {
		this.trackingId = tracking.getTrackingId();
		this.title = tracking.getTitle();
		this.distance = tracking.getDistance();
		this.time = tracking.getTime();
		this.kcal = tracking.getKcal();
		this.createdAt = tracking.getCreateAt();
		this.bookmark = tracking.getTrackingBookmarks().size();
	}

}
