package com.ms8.md.bigdata.feature.weather.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.bigdata.feature.weather.dto.response.ResponseAddressDto;
import com.ms8.md.bigdata.feature.weather.dto.response.ResponseConditionDto;
import com.ms8.md.bigdata.feature.weather.dto.response.ResponseDustDto;
import com.ms8.md.bigdata.feature.weather.repository.WeatherService;
import com.ms8.md.bigdata.global.common.response.ErrorResponse;
import com.ms8.md.bigdata.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/bigdata/weather")
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping("/condition")
	@Operation(summary = "온도 및 상태", description = "온도 및 상태를 가져옵니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "성공적으로 온도 및 상태를 가져왔습니다."
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				schema = @Schema(implementation = ErrorResponse.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "온도 및 상태를 가져올 수 없습니다.",
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
	public ResponseEntity<SuccessResponse<ResponseConditionDto>> getCondition(@RequestParam Double lat, @RequestParam Double lon){
		ResponseConditionDto result = weatherService.getCondition(lat, lon);
		return new ResponseEntity<>(SuccessResponse.<ResponseConditionDto>builder()
			.data(result)
			.status(200)
			.message("성공적으로 가져왔습니다.").build(), HttpStatus.OK);
	}

	@GetMapping("/dust")
	@Operation(summary = "미세먼지 농도", description = "미세먼지 농도 가져옵니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "성공적으로 미세먼지 농도를 가져왔습니다."
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				schema = @Schema(implementation = ErrorResponse.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "미세먼지 농도를 찾을 수 없습니다.",
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
	public ResponseEntity<SuccessResponse<ResponseDustDto>> getDust(@RequestParam Double lat, @RequestParam Double lon){
		ResponseDustDto result = weatherService.getDust(lat, lon);
		return new ResponseEntity<>(SuccessResponse.<ResponseDustDto>builder()
			.data(result)
			.status(200)
			.message("성공적으로 가져왔습니다.").build(), HttpStatus.OK);
	}

	@GetMapping("/address")
	@Operation(summary = "현재 주소", description = "현재 주소를 가져옵니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "성공적으로 현재 주소를 가져왔습니다."
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				schema = @Schema(implementation = ErrorResponse.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "현재 주소를 찾을 수 없습니다.",
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
	public ResponseEntity<SuccessResponse<ResponseAddressDto>> getAddress(@RequestParam Double lat, @RequestParam Double lon){
		ResponseAddressDto result = weatherService.getAddress(lat, lon);
		return new ResponseEntity<>(SuccessResponse.<ResponseAddressDto>builder()
			.data(result)
			.status(200)
			.message("성공적으로 가져왔습니다.").build(), HttpStatus.OK);
	}
}
