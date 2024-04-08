package com.ms8.md.user.feature.user.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.user.feature.user.dto.request.UserModifyMyInfoRequest;
import com.ms8.md.user.feature.user.dto.response.UserResponse;
import com.ms8.md.user.feature.user.dto.response.UserSearchResponse;
import com.ms8.md.user.feature.user.service.UserSearchService;
import com.ms8.md.user.feature.user.service.UserService;
import com.ms8.md.user.global.auth.dto.response.ReIssueResponse;
import com.ms8.md.user.global.common.code.SuccessCode;
import com.ms8.md.user.global.common.response.ErrorResponse;
import com.ms8.md.user.global.common.response.SuccessResponse;
import com.ms8.md.user.global.util.TokenProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "user", description = "유저 관리 API")
public class UserController {

	private final UserService userService;
	private final UserSearchService userSearchService;
	private final TokenProvider tokenProvider;
	private final StringRedisTemplate redisTemplate;

	@GetMapping("/{userId}")
	@Operation(summary = "회원 정보 조회", description = "DB에 등록된 userId로 상세 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "회원 상세 정보 조회를 성공했습니다."),
		@ApiResponse(responseCode = "401", description = "유효하지 않은 유저 입니다.", content = @Content(
			schema = @Schema(implementation = ErrorResponse.class)
		))
	})
	@Parameter(name = "userId", description = "회원 ID", example = "1")
	public SuccessResponse<UserResponse> getUserById(@PathVariable Integer userId) {
		UserResponse response = userSearchService.getUser(userId);
		log.info("UserController.getUser => 회원정보= {}", response);

		return SuccessResponse.<UserResponse>builder()
			.data(response)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/search/{userId}")
	@Operation(summary = "회원 검색", description = "서로 차단되지 않고, 탈퇴하지 않은 회원을 입력된 닉네임이 포함된 유저 목록을 조회합니다.")
	@ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = UserSearchResponse.class))
	))
	public SuccessResponse<List<UserSearchResponse>> getUserByNickName(
		@Parameter(description = "회원ID", example = "1") @PathVariable Integer userId,
		@Parameter(description = "회원 닉네임", example = "도구리") @RequestParam("nickName") String nickName) {

		List<UserSearchResponse> searchResults = userSearchService.getUserByNickName(userId, nickName);

		return SuccessResponse.<List<UserSearchResponse>>builder()
			.data(searchResults)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@PatchMapping(value = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Operation(summary = "회원 정보 수정", description = "회원 ID로 정보를 찾아 입력된 정보로 수정합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "회원 정보 수정을 성공했습니다."),
		@ApiResponse(responseCode = "401", description = "유효하지 않은 유저 입니다.", content = @Content(
			schema = @Schema(implementation = ErrorResponse.class)
		))
	})
	public SuccessResponse<?> modifyUser(
		@Parameter(description = "회원 ID", example = "1")
		@PathVariable Integer userId,
		@Valid
		@ParameterObject
		@ModelAttribute UserModifyMyInfoRequest request,
		@Parameter(description = "회원 이미지")
		@ModelAttribute MultipartFile file) {

		userService.modifyUser(userId, request, file);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.UPDATE_SUCCESS)
			.message(SuccessCode.UPDATE_SUCCESS.getMessage())
			.build();
	}

	@PostMapping("/{userId}")
	@Operation(summary = "회원 로그아웃", description = "로그아웃합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "로그아웃에 성공했습니다."),
		@ApiResponse(responseCode = "401", description = "유효하지 않은 유저 입니다.", content = @Content(
			schema = @Schema(implementation = ErrorResponse.class)
		))
	})
	@Parameter(name = "userId", description = "회원 ID", example = "1")
	public SuccessResponse<?> logOut(@PathVariable Integer userId) {
		userService.logOutUser(userId);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.LOGOUT_SUCCESS)
			.message(SuccessCode.LOGOUT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/access_token/reissue")
	@Operation(summary = "회원 토큰 재발급", description = "만료된 액세스토큰을 재발급 합니다.")
	public ResponseEntity<?> accessTokenReIssue(HttpServletRequest request) {
		log.info("UserController => accessTokenReIssue");
		String requestToken = tokenProvider.resolveToken(request);
		String email = tokenProvider.extractUserNameFromExpiredToken(requestToken);

		UserResponse userResponse = userService.getUserByEmail(email);
		log.info("카카오 아이디로 조회한 유저 정보 : {}", userResponse);
		String refreshToken = redisTemplate.opsForValue().get(userResponse.getEmail());

		// Refresh Token 만료 된 상황
		if (refreshToken == null) {
			log.info("UserController => Refresh Token Expired");
			return ResponseEntity.ok().build();
		}

		// Refresh Token 만료 안된 상황
		else {
			log.info("UserController => Access Token Expired & Refresh Token Validated");
			Authentication authentication = tokenProvider.getAuthentication(refreshToken);

			// accessToken 재발급
			String accessToken = tokenProvider.createAccessToken(authentication);
			return ResponseEntity.ok().body(new ReIssueResponse(accessToken));
		}
	}
}
