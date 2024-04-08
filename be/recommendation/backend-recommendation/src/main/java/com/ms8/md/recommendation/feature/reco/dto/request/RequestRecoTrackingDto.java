package com.ms8.md.recommendation.feature.reco.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestRecoTrackingDto {
	Integer num = 30;
	@NotNull(message = "경도를 입력해주세요.")
	Double lon;
	@NotNull(message = "위도를 입력해주세요.")
	Double lat;
	@NotNull(message = "선호 시간을 입력해주세요.")
	Integer time;
	@NotNull(message = "선호 거리를 입력해주세요.")
	Integer dist;
}
