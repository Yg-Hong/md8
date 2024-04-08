package com.ms8.md.tracking.feature.coordinates.dto.request;

import com.ms8.md.tracking.global.common.GpsEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoordinatesGetRequest {
	@NotBlank
	@Schema(description = "위도", example = "36.1234")
	private Double Latitude;

	@NotBlank
	@Schema(description = "경도", example = "127.34123")
	private Double longitude;

	@NotBlank
	@Schema(description = "거리", example = "1.0")
	private Double distance;
}
