package com.ms8.md.user.feature.user.dto.response;

import com.ms8.md.user.feature.code.dto.response.DetailCodeResponse;
import com.ms8.md.user.feature.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "회원 정보")
public class UserResponse {

	@Schema(description = "회원ID", example = "1")
	private Integer id;
	@Schema(description = "회원 이메일", example = "ssafyJJ@naver.com")
	private String email;
	@Schema(description = "나이코드", example = "3(상세코드 ID)")
	private DetailCodeResponse ageCode;
	@Schema(description = "성별", example = "M")
	private Character gender;
	@Schema(description = "회원 닉네임", example = "김또치")
	private String nickName;

	@Schema(description = "레벨", example = "3")
	private Integer level;
	@Schema(description = "총 산책거리(m 단위)", example = "3000")
	private Integer totalDistance;
	@Schema(description = "회원 프로필")
	private String profile;
	@Schema(description = "산책 선호 시간(분 단위)", example = "30")
	private Integer time;
	@Schema(description = "산책 선호 거리(m 단위)", example = "2000")
	private Integer distance;

	@Schema(description = "내 산책로 수", example = "12")
	private Long trackingCount;
	@Schema(description = "내 팔로워 수", example = "5")
	private Long followerCount;
	@Schema(description = "내 팔로잉 수", example = "10")
	private Long followingCount;

	public UserResponse toDtoWithCounts(User user) {
		assignUserDetails(user);
		return this;
	}

	private void assignUserDetails(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.nickName = user.getNickName();
		this.ageCode = DetailCodeResponse.toDto(user.getAgeCode());
		this.gender = user.getGender();
		this.level = user.getLevel();
		this.totalDistance = user.getTotalDistance();
		this.profile = user.getProfile();
		this.time = user.getTime();
		this.distance = user.getDistance();
	}

	public UserResponse toDtoWithCounts(User user, Long trackingCount, Long followerCount, Long followingCount) {
		assignUserDetails(user);

		// 추가, 내 산책로, 팔로잉, 팔로워 수
		this.trackingCount = trackingCount;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		return this;
	}
}
