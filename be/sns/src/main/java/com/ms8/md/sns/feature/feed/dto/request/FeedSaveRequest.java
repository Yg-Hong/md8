package com.ms8.md.sns.feature.feed.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(title = "피드 생성 정보")
public record FeedSaveRequest(@NotNull @Schema(description = "회원ID")
							  Integer userId,
							  @NotNull @Schema(description = "산책로ID")
							  Long trackingId,
							  @Schema(description = "피드 내용")
							  String content,
							  @Schema(description = "해시태그 목록 swagger에서는 ,(콤마) 기준 List에 추가", example = "사랑, 낭만, 벚꽃")
							  List<String> hashtagList) {
}
