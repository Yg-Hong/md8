package com.ms8.md.user.feature.code.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ms8.md.user.feature.code.dto.request.DetailCodeModifyRequest;
import com.ms8.md.user.feature.code.dto.request.DetailCodeSaveRequest;
import com.ms8.md.user.feature.code.dto.response.DetailCodeResponse;
import com.ms8.md.user.feature.code.dto.response.SimpleDetailCodeResponse;
import com.ms8.md.user.feature.code.service.DetailCodeService;
import com.ms8.md.user.global.common.code.SuccessCode;
import com.ms8.md.user.global.common.response.ErrorResponse;
import com.ms8.md.user.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
@RequestMapping("/api/detail-codes")
@Tag(name = "detail code", description = "상세코드 관리 API")
public class DetailCodeController {

	private final DetailCodeService detailCodeService;

	// DATA_ALREADY_EXIST(400, "2002", "이미 해당 데이터가 존재합니다.")

	@PostMapping("/{codeId}")
	@Operation(summary = "상세코드 등록", description = "입력된 내용으로 상세코드 등록을 합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "상세코드 등록을 성공하였습니다."),
		@ApiResponse(responseCode = "2002", description = "이미 해당 데이터가 존재합니다."),
	})
	@Parameters({
		@Parameter(name = "codeId", description = "코드ID", example = "5"),
	})
	public SuccessResponse<?> createDetailCode(@PathVariable(name = "codeId") Integer codeId, @Valid @RequestBody DetailCodeSaveRequest request) {
		detailCodeService.createDetailCode(codeId, request);
		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.INSERT_SUCCESS)
			.message(SuccessCode.INSERT_SUCCESS.getMessage())
			.build();
	}

	@GetMapping("/{codeId}")
	@Operation(summary = "상세코드 목록조회", description = "입력된 코드ID로 상세코드 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200" , description = "상세코드 조회를 성공하였습니다.", content = @Content(
			mediaType = "application/json",
			array = @ArraySchema(schema = @Schema(implementation = DetailCodeResponse.class))
		)),
	})
	@Parameter(name = "codeId", description = "코드ID", example = "5")
	public SuccessResponse<List<DetailCodeResponse>> getDetailCodeList(@PathVariable(name = "codeId") Integer codeId) {
		List<DetailCodeResponse> detailCodeResponses = detailCodeService.getDetailCodeList(codeId);
		return SuccessResponse.<List<DetailCodeResponse>>builder()
    		.data(detailCodeResponses)
    		.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
    		.build();
	}

	@GetMapping("/simple/{codeName}")
	@Operation(summary = "상세코드 목록 조회 by 코드명", description = "입력된 코드명으로 상세코드 목록을 조회합니다.")
	@ApiResponse(responseCode = "200",description = "성공", content = @Content(
		mediaType = "application/json",
		array = @ArraySchema(schema = @Schema(implementation = SimpleDetailCodeResponse.class))
	))
	public SuccessResponse<List<SimpleDetailCodeResponse>> getDetailSimpleCodelist(
		@Parameter(description = "조회하려는 코드명", example = "Content") @PathVariable(name = "codeName") String codeName) {

		List<SimpleDetailCodeResponse> result = detailCodeService.getSimpleDetailCodeList(codeName);

		return SuccessResponse.<List<SimpleDetailCodeResponse>>builder()
			.data(result)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}

	@Operation(summary = "상세코드 조회", description = "입력된 코드ID와 상세코드명으로 상세코드를 조회합니다.")
	@Parameters(value = {
		@Parameter(name = "codeId",description = "코드ID"),
		@Parameter(name = "name", description = "상세코드명")
	})
	@GetMapping
	public SuccessResponse<DetailCodeResponse> getDetailCode(@RequestParam("codeId") Integer codeId,
		@RequestParam("name") String detailCodeName) {
		DetailCodeResponse response = detailCodeService.getDetailCodeResponse(codeId, detailCodeName);

		return SuccessResponse.<DetailCodeResponse>builder()
			.data(response)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}


	@PatchMapping("/{detailId}")
	@Operation(summary = "상세코드 수정", description = "입력된 상세코드ID에 맞는 상세코드를 찾아 정보를 변경합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "204", description = "상세코드 수정을 성공하였습니다." ),
		@ApiResponse(responseCode = "2001", description = "데이터가 존재하지 않습니다",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@Parameter(description = "상세코드ID", example = "1")
	public SuccessResponse<?> modifyDetailCode(@PathVariable(name = "detailId") Integer detailId, @Valid @RequestBody DetailCodeModifyRequest request) {
		detailCodeService.modifyDetailCode(detailId, request);
		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.UPDATE_SUCCESS)
			.message(SuccessCode.UPDATE_SUCCESS.getMessage())
			.build();
	}

	@DeleteMapping("/{detailId}")
	@Operation(summary = "상세코드 삭제", description = "입력된 상세코드ID에 맞는 상세코드를 찾아 삭제합니다.")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "상세코드 삭제를 성공하였습니다."),
		@ApiResponse(responseCode = "2001", description = "데이터가 존재하지 않습니다",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	@Parameter(description = "상세코드ID", example = "1")
	public SuccessResponse<?> removeDetailCode(@PathVariable(name = "detailId") Integer detailId) {
		detailCodeService.removeDetailCode(detailId);
		return SuccessResponse.builder()
			.data(null)
			.status(SuccessCode.DELETE_SUCCESS)
			.message(SuccessCode.DELETE_SUCCESS.getMessage())
			.build();
	}
}
