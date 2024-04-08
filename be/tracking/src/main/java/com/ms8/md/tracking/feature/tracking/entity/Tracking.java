package com.ms8.md.tracking.feature.tracking.entity;

import java.util.ArrayList;
import java.util.List;

import com.ms8.md.tracking.feature.bookmark.entity.TrackingBookmark;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ms8.md.tracking.feature.amenities.entity.Amenities;
import com.ms8.md.tracking.feature.tracking.dto.common.Route;
import com.ms8.md.tracking.feature.tracking.dto.request.TrackingModifyRequest;
import com.ms8.md.tracking.global.common.GpsEntity;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Entity
@Table(name = "tracking")
public class Tracking extends GpsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tracking_id")
	@Comment("산책로 ID")
	private Long trackingId;

	@Column(name = "title", length = 60)
	@Comment("제목")
	private String title;

	@Column(name = "content")
	@Comment("설명")
	private String content;

	@Column(name = "route")
	@Comment("경로")
	@ElementCollection
	private List<Route> route;

	@Column(name = "distance")
	@Comment("거리")
	private Integer distance;

	@Column(name = "time")
	@Comment("소요시간")
	private Integer time;

	@Column(name = "kcal")
	@Comment("소모 칼로리")
	private Integer kcal;

	@Column(name = "is_recommend")
	@Comment("추천 여부")
	private Boolean isRecommend;

	@Column(name = "is_valid_to_recommend", columnDefinition = "boolean default true")
	@Comment("추천 가능 여부")
	private Boolean isValidToRecommend;

	private String amenitiesList;

	public void update(TrackingModifyRequest request) {
		this.title = request.getTitle();
		if (request.getContent() != null) {
			this.content = request.getContent();
		}
	}

	public void updateKcal(Integer kcal) {
		this.kcal = kcal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	@OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrackingBookmark> trackingBookmarks = new ArrayList<>();


}
