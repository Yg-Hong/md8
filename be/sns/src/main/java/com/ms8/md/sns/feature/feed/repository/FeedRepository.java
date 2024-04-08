package com.ms8.md.sns.feature.feed.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms8.md.sns.feature.feed.entity.Feed;
import com.ms8.md.sns.feature.feed.repository.custom.FeedCustomRepository;

public interface FeedRepository extends JpaRepository<Feed, Long>, FeedCustomRepository {

}
