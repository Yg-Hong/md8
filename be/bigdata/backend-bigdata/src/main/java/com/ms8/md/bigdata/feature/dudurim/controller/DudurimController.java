package com.ms8.md.bigdata.feature.dudurim.controller;

import com.ms8.md.bigdata.feature.dudurim.dto.response.ResponseDudurimDetailDto;
import com.ms8.md.bigdata.feature.dudurim.dto.response.ResponseDudurimDto;
import com.ms8.md.bigdata.feature.dudurim.service.DudurimService;
import com.ms8.md.bigdata.global.common.response.ErrorResponse;
import com.ms8.md.bigdata.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/bigdata/dudurim")
public class DudurimController {
    private final DudurimService dudurimService;

    @GetMapping
    @Operation(summary = "전체 두드림길", description = "두드림길을 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 두드림길을 가져왔습니다."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청입니다.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "두드림길을 찾을 수 없습니다.",
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
    public ResponseEntity<SuccessResponse<ResponseDudurimDto>> getDudurim(@RequestParam Double lat, @RequestParam Double lon){


        ResponseDudurimDto result = dudurimService.getDudurim(lat, lon);
        return new ResponseEntity<>(SuccessResponse.<ResponseDudurimDto>builder()
            .data(result)
            .status(200)
            .message("성공적으로 추천 목록을 가져왔습니다.").build(), HttpStatus.OK);
    }

    @GetMapping("/{ptrackingId}")
    @Operation(summary = "전체 두드림길", description = "두드림길을 상세를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 두드림길 상세를 가져왔습니다."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "잘못된 요청입니다.",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 두드림길을 찾을 수 없습니다.",
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
    public ResponseEntity<SuccessResponse<ResponseDudurimDetailDto>> getDudurimDetail(@PathVariable Integer ptrackingId){
        ResponseDudurimDetailDto result = dudurimService.getDudurimDetail(ptrackingId);
        return new ResponseEntity<>(SuccessResponse.<ResponseDudurimDetailDto>builder()
            .data(result)
            .status(200)
            .message("성공적으로 추천 목록을 가져왔습니다.").build(), HttpStatus.OK);
    }
}
