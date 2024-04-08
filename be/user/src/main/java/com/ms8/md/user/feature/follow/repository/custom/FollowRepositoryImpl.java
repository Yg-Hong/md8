package com.ms8.md.user.feature.follow.repository.custom;

import static com.ms8.md.user.feature.follow.entity.QFollow.*;
import static com.ms8.md.user.feature.user.entity.QUser.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ms8.md.user.feature.follow.entity.Follow;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Follow> selectNormalFollower(Integer userId) {
		return jpaQueryFactory.selectFrom(follow)
			.join(follow.followerId, user)
			.where(isUserWithdrawal(false), isDeleted(false), isBlocked(false), eqFollowingId(userId))
			.fetch();
	}

	@Override
	public List<Follow> selectNormalFollowing(Integer userId) {
		return jpaQueryFactory.selectFrom(follow)
			.join(follow.followingId, user)
			.where(isUserWithdrawal(false), isDeleted(false), isBlocked(false), eqFollowerId(userId))
			.fetch();
	}

	@Override
	public Map<String, Long> selectMyFollowCount(Integer userId) {
		// 내 팔로워 수
		Long myFollower = jpaQueryFactory.select(follow.count())
			.from(follow)
			.join(follow.followerId, user)
			.where(isUserWithdrawal(false), isDeleted(false), isBlocked(false), eqFollowingId(userId))
			.fetchOne();

		// 내 팔로잉 수
		Long myFollowing = jpaQueryFactory.select(follow.count())
			.from(follow)
			.join(follow.followingId, user)
			.where(isUserWithdrawal(false), isDeleted(false), isBlocked(false), eqFollowerId(userId))
			.fetchOne();

		Map<String, Long> followCountMap = new HashMap<>();
		followCountMap.put("followerCount", myFollower != null ? myFollower.intValue() : 0L);
		followCountMap.put("followingCount", myFollowing != null ? myFollowing.intValue() : 0L);

		return followCountMap;
	}

	@Override
	public Long selectFollowCount(Integer follower, Integer following) {
		return jpaQueryFactory.select(follow.count())
			.from(follow)
			.join(follow.followingId, user)
			.where(
				isUserWithdrawal(true).or(isDeleted(false)).or
					(isBlocked(true)),
				eqFollowerId(follower),
				eqFollowingId(following))
			.fetchOne();
	}

	@Override
	public List<Follow> selectBlockFollow(Integer userId) {
		return jpaQueryFactory.selectFrom(follow)
			.where(isBlocked(true),
				eqFollowingId(userId).or(eqFollowerId(userId))
			).fetch();
	}

	@Override
	public Optional<Follow> selectBlockTargetFollower(Integer userId, Integer followerId, boolean isBlocked) {
		return Optional.ofNullable(jpaQueryFactory.selectFrom(follow)
			.join(follow.followerId, user)
			.where(isUserWithdrawal(false),
				isBlocked(isBlocked),
				isDeleted(false),
				eqFollowingId(userId),
				eqFollowerId(followerId))
			.fetchOne());
	}

	@Override
	public Optional<Follow> selectBlockTargetFollowing(Integer userId, Integer followingId, boolean isBlocked) {
		return Optional.ofNullable(jpaQueryFactory.selectFrom(follow)
			.join(follow.followingId, user)
			.where(isUserWithdrawal(false),
				isBlocked(isBlocked),
				isDeleted(false),
				eqFollowingId(followingId),
				eqFollowerId(userId)
			).fetchOne());
	}

	private static BooleanExpression isUserWithdrawal(boolean isWithdrawal) {
		return user.isWithdrawal.eq(isWithdrawal);
	}

	private static BooleanExpression eqFollowerId(Integer userId) {
		return follow.followerId.id.eq(userId);
	}

	private static BooleanExpression eqFollowingId(Integer userId) {
		return follow.followingId.id.eq(userId);
	}

	private static BooleanExpression isDeleted(boolean deleted) {
		return follow.isDeleted.eq(deleted);
	}

	private static BooleanExpression isBlocked(Boolean block) {
		return follow.isBlocked.eq(block);
	}
}
