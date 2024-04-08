package com.ms8.md.user.feature.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserMyPageInfoResponse {

	private UserResponse userResponse;

	private Integer trackingCount;
	private Integer followerCount;
	private Integer followingCount;

	@Builder
	private UserMyPageInfoResponse(UserResponse userResponse, Integer trackingCount, Integer followerCount, Integer followingCount) {
		this.userResponse = userResponse;
		this.trackingCount = trackingCount;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
	}
}
