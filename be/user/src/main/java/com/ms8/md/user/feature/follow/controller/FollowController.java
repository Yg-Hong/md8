package com.ms8.md.user.feature.follow.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.user.feature.follow.dto.request.FollowBlockRequest;
import com.ms8.md.user.feature.follow.dto.request.FollowSaveRequest;
import com.ms8.md.user.feature.follow.dto.response.FollowBlockResponse;
import com.ms8.md.user.feature.follow.dto.response.FollowNormalResponse;
import com.ms8.md.user.feature.follow.service.FollowService;
import com.ms8.md.user.global.common.code.SuccessCode;
import com.ms8.md.user.global.common.response.ErrorResponse;
import com.ms8.md.user.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/api/follows")
@Tag(name = "follow", description = "팔로우 관리 API")
public class FollowController {

	private final FollowService followService;

	@PostMapping
	@Operation(summary = "팔로우 등록", description = "특정 회원에게 팔로우를 합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "팔로우를 정상적으로 등록하였습니다."),
		@ApiResponse(responseCode = "400", description = "3001: 팔로우 할 수 없는 대상입니다.", content = @Content(
			schema = @Schema(implementation = ErrorResponse.class)
		))
	})
	public SuccessResponse<?> createFollow(@Valid @RequestBody FollowSaveRequest request) {
		followService.createFollow(request);

		return updateSuccessResponse(SuccessCode.INSERT_SUCCESS);
	}

	@GetMapping("/follower/{userId}")
	@Operation(summary = "팔로워 목록 조회", description = "본인을 팔로우하고 있는 회원목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "팔로워 목록을 정상적으로 조회하였습니다.", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = FollowNormalResponse.class))
	))
	@Parameter(name = "userId", description = "회원 ID(본인)", example = "5")
	public SuccessResponse<List<FollowNormalResponse>> getFollowerList(@PathVariable Integer userId) {
		List<FollowNormalResponse> followerList = followService.getNormalFollowerList(userId);

		return SuccessResponse.<List<FollowNormalResponse>>builder()
			.data(followerList)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/following/{userId}")
	@Operation(summary = "팔로잉 목록 조회", description = "본인이 팔로잉하고 있는 회원목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "팔로잉 목록을 정상적으로 조회하였습니다.", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = FollowNormalResponse.class))
	))
	@Parameter(name = "userId", description = "회원 ID(본인)", example = "5")
	public SuccessResponse<List<FollowNormalResponse>> getFollwingList(@PathVariable Integer userId) {
		List<FollowNormalResponse> follwingList = followService.getNormalFollwingList(userId);

		return SuccessResponse.<List<FollowNormalResponse>>builder()
			.data(follwingList)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/block/{userId}")
	@Operation(summary = "차단한 팔로워/팔로잉 목록 조회", description = "본인이 차단한 팔로워/팔로잉 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "차단 목록을 정상적으로 조회했습니다.", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = FollowBlockResponse.class))
	))
	@Parameter(name = "userId", description = "회원 ID(본인)", example = "5")
	public SuccessResponse<List<FollowBlockResponse>> getBlockFollowList(@PathVariable Integer userId) {
		List<FollowBlockResponse> blockFollowList = followService.getBlockFollowList(userId);

		return SuccessResponse.<List<FollowBlockResponse>>builder()
			.data(blockFollowList)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@PatchMapping("/block/follower")
	@Operation(summary = "팔로워 차단", description = "특정 회원을 차단합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "팔로워 차단을 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다")
	})
	public SuccessResponse<?> blockFollower(@Valid @RequestBody FollowBlockRequest request) {
		followService.blockFollower(request);
		return updateSuccessResponse(SuccessCode.UPDATE_SUCCESS);
	}

	@PatchMapping("/block/following")
	@Operation(summary = "팔로잉 차단", description = "특정 회원을 차단합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "팔로잉 차단을 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다")
	})
	public SuccessResponse<?> blockFollowing(@Valid @RequestBody FollowBlockRequest request) {
		followService.blockFollowing(request);
		return updateSuccessResponse(SuccessCode.UPDATE_SUCCESS);
	}

	@PatchMapping("/unblock/follower")
	@Operation(summary = "팔로워 차단해제", description = "차단된 팔로워를 차단해제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "팔로워 차단해제를 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다")
	})
	public SuccessResponse<?> unblockFollower(@Valid @RequestBody FollowBlockRequest request) {
		followService.unBlockFollower(request);
		return updateSuccessResponse(SuccessCode.UPDATE_SUCCESS);
	}

	@PatchMapping("/unblock/following")
	@Operation(summary = "팔로잉 차단해제", description = "차단된 팔로잉을 차단해제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "팔로잉 차단해제를 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다")
	})
	public SuccessResponse<?> unblockFollowing(@Valid @RequestBody FollowBlockRequest request) {
		followService.unBlockFollowing(request);
		return updateSuccessResponse(SuccessCode.UPDATE_SUCCESS);
	}

	@DeleteMapping("/follower")
	@Operation(summary = "팔로워 삭제", description = "팔로워를 삭제합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "팔로워 삭제를 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다")
	})
	public SuccessResponse<?> removeFollower(@Valid @RequestBody FollowBlockRequest request) {
		followService.removeFollower(request);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.DELETE_SUCCESS)
			.message(SuccessCode.DELETE_SUCCESS.getMessage())
			.build();
	}

	@DeleteMapping("/following")
	@Operation(summary = "팔로잉 삭제", description = "팔로잉을 삭제합니다")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "팔로잉 삭제를 성공하였습니다."),
		@ApiResponse(responseCode = "400", description = "데이터가 존재하지 않습니다")
	})
	public SuccessResponse<?> removeFollowing(@Valid @RequestBody FollowBlockRequest request) {
		followService.removeFollowing(request);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.DELETE_SUCCESS)
			.message(SuccessCode.DELETE_SUCCESS.getMessage())
			.build();
	}

	private static SuccessResponse<Object> updateSuccessResponse(SuccessCode updateSuccess) {
		return SuccessResponse.builder()
			.data(null)
			.status(updateSuccess)
			.message(updateSuccess.getMessage())
			.build();
	}
}
