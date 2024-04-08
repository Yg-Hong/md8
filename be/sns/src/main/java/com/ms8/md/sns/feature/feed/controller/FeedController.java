package com.ms8.md.sns.feature.feed.controller;

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

import com.ms8.md.sns.feature.feed.dto.request.FeedModifyRequest;
import com.ms8.md.sns.feature.feed.dto.request.FeedSaveRequest;
import com.ms8.md.sns.feature.feed.dto.response.FeedDetailResponse;
import com.ms8.md.sns.feature.feed.dto.response.FeedSliceResponse;
import com.ms8.md.sns.feature.feed.dto.response.MyFeedSliceResponse;
import com.ms8.md.sns.feature.feed.service.FeedService;
import com.ms8.md.sns.global.common.code.SuccessCode;
import com.ms8.md.sns.global.common.response.ErrorResponse;
import com.ms8.md.sns.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feeds")
@Tag(name = "feed", description = "피드 관리 API")
public class FeedController {

	private final FeedService feedService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "피드 생성", description = "작성한 정보로 피드를 생성합니다.")
	@ApiResponse(responseCode = "201", description = "피드 생성을 성공했습니다.")
	public SuccessResponse<?> createFeed(
		@Valid
		@ParameterObject
		@ModelAttribute FeedSaveRequest request,
		@Parameter(description = "이미지 목록")
		@ModelAttribute List<MultipartFile> fileList) {

		feedService.createFeed(request, fileList);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.INSERT_SUCCESS)
			.message(SuccessCode.INSERT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/home/{userId}")
	@Operation(summary = "메인 피드 조회", description = "회원이 팔로잉 하고있는 회원들의 피드를 조회합니다.")
	@Parameters(value = {
		@Parameter(name = "userId", description = "현재 로그인된 회원ID", example = "1"),
		@Parameter(name = "lastFeedId", description = "마지막 조회된 피드ID, 최초 요청시엔 값 x")
	})
	public SuccessResponse<FeedSliceResponse> getFeedList(@PathVariable Integer userId,
		@RequestParam(name = "lastFeedId", required = false) Long lastFeedId) {
		FeedSliceResponse feedSlice = feedService.getFeedList(userId, lastFeedId);

		return SuccessResponse.<FeedSliceResponse>builder()
			.data(feedSlice)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/myfeed/{userId}")
	@Operation(summary = "내 피드 조회", description = "내가 작성한 피드 조회")
	public SuccessResponse<MyFeedSliceResponse> getMyFeedList(
		@Parameter(description = "회원ID") @PathVariable("userId") Integer userId,
		@Parameter(description = "마지막 조회 피드ID") @RequestParam(name = "lastFeedId", required = false) Long lastFeedId) {

		MyFeedSliceResponse result = feedService.getMyFeedList(userId, lastFeedId);

		return SuccessResponse.<MyFeedSliceResponse>builder()
			.data(result)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/{feedId}")
	@Operation(summary = "피드 상세 조회", description = "피드ID로 피드 상세 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "피드 상세 정보 조회를 성공했습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다")
	})
	@Parameter(name = "feedId", description = "조회하려는 피드ID", example = "1")
	public SuccessResponse<FeedDetailResponse> getFeedDetail(@PathVariable Long feedId) {
		FeedDetailResponse feedDetail = feedService.getFeedDetail(feedId);

		return SuccessResponse.<FeedDetailResponse>builder()
			.data(feedDetail)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();

	}

	@PatchMapping(value = "/{feedId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "피드 수정", description = "입력된 정보로 피드를 수정합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "피드 수정을 성공했습니다.")
	})
	public SuccessResponse<?> modifyFeed(
		@PathVariable @Parameter(description = "수정하려는 피드ID") Long feedId,
		@Valid
		@ParameterObject
		@ModelAttribute FeedModifyRequest request,
		@Parameter(description = "이미지 목록")
		@ModelAttribute List<MultipartFile> fileList) {

		log.info("modifyFeed feedId={} \n request ={} \n fileList={}", feedId, request, fileList);

		feedService.modifyFeed(feedId, request, fileList);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.UPDATE_SUCCESS)
			.message(SuccessCode.UPDATE_SUCCESS.getMessage())
			.build();
	}

	@PatchMapping("/remove/{feedId}")
	@Operation(summary = "피드 삭제", description = "입력된 피드ID로 피드를 찾아 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "피드 삭제를 성공했습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	public SuccessResponse<?> removeFeed(@PathVariable Long feedId) {
		// TODO 피드 삭제시, 해시태그, S3파일, photo 삭제처리 => 시간남으면 구현
		feedService.removeFeed(feedId);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.DELETE_SUCCESS)
			.message(SuccessCode.DELETE_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/search/hashtags")
	@Operation(summary = "해시태그 검색", description = "입력된 해시태그가 작성된 피드를 조회합니다.")
	public SuccessResponse<MyFeedSliceResponse> searchFeedByHashtag(
		@Parameter(description = "조회하려는 해시태그", example = "벚꽃") @RequestParam("hashtag") String hashtag,
		@Parameter(description = "마지막 조회 피드ID") @RequestParam(name = "lastFeedId", required = false) Long lastFeedId) {

		MyFeedSliceResponse result = feedService.searchFeedByHashtag(hashtag, lastFeedId);

		return SuccessResponse.<MyFeedSliceResponse>builder()
			.data(result)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

}
