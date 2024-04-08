package com.ms8.md.sns.feature.feed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms8.md.sns.feature.feed.entity.Feed;
import com.ms8.md.sns.feature.feed.entity.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Integer> {

	@Query("select h from Hashtag h where h.isDeleted = false and h.feed.id = :id")
	List<Hashtag> findByFeedId(@Param("id") Long feedId);

	@Query("select h from Hashtag h "
		+ "where h.isDeleted = false "
		+ "and h.feed = :feed "
		+ "and h.hashtagName = :name")
	Hashtag isExist(@Param("feed") Feed feed, @Param("name") String hashtagName);
}
