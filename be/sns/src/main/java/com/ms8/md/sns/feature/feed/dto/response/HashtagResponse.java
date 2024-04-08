package com.ms8.md.sns.feature.feed.dto.response;

import com.ms8.md.sns.feature.feed.entity.Hashtag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "피드 해시태그")
public class HashtagResponse {

	@Schema(description = "해시태그ID", example = "1")
	private Integer hashtagId;
	@Schema(description = "피드ID", example = "3")
	private Long feedId;
	@Schema(description = "해시태그명", example = "낭만")
	private String hashtagName;

	public HashtagResponse toDto(Hashtag hashtag) {
		if (hashtag != null) {
			this.hashtagId = hashtag.getId();
			this.feedId = hashtag.getFeed().getId();
			this.hashtagName = hashtag.getHashtagName();
		}

		return this;
	}
}
