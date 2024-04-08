package com.ms8.md.sns.feature.feed.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "팔로잉 하고 있는 대상 피드 목록")
public class FeedSliceResponse {

	@Schema(description = "피드 정보")
	private List<FeedDetailResponse> feedDetailResponse;
	@Schema(description = "마지막으로 조회된 피드ID", example = "5")
	private Long lastFeedId;
	@Schema(description = "다음 피드 데이터 존재여부", example = "true")
	private Boolean hasNext;

	public FeedSliceResponse toDto(List<FeedDetailResponse> feedDetailResponse, Long lastFeedId, Boolean hasNext) {
		this.feedDetailResponse = feedDetailResponse;
		this.lastFeedId = lastFeedId;
		this.hasNext = hasNext;
		return this;
	}
}
