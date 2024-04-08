package com.ms8.md.sns.feature.feignclient.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {

	private Integer id;
	private String email;
	private String nickName;
	private String profile;

	@Builder
	public UserResponse(Integer id, String email, String nickName, String profile) {
		this.id = id;
		this.email = email;
		this.nickName = nickName;
		this.profile = profile;
	}
}
