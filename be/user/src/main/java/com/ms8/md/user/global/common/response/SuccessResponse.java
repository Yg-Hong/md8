package com.ms8.md.user.global.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import com.ms8.md.user.global.common.code.SuccessCode;

/**
 * [공통] API Response 결과의 반환 값을 관리
 */
@Getter
public class SuccessResponse<T> {

    // API 응답 결과 Response
    @Schema(description = "데이터 구조", example = "data:[{name: ...}]")
    private T data;

    // API 응답 코드 Message
    @Schema(description = "상태코드", example = "200")
    private SuccessCode status;
    @Schema(description = "메시지", example = "조회를 성공하였습니다.")
    private String message;


    @Builder
    public SuccessResponse(final T data, final SuccessCode status, final String message) {
    // public SuccessResponse(final T data, final SuccessCode status) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

}
