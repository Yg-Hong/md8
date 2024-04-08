package com.ms8.md.user.feature.follow.repository.custom;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ms8.md.user.feature.follow.entity.Follow;

public interface FollowCustomRepository {

	List<Follow> selectNormalFollower(Integer userId);

	List<Follow> selectNormalFollowing(Integer userId);

	Map<String, Long> selectMyFollowCount(Integer userId);

	Long selectFollowCount(Integer follower, Integer following);

	List<Follow> selectBlockFollow(Integer userId);

	Optional<Follow> selectBlockTargetFollower(Integer userId, Integer followerId, boolean isBlocked);

	Optional<Follow> selectBlockTargetFollowing(Integer userId, Integer followingId, boolean isBlocked);
}
