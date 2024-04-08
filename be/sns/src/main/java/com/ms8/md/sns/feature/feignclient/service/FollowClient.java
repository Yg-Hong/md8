package com.ms8.md.sns.feature.feignclient.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms8.md.sns.feature.feignclient.dto.FollowResponse;
import com.ms8.md.sns.global.common.response.SuccessResponse;

@FeignClient(name = "followClient", url = "http://localhost:8080")
public interface FollowClient {

	@GetMapping(value = "/api/follows/following/{userId}")
	SuccessResponse<List<FollowResponse>> getFollwingList(@PathVariable("userId") Integer userId);
}
