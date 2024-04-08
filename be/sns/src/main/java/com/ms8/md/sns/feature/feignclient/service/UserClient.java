package com.ms8.md.sns.feature.feignclient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms8.md.sns.feature.feignclient.dto.UserResponse;
import com.ms8.md.sns.global.common.response.SuccessResponse;

@FeignClient(name = "userClient", url = "http://localhost:8080")
public interface UserClient {

	@GetMapping(value = "/api/users/{userId}")
 	SuccessResponse<UserResponse> getUser(@PathVariable("userId") Integer userId);
}
