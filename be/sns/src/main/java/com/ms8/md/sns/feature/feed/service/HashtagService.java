package com.ms8.md.sns.feature.feed.service;

import java.util.List;

import com.ms8.md.sns.feature.feed.dto.request.HashtagModifyRequest;
import com.ms8.md.sns.feature.feed.dto.request.HashtagSaveRequest;
import com.ms8.md.sns.feature.feed.dto.response.HashtagResponse;

public interface HashtagService {

	List<HashtagResponse> getHashtags(Long feedId);

	void createHashtag(HashtagSaveRequest request);

	void modifyHashtag(HashtagModifyRequest request);
	void removeHashtag(Integer hashtagId);
}
