package com.ms8.md.user.feature.feignclients.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ms8.md.user.global.common.response.SuccessResponse;

@FeignClient(name = "trackingClient", url = "http://localhost:8082")
public interface TrackingClient {

	@GetMapping(value = "/api/v1/tracking/by-creator/count")
	SuccessResponse<Long> getCountTrackingListByCreator(@RequestParam(name = "userId") Integer userId);

}
