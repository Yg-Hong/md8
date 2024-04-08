package com.ms8.md.sns.feature.photo.dto.response;

import com.ms8.md.sns.feature.photo.entity.Photo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "사진 정보")
public class PhotoResponse {

	@Schema(description = "사진ID")
	private Integer id;
	@Schema(description = "컨텐츠 유형코드", example = "3")
	private Integer contentCode;
	@Schema(description = "컨텐츠ID", example = "2")
	private Long contentId;
	@Schema(description = "파일 URL", example = "S3에 저장된 이미지URL")
	private String fileUrl;

	public PhotoResponse toDto(Photo photo) {
		if (photo != null) {
			this.id = photo.getId();
			this.contentCode = photo.getContentCode();
			this.contentId = photo.getContentId();
			this.fileUrl = photo.getFileName();
		}
		return this;
	}
}
