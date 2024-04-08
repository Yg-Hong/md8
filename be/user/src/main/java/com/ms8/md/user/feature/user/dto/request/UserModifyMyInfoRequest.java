package com.ms8.md.user.feature.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(title = "회원 수정 정보")
public record UserModifyMyInfoRequest(@Size(max = 30)
									  @Schema(description = "닉네임", example = "김짱구")
									  String nickName,
									  @Schema(description = "나이코드", example = "1")
									  Integer ageDetailCodeId,
									  @Schema(description = "성별", example = "F")
									  Character gender,
									  @Schema(description = "산책 선호 시간(초 단위)", example = "3600")
									  Integer time,
									  @Schema(description = "산책 선호 거리(미터 단위)", example = "2500")
									  Integer distance) {

}
