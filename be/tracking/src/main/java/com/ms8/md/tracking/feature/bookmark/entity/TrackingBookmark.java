package com.ms8.md.tracking.feature.bookmark.entity;

import com.ms8.md.tracking.feature.tracking.entity.Tracking;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import com.ms8.md.tracking.feature.tracking.entity.User;
import com.ms8.md.tracking.global.common.DeleteEntity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "tracking_bookmark")
public class TrackingBookmark extends DeleteEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookmark_id")
	@Comment("산책로 ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tracking_id", referencedColumnName = "tracking_id")
	private Tracking tracking;
}
