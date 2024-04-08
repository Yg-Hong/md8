package com.ms8.md.sns.feature.feed.repository.custom;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.ms8.md.sns.feature.feed.entity.Feed;

public interface FeedCustomRepository {

	List<Feed> selectFeedByUserId();

	Slice<Feed> selectFeedListByLastId(List<Integer> userIdList, Pageable pageable, Long lastFeedId);
	Slice<Feed> selectMyFeedListByLastId(Integer userId, Pageable pageable, Long lastFeedId);

	Slice<Feed> selectFeedSearchHashtag(String hashtag, Pageable pageable, Long lastFeedId);
}
