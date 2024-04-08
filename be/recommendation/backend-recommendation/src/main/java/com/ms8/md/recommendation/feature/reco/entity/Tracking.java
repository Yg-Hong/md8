package com.ms8.md.recommendation.feature.reco.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tracking")
public class Tracking {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "tracking_id", nullable = false)
	private Long id;
	private String title;
	private Integer time;
	private Integer distance;
	private Integer kcal;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private String amenitiesList;
	@OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TrackingBookmark> trackingBookmarks = new ArrayList<>();
}
