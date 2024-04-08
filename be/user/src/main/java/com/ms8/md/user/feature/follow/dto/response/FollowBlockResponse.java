package com.ms8.md.user.feature.follow.dto.response;

import com.ms8.md.user.feature.follow.entity.Follow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "차단 목록")
public class FollowBlockResponse {

	@Schema(description = "팔로우ID")
	private Integer followId;
	@Schema(description = "차단여부")
	private Boolean isBlocked;

	@Schema(description = "팔로워ID", example = "1")
	private Integer followerId;
	@Schema(description = "팔로워 닉네임", example = "도구리")
	private String followerName;
	@Schema(description = "팔로워 프로필")
	private String followerProfile;

	@Schema(description = "팔로잉ID", example = "5")
	private Integer followingId;
	@Schema(description = "팔로잉 닉네임", example = "김또치")
	private String followingName;
	@Schema(description = "팔로잉 프로필")
	private String followingProfile;

	public FollowBlockResponse toDto(Follow follow) {
		if (follow != null) {
			this.followId = follow.getId();
			this.isBlocked = follow.getIsBlocked();
			this.followerId = follow.getFollowerId().getId();
			this.followerName = follow.getFollowerId().getNickName();
			this.followerProfile = follow.getFollowerId().getProfile();
			this.followingId = follow.getFollowingId().getId();
			this.followingName = follow.getFollowingId().getNickName();
			this.followingProfile = follow.getFollowingId().getProfile();
		}

		return this;
	}
}
