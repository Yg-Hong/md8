package com.ms8.md.sns.feature.feed.repository.custom;

import static com.ms8.md.sns.feature.feed.entity.QFeed.*;
import static com.ms8.md.sns.feature.feed.entity.QHashtag.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.StringUtils;

import com.ms8.md.sns.feature.feed.entity.Feed;
import com.ms8.md.sns.feature.feed.entity.QHashtag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<Feed> selectFeedByUserId() {
		return null;
	}

	@Override
	public Slice<Feed> selectFeedListByLastId(List<Integer> userIdList, Pageable pageable, Long lastFeedId) {
		List<Feed> content = jpaQueryFactory.selectFrom(feed)
			.where(
				feed.userId.in(userIdList),
				isDeleted(false),
				ltFeedId(lastFeedId)
			)
			.orderBy(feed.id.desc())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}
		return new SliceImpl<>(content, pageable, hasNext);
	}

	@Override
	public Slice<Feed> selectMyFeedListByLastId(Integer userId, Pageable pageable, Long lastFeedId) {
		List<Feed> content = jpaQueryFactory.selectFrom(feed)
			.where(
				feed.userId.eq(userId),
				isDeleted(false),
				ltFeedId(lastFeedId)
			)
			.orderBy(feed.id.desc())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}
		return new SliceImpl<>(content, pageable, hasNext);
	}

	@Override
	public Slice<Feed> selectFeedSearchHashtag(String hashtagParam, Pageable pageable, Long lastFeedId) {
		List<Feed> content = jpaQueryFactory.selectFrom(feed)
			.innerJoin(hashtag)
			.on(feed.id.eq(hashtag.feed.id))
			.where(
				eqHashtagname(hashtagParam),
				isDeleted(false),
				ltFeedId(lastFeedId)
			)
			.orderBy(feed.id.desc())
			.limit(pageable.getPageSize() + 1)
			.fetch();

		boolean hasNext = false;
		if (content.size() > pageable.getPageSize()) {
			content.remove(pageable.getPageSize());
			hasNext = true;
		}
		return new SliceImpl<>(content, pageable, hasNext);
	}

	private static BooleanExpression eqHashtagname(String hashtagName) {
		if (!StringUtils.hasText(hashtagName)) {
			return null;
		}
		return hashtag.isDeleted.eq(false)
			.and(hashtag.hashtagName.contains(hashtagName));
	}

	private static BooleanExpression ltFeedId(Long lastFeedId) {
		if (lastFeedId == null) {
			return null;
		}
		return feed.id.lt(lastFeedId);
	}

	private static BooleanExpression isDeleted(boolean hasDeletedFlag) {
		return feed.isDeleted.eq(hasDeletedFlag);
	}
}
