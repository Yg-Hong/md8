package com.ms8.md.user.feature.feignclients.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.user.global.common.response.SuccessResponse;
import com.ms8.md.user.global.config.FeignClientConfig;

@FeignClient(name = "s3Client", url = "http://localhost:8081", configuration = FeignClientConfig.class)
public interface S3Client {

	@PostMapping(value = "/api/s3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	SuccessResponse<String> createS3File(@ModelAttribute MultipartFile file);

}