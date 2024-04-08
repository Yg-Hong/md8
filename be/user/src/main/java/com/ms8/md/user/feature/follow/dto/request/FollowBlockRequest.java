package com.ms8.md.user.feature.follow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(title = "팔로우 차단/삭제 정보")
public record FollowBlockRequest(@NotNull
								 @Schema(description = "내 아이디")
								 Integer userId,
								 @NotNull
								 @Schema(description = "대상 아이디")
								 Integer targetUserId) {
}
