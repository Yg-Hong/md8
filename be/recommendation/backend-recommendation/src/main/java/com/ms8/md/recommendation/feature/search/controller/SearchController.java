package com.ms8.md.recommendation.feature.search.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.recommendation.feature.reco.dto.response.ResponseRecoTrackingDto;
import com.ms8.md.recommendation.feature.search.dto.response.ResponseSearchByContentDto;
import com.ms8.md.recommendation.feature.search.dto.response.ResponseSearchByTitleDto;
import com.ms8.md.recommendation.feature.search.service.SearchService;
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
@RequestMapping(value = "/api/recommendation/search")
@Tag(name = "Search", description = "검색 API Doc")
public class SearchController {
	private final SearchService searchService;

	@GetMapping("/title")
	@Operation(summary = "제목 기반 산책로 검색", description = "제목을 기반을 산책로를 검색합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "성공적으로 검색 목록을 가져왔습니다."
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
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
	public ResponseEntity<SuccessResponse<ResponseSearchByTitleDto>> searchByTitle(@RequestParam String keyword, @RequestParam Integer page, @RequestParam Integer size){
		ResponseSearchByTitleDto result = searchService.searchByTitle(keyword, page, size);
		return new ResponseEntity<>(SuccessResponse.<ResponseSearchByTitleDto>builder().data(result).status(200).message("성공적으로 추천 목록을 가져왔습니다.").build(), HttpStatus.OK);
	}

	@GetMapping("/content")
	@Operation(summary = "내용 기반 산책로 검색", description = "내용을 기반을 산책로를 검색합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "성공적으로 검색 목록을 가져왔습니다."
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
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
	public ResponseEntity<SuccessResponse<ResponseSearchByContentDto>> searchByContent(@RequestParam String keyword, @RequestParam Integer page, @RequestParam Integer size){
		ResponseSearchByContentDto result = searchService.searchByContent(keyword, page, size);
		return new ResponseEntity<>(SuccessResponse.<ResponseSearchByContentDto>builder().data(result).status(200).message("성공적으로 추천 목록을 가져왔습니다.").build(), HttpStatus.OK);
	}
}
