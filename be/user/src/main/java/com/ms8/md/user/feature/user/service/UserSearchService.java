package com.ms8.md.user.feature.user.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ms8.md.user.feature.feignclients.service.TrackingClient;
import com.ms8.md.user.feature.follow.dto.response.FollowBlockResponse;
import com.ms8.md.user.feature.follow.service.FollowService;
import com.ms8.md.user.feature.user.dto.response.UserResponse;
import com.ms8.md.user.feature.user.dto.response.UserSearchResponse;
import com.ms8.md.user.feature.user.entity.User;
import com.ms8.md.user.feature.user.repository.UserRepository;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.common.response.SuccessResponse;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserSearchService {

	private final UserRepository userRepository;
	private final FollowService followService;

	private final TrackingClient trackingClient;

	public List<UserSearchResponse> getUserByNickName(Integer userId, String nickName) {
		// #1. 닉네임 like 검색 user 전체조회 (본인, 탈퇴회원 제외)
		List<User> userList = userRepository.findByNickName(userId, nickName);

		// #2. 서로 차단안한 유저들만 필터링
		List<FollowBlockResponse> blockFollowList = followService.getBlockFollowList(userId);
		List<Integer> blockedUserIdList = blockFollowList.stream()
			.map(block -> Objects.equals(block.getFollowingId(), userId)
				? block.getFollowerId()
				: block.getFollowingId())
			.distinct()
			.toList();

		return userList.stream()
			.filter(user -> !blockedUserIdList.contains(user.getId()))
			.map(user -> new UserSearchResponse().toDto(user))
			.toList();
	}

	public UserResponse getUser(Integer userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));

		// 내 팔로워, 팔로잉 count
		Map<String, Long> myMap = followService.getMyFollowCount(userId);
		Long followerCount = myMap.get("followerCount");
		Long followingCount = myMap.get("followingCount");

		// 산책로 count
		SuccessResponse<Long> trackingResponse = trackingClient.getCountTrackingListByCreator(userId);
		Long trackingCount = trackingResponse.getData();

		return new UserResponse().toDtoWithCounts(user, trackingCount, followerCount, followingCount);
	}
}
