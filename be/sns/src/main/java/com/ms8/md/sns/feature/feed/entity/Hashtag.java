package com.ms8.md.sns.feature.feed.entity;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import com.ms8.md.sns.global.common.entity.DeleteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "hashtag")
public class Hashtag extends DeleteEntity {

	@Id
	@Column(name = "hashtag_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("해시태그ID")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id")
	@Comment("피드ID")
	private Feed feed;

	@Column(length = 60)
	@Comment("해시태그명")
	private String hashtagName;

	@Builder
	private Hashtag(Feed feed, String hashtagName) {
		this.feed = feed;
		this.hashtagName = hashtagName;
	}

	public void updateHashtag(String hashtagName) {
		this.hashtagName = hashtagName;
	}
}
