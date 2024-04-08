package com.ms8.md.sns.global.S3.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ms8.md.sns.global.common.code.ErrorCode;
import com.ms8.md.sns.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3FileUploadService {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	/**
	 * 파일 업로드
	 * @return S3에 올라간 파일명
	 */
	public String uploadFile(MultipartFile file) {
		if (file.isEmpty()) {
			throw new BusinessExceptionHandler(ErrorCode.FILE_NOT_EXIST);
		}

		String uuidFileName = createUUIDFileName(file);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		try {
			amazonS3Client.putObject(bucket, uuidFileName, file.getInputStream(), metadata);
			log.info("S3에 저장된 파일명 : {}", uuidFileName);
		} catch (IOException e) {
			log.error("Failed to upload file to S3: {}", e.getMessage());
			throw new BusinessExceptionHandler(ErrorCode.IO_ERROR);
		}
		return uuidFileName;
	}

	public String uploadFileAndReturnSavedName(MultipartFile file) {
		String uploadFile = uploadFile(file);
		return getURL(uploadFile);
	}

	public String getURL(String s3Key) {
		if (StringUtils.hasText(s3Key)) {
			return amazonS3Client.getUrl(bucket, s3Key).toString();
		}
		return "";
	}

	public void deleteFile(String s3Key) {
		amazonS3Client.deleteObject(bucket, s3Key);
	}

	private static String createUUIDFileName(MultipartFile file) {
		String uuid = UUID.randomUUID().toString();

		String originalFilename = file.getOriginalFilename();
		if (originalFilename == null) {
			throw new IllegalArgumentException("파일명이 없습니다.");
		}

		String fileName = Paths.get(originalFilename).getFileName().toString();
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex > 0) {
			fileName = fileName.substring(0, dotIndex);
		}

		/*
		 * UUID -> 128bit (16 바이트) 문자열로 표현할경우에는 36자(하이픈 포함)
		 * DB에는 255자 넘지 않아야하기에 255 - 36-1 를 계산한 218자만 저장
		 * */
		if (fileName.length() > 218) {
			fileName = fileName.substring(0, 218);
		}

		return uuid + "_" + fileName;
	}
}
