package com.ms8.md.sns.feature.feed.entity;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import com.ms8.md.sns.global.common.entity.DeleteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@DynamicInsert
@Table(name = "feed")
public class Feed extends DeleteEntity {
	@Id
	@Column(name = "feed_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("피드ID")
	private Long id;

	@Comment("회원ID")
	private Integer userId;

	@Comment("산책로ID")
	private Long trackingId;

	@Comment("내용")
	@Column(columnDefinition = "TEXT")
	private String content;

	@Comment("좋아요")
	@Column(name = "like_count")
	private Integer likeCount;

	@Builder
	private Feed(Integer memberId, Long trackingId, String content) {
		this.userId = memberId;
		this.trackingId = trackingId;
		this.content = content;
		likeCount = 0;
	}

	public void updateFeed(String content) {
		this.content = content;
	}
}
