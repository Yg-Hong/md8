package com.ms8.md.tracking.feature.coordinates.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.tracking.feature.coordinates.dto.request.CoordinatesGetRequest;
import com.ms8.md.tracking.feature.coordinates.service.CoordinatesService;
import com.ms8.md.tracking.global.common.code.SuccessCode;
import com.ms8.md.tracking.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/coordinates")
@Tag(name = "coordinates", description = "좌표 API")
public class CoordinatesController {

	private final CoordinatesService coordinatesService;

	//펀의시설 목록 조회
	@GetMapping("/{ownerType}")
	@Operation(summary = "좌표 목록 조회", description = "시스템에 등록된 좌표 목록 조회를 합니다.")
	@ApiResponse(responseCode = "200", description = "좌표 목록 조회를 성공하였습니다.")
	public SuccessResponse<?> getCoordinatesList(@PathVariable String ownerType, @RequestBody CoordinatesGetRequest request) {
		return SuccessResponse.builder()
			.data(coordinatesService.getAllByCoordinates(ownerType, request))
			.status(SuccessCode.SELECT_SUCCESS)
			.build();
	}

	@PostMapping("/sido/{latitude}/{longitude}")
	@Operation(summary = "시도 조회", description = "시도 조회를 합니다.")
	@ApiResponse(responseCode = "200", description = "시도 조회를 성공하였습니다.")
	public SuccessResponse<?> getSido(@PathVariable Double latitude, @PathVariable Double longitude) {
		return SuccessResponse.builder()
			.data(coordinatesService.getSido(latitude, longitude))
			.status(SuccessCode.SELECT_SUCCESS)
			.build();
	}
}
