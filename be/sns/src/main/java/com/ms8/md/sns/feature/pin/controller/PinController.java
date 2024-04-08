package com.ms8.md.sns.feature.pin.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.sns.feature.pin.dto.request.PinSaveRequest;
import com.ms8.md.sns.feature.pin.dto.response.MyPinResponse;
import com.ms8.md.sns.feature.pin.dto.response.PinResponse;
import com.ms8.md.sns.feature.pin.service.PinService;
import com.ms8.md.sns.global.common.code.SuccessCode;
import com.ms8.md.sns.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pins")
@Tag(name = "pin", description = "핀 관리 API")
public class PinController {

	private final PinService pinService;

	@PostMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "핀 생성")
	public SuccessResponse<?> createPin(@Parameter(description = "회원ID") @PathVariable(name = "userId") Integer userId,
		@Valid
		@ParameterObject
		@ModelAttribute PinSaveRequest request,
		@Parameter(description = "이미지파일")
		@ModelAttribute MultipartFile file) {
		log.info("받은 정보 userId={} \n pinData={} \n img={}", userId, request, file);

		pinService.createPin(userId, request, file);
		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.INSERT_SUCCESS)
			.message(SuccessCode.INSERT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping
	@Operation(summary = "핀 전체 조회", description = "중심 좌표를 기준으로 클라이언트에서 받은 반경(m 단위)내에 있는 모든 핀 조회")
	@ApiResponse(responseCode = "200", description = "핀 전체 조회에 성공했습니다", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = PinResponse.class))
	))
	public SuccessResponse<List<PinResponse>> getAllPins(
		@Parameter(description = "위도", example = "37.56820877993765") @RequestParam("lat") Double lat,
		@Parameter(description = "경도", example = "126.97806714417614") @RequestParam("lng") Double lng,
		@Parameter(description = "반경(m단위)", example = "50") @RequestParam("radius") Double radius) {

		List<PinResponse> list = pinService.getAllPins(lat, lng, radius);
		return SuccessResponse.<List<PinResponse>>builder()
			.data(list)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/{userId}")
	@Operation(summary = "내 핀 조회", description = "24시간이 지나지 않은 내 핀들을 조회한다.")
	@ApiResponse(responseCode = "200", description = "성공", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = MyPinResponse.class))
	))
	public SuccessResponse<List<MyPinResponse>> getMyPins(
		@Parameter(description = "회원ID") @PathVariable("userId") Integer userId) {
		List<MyPinResponse> result = pinService.getMyPins(userId);

		return SuccessResponse.<List<MyPinResponse>>builder()
			.data(result)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@PatchMapping("/{pinId}")
	@Operation(summary = "핀 삭제", description = "입력받은 pinId로 핀 삭제")
	public SuccessResponse<?> removePin(
		@Parameter(description = "삭제하려는 핀 ID", example = "1")
		@PathVariable Long pinId) {

		log.info("받은 핀 ID={}", pinId);
		pinService.removePin(pinId);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.DELETE_SUCCESS)
			.message(SuccessCode.DELETE_SUCCESS.getMessage())
			.build();
	}
}
