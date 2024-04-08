package com.ms8.md.tracking.feature.tracking.controller;

import java.util.List;

import com.ms8.md.tracking.feature.tracking.dto.response.GetTrackingListByCreatorResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.tracking.feature.tracking.dto.request.TrackingCreateRequest;
import com.ms8.md.tracking.feature.tracking.dto.request.TrackingModifyRequest;
import com.ms8.md.tracking.feature.tracking.dto.response.TrackingCreationResponse;
import com.ms8.md.tracking.feature.tracking.dto.response.TrackingDetailResponse;
import com.ms8.md.tracking.feature.tracking.dto.common.TrackingResponse;
import com.ms8.md.tracking.feature.tracking.service.TrackingService;
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
@RequestMapping("/api/v1/tracking")
@Tag(name = "tracking", description = "산책로 API")
public class TrackingController {
	private final TrackingService trackingService;

	//산책 등록
	@PostMapping
	@Operation(summary = "산책로 등록", description = "입력된 내용으로 산책로를 등록 합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "산책 등록을 성공하였습니다."),
		@ApiResponse(responseCode = "202", description = "이미 해당 데이터가 존재합니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	public SuccessResponse<?> createTracking(@Validated @RequestBody TrackingCreateRequest request,
		@RequestParam(name = "userId") Integer userId) {
		TrackingCreationResponse tracking = trackingService.create(request, userId);
		return SuccessResponse.builder().data(tracking).status(SuccessCode.INSERT_SUCCESS).build();
	}

	//산책로 수정
	@PatchMapping("/{trackingId}")
	@Operation(summary = "산책로 수정", description = "입력된 산책로ID를 찾아 정보를 변경합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "산책로 수정을 성공하였습니다.")})
	@Parameter(name = "trackingId", description = "수정 대상 산책로ID", example = "5")
	public SuccessResponse<?> modifyTracking(@PathVariable Long trackingId,
		@Validated @RequestBody TrackingModifyRequest request) {
		trackingService.modify(trackingId, request);
		return SuccessResponse.builder().data(null).status(SuccessCode.UPDATE_SUCCESS).build();
	}

	// 산책로 삭제
	@DeleteMapping("/{trackingId}")
	@Operation(summary = "산책로 삭제", description = "입력된 산책로ID를 찾아 삭제합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "산책로 삭제를 성공하였습니다.")})
	@Parameter(name = "trackingId", description = "삭제 대상 산책로ID", example = "5")
	public SuccessResponse<?> removeTracking(@PathVariable Long trackingId) {
		trackingService.remove(trackingId);
		return SuccessResponse.builder().data(null).status(SuccessCode.DELETE_SUCCESS).build();
	}

	//산책로 상세 조회
	@GetMapping("/{trackingId}")
	@Operation(summary = "산책로 상세 조회", description = "입력된 산책로ID로 상세 조회를 합니다.")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "산책로 상세 조회를 성공하였습니다."),
		@ApiResponse(responseCode = "401", description = "데이터가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
	@Parameter(name = "trackingId", description = "산책로ID", example = "1")
	public SuccessResponse<TrackingDetailResponse> getTracking(@PathVariable(name = "trackingId") Long trackingId) {
		TrackingDetailResponse response = trackingService.getDetail(trackingId);
		return SuccessResponse.<TrackingDetailResponse>builder()
			.data(response)
			.status(SuccessCode.SELECT_SUCCESS)
			.build();
	}

	@GetMapping("/by-creator/count")
	@Operation(summary = "유저별 산책로 목록 조회", description = "시스템에 등록된 유저별 산책로 목록 조회를 합니다.")
	@ApiResponse(responseCode = "200", description = "유저별 산책로 목록 조회를 성공하였습니다.")
	public SuccessResponse<?> getCountTrackingListByCreator(@RequestParam(name = "userId") Integer userId) {
		GetTrackingListByCreatorResponse trackingList = trackingService.getAllByCreator(userId, 0, 1);
		Long result = trackingList.getTotalSize();
		return SuccessResponse.builder()
			.data(result)
			.status(SuccessCode.SELECT_SUCCESS)
			.build();
	}

	@GetMapping("/by-creator")
	@Operation(summary = "유저별 산책로 목록 조회", description = "시스템에 등록된 유저별 산책로 목록 조회를 합니다.")
	@ApiResponse(responseCode = "200", description = "유저별 산책로 목록 조회를 성공하였습니다.")
	public SuccessResponse<?> getTrackingListByCreator(@RequestParam(name = "userId") Integer userId, @RequestParam(name = "page") Integer page, @RequestParam(name = "size") Integer size) {
		GetTrackingListByCreatorResponse trackingList = trackingService.getAllByCreator(userId, page, size);
		return SuccessResponse.builder()
			.data(trackingList)
			.status(SuccessCode.SELECT_SUCCESS)
			.build();
	}
}
