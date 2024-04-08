package com.ms8.md.user.feature.follow.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms8.md.user.feature.follow.dto.request.FollowBlockRequest;
import com.ms8.md.user.feature.follow.dto.request.FollowSaveRequest;
import com.ms8.md.user.feature.follow.dto.response.FollowBlockResponse;
import com.ms8.md.user.feature.follow.dto.response.FollowNormalResponse;
import com.ms8.md.user.feature.follow.entity.Follow;
import com.ms8.md.user.feature.follow.repository.FollowRepository;
import com.ms8.md.user.feature.user.entity.User;
import com.ms8.md.user.feature.user.service.UserService;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

	private final FollowRepository followRepository;

	private final UserService userService;

	@Override
	public void createFollow(FollowSaveRequest request) {
		User follower = userService.getuserEntity(request.followerId());
		User following = userService.getuserEntity(request.followingId());

		/* 1. 이미 팔로우 한 관계거나
		 *  2. 팔로잉 대상이 나를 차단한 경우에는 팔로우 X */
		Long followCount = followRepository.selectFollowCount(follower.getId(), following.getId());
		if (followCount > 0) {
			throw new BusinessExceptionHandler(ErrorCode.CAN_NOT_FOLLOW);
		}

		Follow follow = Follow.builder()
			.followerId(follower)
			.followingId(following)
			.build();

		followRepository.save(follow);
	}

	@Override
	public List<FollowNormalResponse> getNormalFollowerList(Integer userId) {
		return followRepository.selectNormalFollower(userId).stream()
			.map(follow -> new FollowNormalResponse().toDto(follow))
			.toList();
	}

	@Override
	public List<FollowNormalResponse> getNormalFollwingList(Integer userId) {
		return followRepository.selectNormalFollowing(userId).stream()
			.map(follow -> new FollowNormalResponse().toDto(follow))
			.toList();
	}

	@Override
	public List<FollowBlockResponse> getBlockFollowList(Integer userId) {
		return followRepository.selectBlockFollow(userId).stream()
			.map(follow -> new FollowBlockResponse().toDto(follow))
			.toList();
	}

	@Override
	public Map<String, Long> getMyFollowCount(Integer userId) {
		return followRepository.selectMyFollowCount(userId);
	}

	@Override
	@Transactional
	public void blockFollower(FollowBlockRequest request) {
		followRepository.selectBlockTargetFollower(request.userId(), request.targetUserId(), false)
			.ifPresentOrElse(
				Follow::updateBlock,
				() -> {
					throw new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST);
				});
	}

	@Override
	@Transactional
	public void blockFollowing(FollowBlockRequest request) {
		followRepository.selectBlockTargetFollowing(request.userId(), request.targetUserId(), false)
			.ifPresentOrElse(
				Follow::updateBlock,
				() -> {
					throw new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST);
				});
	}

	@Override
	@Transactional
	public void unBlockFollower(FollowBlockRequest request) {
		followRepository.selectBlockTargetFollower(request.userId(), request.targetUserId(), true)
			.ifPresentOrElse(
				Follow::updateUnBlock,
				() -> {
					throw new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST);
				});
	}

	@Override
	@Transactional
	public void unBlockFollowing(FollowBlockRequest request) {
		followRepository.selectBlockTargetFollowing(request.userId(), request.targetUserId(), true)
			.ifPresentOrElse(
				Follow::updateUnBlock,
				() -> {
					throw new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST);
				});
	}

	@Override
	@Transactional
	public void removeFollower(FollowBlockRequest request) {
		followRepository.selectBlockTargetFollower(request.userId(), request.targetUserId(), false)
			.ifPresentOrElse(
				Follow::deleteData,
				() -> {
					throw new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST);
				});
	}

	@Override
	@Transactional
	public void removeFollowing(FollowBlockRequest request) {
		followRepository.selectBlockTargetFollowing(request.userId(), request.targetUserId(), false)
			.ifPresentOrElse(
				Follow::deleteData,
				() -> {
					throw new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST);
				});
	}

}
