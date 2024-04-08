package com.ms8.md.user.feature.follow.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import com.ms8.md.user.feature.user.entity.User;
import com.ms8.md.user.global.common.entity.DeleteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "follow")
public class Follow extends DeleteEntity {

	@Id
	@Column(name = "follow_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("팔로우ID")
	private Integer id;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "follower_id")
	@Comment("팔로워ID")
	private User followerId;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "following_id")
	@Comment("팔로잉ID")
	private User followingId;

	@Comment("차단여부")
	@ColumnDefault("false")
	private Boolean isBlocked;

	@Builder
	private Follow(User followerId, User followingId) {
		this.followerId = followerId;
		this.followingId = followingId;
	}

	public void updateBlock() {
		this.isBlocked = true;
	}

	public void updateUnBlock() {
		this.isBlocked = false;
	}
}
