package com.ms8.md.sns.feature.feed.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ms8.md.sns.feature.feed.entity.Feed;
import com.ms8.md.sns.feature.feignclient.dto.UserResponse;
import com.ms8.md.sns.feature.photo.dto.response.PhotoResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "피드 정보")
public class FeedDetailResponse {
	// [피드 정보]
	@Schema(description = "피드ID", example = "1")
	private Long feedId;
	@Schema(description = "피드 내용", example = "좋은 아침입니다.")
	private String content;
	@Schema(description = "피드 생성일시", example = "2024-03-22 10:08:28.326")
	private LocalDateTime createdAt;
	@Schema(description = "산책로 ID", example = "10")
	private Long trackingId;

	// [유저 정보]
	@Schema(description = "피드 작성자ID", example = "1")
	private Integer userId;
	@Schema(description = "피드 작성자 닉네임", example = "도구리")
	private String nickName;
	@Schema(description = "피드 작성자 프로필")
	private String profile;

	// [피드 사진 List]
	@Schema(description = "피드 사진 목록")
	private List<PhotoResponse> photos;

	// [해시태그 List]
	@Schema(description = "피드 해시태그 목록")
	private List<HashtagResponse> hashtags;

	public FeedDetailResponse toDto(UserResponse userResponse,List<PhotoResponse> photos, Feed feed, List<HashtagResponse> hashtags) {
		this.feedId = feed.getId();
		this.content = feed.getContent();
		this.createdAt = feed.getCreateAt();
		this.trackingId = feed.getTrackingId();

		this.userId = userResponse.getId();
		this.nickName = userResponse.getNickName();
		this.profile = userResponse.getProfile();

		this.photos = photos;
		this.hashtags = hashtags;

		return this;
	}
}
