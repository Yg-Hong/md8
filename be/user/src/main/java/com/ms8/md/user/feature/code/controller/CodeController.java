package com.ms8.md.user.feature.code.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.user.feature.code.dto.request.CodeModifyRequest;
import com.ms8.md.user.feature.code.dto.request.CodeSaveRequest;
import com.ms8.md.user.feature.code.dto.response.CodeResponse;
import com.ms8.md.user.feature.code.service.CodeService;
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
@RequestMapping("/api/codes")
@Tag(name = "code", description = "코드 관리 API")
public class CodeController {

	private final CodeService codeService;

	@GetMapping
	@Operation(summary = "코드 목록 조회", description = "시스템에 등록된 코드 목록 조회를 합니다.")
	@ApiResponse(responseCode = "200", description = "코드 목록 조회를 성공하였습니다.", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = CodeResponse.class))
	))
	public SuccessResponse<List<CodeResponse>> getCodeList() {
		List<CodeResponse> codeList = codeService.getCodeList();

		return SuccessResponse.<List<CodeResponse>>builder()
			.data(codeList)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/{codeId}")
	@Operation(summary = "코드 상세 조회", description = "입력된 코드ID로 상세 조회를 합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "코드 상세 조회를 성공하였습니다."),
		@ApiResponse(responseCode = "401", description = "데이터가 존재하지 않습니다.", content = @Content(
			schema = @Schema(implementation = ErrorResponse.class)
		))
	})
	@Parameter(name = "codeId", description = "코드ID", example = "5")
	public SuccessResponse<CodeResponse> getCodeById(@PathVariable(name = "codeId") Integer codeId) {
		CodeResponse codeResponse = codeService.getCodeResponse(codeId);
		return SuccessResponse.<CodeResponse>builder()
			.data(codeResponse)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/search/{codeName}")
	@Operation(summary = "코드 상세 조회", description = "입력된 코드명으로 상세 조회를 합니다.")
	@ApiResponse(responseCode = "200", description = "코드 상세 조회를 성공하였습니다.")
	@Parameter(name = "codeName", description = "코드명", example = "Content")
	public SuccessResponse<CodeResponse> getCodeByName(@PathVariable(name = "codeName") String name) {
		CodeResponse codeResponse = codeService.getCodeResponseByName(name);
		return SuccessResponse.<CodeResponse>builder()
			.data(codeResponse)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@PostMapping
	@Operation(summary = "코드 등록", description = "입력된 내용으로 코드 등록을 합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "코드 등록을 성공하였습니다."),
		@ApiResponse(responseCode = "2002", description = "이미 해당 데이터가 존재합니다.",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	public SuccessResponse<?> createCode(@Valid @RequestBody CodeSaveRequest request) {
		codeService.createCode(request);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.INSERT_SUCCESS)
			.message(SuccessCode.INSERT_SUCCESS.getMessage())
			.build();
	}

	@PatchMapping("/{codeId}")
	@Operation(summary = "코드 수정", description = "입력된 코드ID를 찾아 정보를 변경합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "코드 수정을 성공하였습니다.")
	})
	@Parameter(name = "codeId", description = "수정 대상 코드ID", example = "5")
	public SuccessResponse<?> modifyCode(@PathVariable Integer codeId,@Valid @RequestBody CodeModifyRequest request) {
		codeService.modifyCode(codeId ,request);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.UPDATE_SUCCESS)
			.message(SuccessCode.UPDATE_SUCCESS.getMessage())
			.build();
	}

	@DeleteMapping("/{codeId}")
	@Operation(summary = "코드 삭제", description = "입력된 코드ID를 찾아 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "코드 삭제를 성공하였습니다.")
	})
	@Parameter(name = "codeId", description = "삭제 대상 코드ID", example = "5")
	public SuccessResponse<?> removeCode(@PathVariable Integer codeId) {
		codeService.removeCode(codeId);

		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.DELETE_SUCCESS)
			.message(SuccessCode.DELETE_SUCCESS.getMessage())
			.build();
	}
}
