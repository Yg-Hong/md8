package com.ms8.md.sns.feature.feed.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "내 피드 썸네일")
public class MyFeedThumbnailResponse {

	@Schema(description = "피드ID", example = "1")
	private Long feedId;
	@Schema(description = "피드 썸네일")
	private String photoURL;

	public MyFeedThumbnailResponse toDto(Long feedId, String photoURL) {
		this.feedId = feedId;
		this.photoURL = photoURL;

		return this;
	}
}
