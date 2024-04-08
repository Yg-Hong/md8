package com.ms8.md.sns.feature.pin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "핀 저장 정보")
public record PinSaveRequest(@Schema(description = "위도", example = "37.56577")
							 Double lat,
							 @Schema(description = "경도", example = "126.7345")
							 Double lng) {
}
