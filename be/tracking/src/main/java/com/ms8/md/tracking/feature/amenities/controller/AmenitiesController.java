package com.ms8.md.tracking.feature.amenities.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.tracking.feature.amenities.dto.request.AmenitiesCreateRequest;
import com.ms8.md.tracking.feature.amenities.dto.response.AmenitiesResponse;
import com.ms8.md.tracking.feature.amenities.service.AmenitiesService;
import com.ms8.md.tracking.feature.coordinates.dto.request.CoordinatesGetRequest;
import com.ms8.md.tracking.global.common.code.SuccessCode;
import com.ms8.md.tracking.global.common.response.ErrorResponse;
import com.ms8.md.tracking.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/amenities")
@Tag(name = "amenities", description = "편의시설 API")
public class AmenitiesController {

	/**
	 * TODO
	 * 편의시설 서비스 로직 추가
	 * 에러 처리 로직 추가
	 * 응답 DTO 추가
	 */

	private final AmenitiesService amenitiesService;
	//프론트 응답으로 수정
	private final PageRequest pageRequest = PageRequest.of(0, 100);


	//좌표별 펀의시설 목록 조회
	@PostMapping("/by-coordinates")
	@Operation(summary = "좌표별 편의시설 목록 조회", description = "입력된 좌표로 편의시설 목록 조회를 합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "편의시설 목록 조회를 성공하였습니다."),
		@ApiResponse(responseCode = "401", description = "데이터가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	public SuccessResponse<?> getAmenitiesListByCoordinates(@RequestBody CoordinatesGetRequest request) {
		List<AmenitiesResponse> amenitiesList = amenitiesService.getAmenitiesListByCoordinates(request, pageRequest);
		return SuccessResponse.<List<AmenitiesResponse>>builder()
			.data(amenitiesList)
			.status(SuccessCode.SELECT_SUCCESS)
			.build();
	}
}
