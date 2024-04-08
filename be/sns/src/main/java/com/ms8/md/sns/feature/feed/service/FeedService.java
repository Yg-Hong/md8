package com.ms8.md.sns.feature.feed.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.sns.feature.feed.dto.request.FeedModifyRequest;
import com.ms8.md.sns.feature.feed.dto.request.FeedSaveRequest;
import com.ms8.md.sns.feature.feed.dto.response.FeedDetailResponse;
import com.ms8.md.sns.feature.feed.dto.response.FeedSliceResponse;
import com.ms8.md.sns.feature.feed.dto.response.MyFeedSliceResponse;

public interface FeedService {

	void createFeed(FeedSaveRequest request, List<MultipartFile> fileList);

	FeedSliceResponse getFeedList(Integer userId, Long lastFeedId);
	MyFeedSliceResponse getMyFeedList(Integer userId, Long lastFeedId);
	FeedDetailResponse getFeedDetail(Long feedId);

	void modifyFeed(Long feedId, FeedModifyRequest request, List<MultipartFile> fileList);

	void removeFeed(Long feedId);

	MyFeedSliceResponse searchFeedByHashtag(String hashtag, Long lastFeedId);
}
