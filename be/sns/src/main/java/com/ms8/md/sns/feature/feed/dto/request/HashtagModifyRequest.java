package com.ms8.md.sns.feature.feed.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(title = "해시태그 수정 정보")
public record HashtagModifyRequest(@NotNull @Schema(description = "해시태그 ID")
								   Integer hashtagId,
								   @Schema(description = "해시태그명")
								   String hashtagName) {
}
