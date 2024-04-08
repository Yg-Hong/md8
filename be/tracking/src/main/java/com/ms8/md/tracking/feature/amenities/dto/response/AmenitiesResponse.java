package com.ms8.md.tracking.feature.amenities.dto.response;

import com.ms8.md.tracking.feature.amenities.entity.Amenities;

import lombok.Builder;
import lombok.Data;

@Data
public class AmenitiesResponse {

	private Long amenitiesId;
	private Double latitude;
	private Double longitude;
	private Integer amenitiesCodeId;

	@Builder
	public AmenitiesResponse(Amenities amenities) {
		this.amenitiesId = amenities.getAmenitiesId();
		this.latitude = amenities.getLatitude();
		this.longitude = amenities.getLongitude();
		this.amenitiesCodeId = amenities.getAmenitiesCodeId();
	}
}
