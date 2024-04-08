package com.ms8.md.user.feature.user.dto.response;

import com.ms8.md.user.feature.user.entity.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "회원 검색 정보")
public class UserSearchResponse {

	@Schema(description = "회원ID", example = "1")
	private Integer userId;
	@Schema(description = "회원 닉네임", example = "김또치")
	private String nickName;
	@Schema(description = "회원 레벨", example = "3")
	private Integer level;
	@Schema(description = "회원 프로필")
	private String profile;

	public UserSearchResponse toDto(User user) {
		if (user != null) {
			this.userId = user.getId();
			this.nickName = user.getNickName();
			this.level = user.getLevel();
			this.profile = user.getProfile();
		}
		return this;
	}
}
