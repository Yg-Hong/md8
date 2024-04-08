package com.ms8.md.user.feature.follow.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(title = "팔로우 등록")
public record FollowSaveRequest(@NotNull
								@Schema(description = "팔로우 신청하는 회원ID", example = "5")
								Integer followerId,
								@NotNull
								@Schema(description = "팔로우 대상 회원ID", example = "10")
								Integer followingId) {
}
