package com.ms8.md.user.feature.follow.dto.response;

import com.ms8.md.user.feature.follow.entity.Follow;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "팔로우 목록")
public class FollowNormalResponse {
	@Schema(description = "팔로우ID", example = "1")
	private Integer followId;

	@Schema(description = "팔로우 한 회원ID", example = "5")
	private Integer followerId;
	@Schema(description = "팔로우 한 회원 닉네임", example = "코코")
	private String followerName;
	@Schema(description = "팔로우 한 회원 프로필", example = "http:...")
	private String followerProfile;

	@Schema(description = "팔로잉 한 회원ID", example = "3")
	private Integer followingId;
	@Schema(description = "팔로잉 한 회원 닉네임", example = "소나무")
	private String followingName;
	@Schema(description = "팔로잉 한 회원 프로필", example = "http:...")
	private String followingProfile;

	public FollowNormalResponse toDto(Follow follow) {
		if (follow != null) {
			this.followId = follow.getId();
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
