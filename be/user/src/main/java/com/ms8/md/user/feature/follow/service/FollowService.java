package com.ms8.md.user.feature.follow.service;

import java.util.List;
import java.util.Map;

import com.ms8.md.user.feature.follow.dto.request.FollowBlockRequest;
import com.ms8.md.user.feature.follow.dto.request.FollowSaveRequest;
import com.ms8.md.user.feature.follow.dto.response.FollowBlockResponse;
import com.ms8.md.user.feature.follow.dto.response.FollowNormalResponse;

public interface FollowService {

	void createFollow(FollowSaveRequest request);

	List<FollowNormalResponse> getNormalFollowerList(Integer userId);

	List<FollowNormalResponse> getNormalFollwingList(Integer userId);

	List<FollowBlockResponse> getBlockFollowList(Integer userId);

	Map<String, Long> getMyFollowCount(Integer userId);

	void blockFollower(FollowBlockRequest request);
	void blockFollowing(FollowBlockRequest request);

	void unBlockFollower(FollowBlockRequest request);
	void unBlockFollowing(FollowBlockRequest request);

	void removeFollower(FollowBlockRequest request);
	void removeFollowing(FollowBlockRequest request);
}
