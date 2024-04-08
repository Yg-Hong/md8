package com.ms8.md.tracking.feature.amenities.dto.request;

import java.util.List;

import com.ms8.md.tracking.feature.amenities.entity.Amenities;
import com.ms8.md.tracking.feature.tracking.entity.Tracking;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AmenitiesCreateRequest {

	@NotBlank
	@Schema(description = "편의시설 코드 ID", example = "3")
	private Integer amenitiesCodeId;

	@NotBlank
	@Schema(description = "위도", example = "37.123456")
	private Double latitude;

	@NotBlank
	@Schema(description = "경도", example = "127.123456")
	private Double longitude;

	@Schema(description = "위도변화량", example = "0.0001")
	private Double latitudeDelta;

	@Schema(description = "경도변화량", example = "0.0001")
	private Double longitudeDelta;

	public Amenities toEntity(AmenitiesCreateRequest request, Tracking tracking) {
		return Amenities.builder()
			.amenitiesCodeId(request.getAmenitiesCodeId())
			.build();
	}

	private List<AmenitiesCreateRequest> requestList;

}
