package com.ms8.md.sns.feature.pin.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(title = "핀 정보")
public class PinResponse {

	@Schema(description = "핀ID", example = "1")
	private Long pinId;
	@Schema(description = "위도", example = "37.56820877993765 ")
	private Double lat;
	@Schema(description = "경도", example = "126.97806714417614")
	private Double lng;
	@Schema(description = "사진 URL")
	private String photoURL;
	@Schema(description = "만료 시간")
	private LocalDateTime expireTime;
	@Schema(description = "남은 시간")
	private String remainTime;

	@Builder
	private PinResponse(Long pinId, Double lat, Double lng, String photoURL, LocalDateTime expireTime,
		String remainTime) {
		this.pinId = pinId;
		this.lat = lat;
		this.lng = lng;
		this.photoURL = photoURL;
		this.expireTime = expireTime;
		this.remainTime = remainTime;
	}
}
