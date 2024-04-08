 package com.ms8.md.sns.feature.pin.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(title = "내 핀 정보")
public class MyPinResponse {

	@Schema(description = "핀ID", example = "1")
	private Long pinId;
	@Schema(description = "사진 URL")
	private String photoURL;
	@Schema(description = "경과 시간 1시간 미만은 분으로표시, 그 이상은 시간", example = "2시간")
	private String elapsedTime;

	@Builder
	private MyPinResponse(Long pinId, String photoURL, String elapsedTime) {
		this.pinId = pinId;
		this.photoURL = photoURL;
		this.elapsedTime = elapsedTime;
	}
}
