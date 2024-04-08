package com.ms8.md.sns.feature.feignclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "팔로잉 목록")
public class FollowResponse {

	@Schema(description = "팔로우ID", example = "1")
	private Integer followId;
	@Schema(description = "팔로잉 한 회원ID", example = "3")
	private Integer followingId;
	@Schema(description = "팔로잉 한 회원 닉네임", example = "소나무")
	private String followingName;
	@Schema(description = "팔로잉 한 회원 프로필", example = "http:...")
	private String followingProfile;
}
