package com.ms8.md.sns.global.S3.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.sns.global.S3.service.S3FileUploadService;
import com.ms8.md.sns.global.common.code.SuccessCode;
import com.ms8.md.sns.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/s3")
@Tag(name = "S3", description = "S3 관리 API")
public class S3Controller {

	private final S3FileUploadService s3FileUploadService;

	@PostMapping
	public SuccessResponse<String> createS3File(@ModelAttribute MultipartFile file) {
		String savedPhotoURL = s3FileUploadService.uploadFileAndReturnSavedName(file);

		return SuccessResponse.<String>builder()
			.data(savedPhotoURL)
			.status(SuccessCode.SELECT_SUCCESS)
			.message(SuccessCode.SELECT_SUCCESS.getMessage())
			.build();
	}
}
