package com.ms8.md.tracking.feature.tracking.dto.request;

import java.util.List;
import java.util.stream.Collectors;

import com.ms8.md.tracking.feature.amenities.dto.request.AmenitiesCreateRequest;
import com.ms8.md.tracking.feature.tracking.dto.common.Route;
import com.ms8.md.tracking.feature.tracking.entity.Tracking;
import com.ms8.md.tracking.feature.tracking.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrackingCreateRequest {

	@NotBlank @Size(max = 60)
	@Schema(description = "제목", example = "Title")
	private String title;

	@Schema(description = "내용", example = "Content")
	private String content;

	@NotBlank
	@Schema(description = "위도", example = "15.087269")
	private Double latitude;

	@NotBlank
	@Schema(description = "경도", example = "37.502669")
	private Double longitude;

	@NotBlank
	@Schema(description = "위도변화량", example = "0.0001")
	private Double latitudeDelta;

	@NotBlank
	@Schema(description = "경도변화량", example = "0.0001")
	private Double longitudeDelta;

	@NotBlank
	@Schema(description = "경로", example = "[]")
	private List<Route> route;

	@NotBlank
	@Schema(description = "소요시간", example = "3600")
	private Integer time;

	@NotBlank
	@Schema(description = "거리", example = "1000")
	private Integer distance;

	@NotBlank
	@Schema(description = "추천여부", example = "true")
	private Boolean isRecommend;

	@NotNull
	@Schema(description = "편의시설 목록", example = "[]")
	private List<Integer> amenitiesList;

	public Tracking toEntity(TrackingCreateRequest request, Integer userId) {
		Tracking tracking = Tracking.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.route(request.getRoute())
			.time(request.getTime())
			.distance(request.getDistance())
			.user(User.builder().id(userId).build())
			.isRecommend(request.getIsRecommend())
			.isValidToRecommend(true)
			.build();
		String result = request.getAmenitiesList().stream()
			.map(String::valueOf) // Integer를 String으로 변환
			.collect(Collectors.joining(", "));
		tracking.setAmenitiesList(result);
		return tracking;
	}

}
