package com.ms8.md.sns.feature.feed.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "내 피드 목록")
public class MyFeedSliceResponse {

	@Schema(description = "내 피드 썸네일")
	private List<MyFeedThumbnailResponse> myFeedThumbnailResponseList;
	@Schema(description = "마지막으로 조회된 피드ID", example = "5")
	private Long lastFeedId;
	@Schema(description = "다음 피드 데이터 존재여부", example = "true")
	private Boolean hasNext;

	public MyFeedSliceResponse toDto(List<MyFeedThumbnailResponse> myFeedThumbnailResponseList, Long lastFeedId, Boolean hasNext) {
		this.myFeedThumbnailResponseList = myFeedThumbnailResponseList;
		this.lastFeedId = lastFeedId;
		this.hasNext = hasNext;
		return this;
	}
}
