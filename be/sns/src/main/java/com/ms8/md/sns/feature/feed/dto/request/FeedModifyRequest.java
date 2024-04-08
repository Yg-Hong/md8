package com.ms8.md.sns.feature.feed.dto.request;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "피드 수정 정보")
public record FeedModifyRequest(@Schema(description = "피드 내용")
								String content,
								@Schema(description = "해시태그 목록 swagger에서는 ,(콤마) 기준 List에 추가", example = "사랑, 낭만, 벚꽃")
								List<HashtagModifyRequest> hashtagList) {

}