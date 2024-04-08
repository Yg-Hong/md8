package com.ms8.md.recommendation.feature.reco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.recommendation.feature.reco.dto.request.RequestRecoTrackingDto;
import com.ms8.md.recommendation.feature.reco.dto.response.ResponseRecoTrackingDto;
import com.ms8.md.recommendation.feature.reco.service.RecoService;
import com.ms8.md.recommendation.global.common.response.ErrorResponse;
import com.ms8.md.recommendation.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/recommendation/reco")
@Tag(name = "Recommendation", description = "추천 API Doc")
public class RecoController {

	private final RecoService recoService;
	@GetMapping("/tracking")
	@Operation(summary = "산책로 추천", description = "30개의 산책로를 추천합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "성공적으로 추천 목록을 가져왔습니다."
			),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				schema = @Schema(implementation = ErrorResponse.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "추천 목록을 찾을 수 없습니다.",
			content = @Content(
				schema = @Schema(implementation = ErrorResponse.class)
			)),
		@ApiResponse(
			responseCode = "500",
			description = "처리 중 에러가 발생했습니다.",
			content = @Content(
				schema = @Schema(implementation = ErrorResponse.class)
			))
	})
	public ResponseEntity<SuccessResponse<ResponseRecoTrackingDto>> RecoTrackings(@Validated RequestRecoTrackingDto request){
		ResponseRecoTrackingDto result = recoService.recoTrackings(request);
		return new ResponseEntity<>(SuccessResponse.<ResponseRecoTrackingDto>builder().data(result).status(200).message("성공적으로 추천 목록을 가져왔습니다.").build(),
			HttpStatus.OK);
	}
}
