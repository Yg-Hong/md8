package com.ms8.md.sns.feature.feed.dto.request;

import com.ms8.md.sns.feature.feed.entity.Feed;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(title = "해시태그 저장 정보")
public record HashtagSaveRequest(@NotNull
								 @Schema(description = "피드 정보")
								 Feed feed,
								 @NotBlank
								 @Size(max = 60)
								 @Schema(description = "해시태그명")
								 String hashtagName) {
}
