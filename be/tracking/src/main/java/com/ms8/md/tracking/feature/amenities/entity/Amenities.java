package com.ms8.md.tracking.feature.amenities.entity;

import org.hibernate.annotations.Comment;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ms8.md.tracking.feature.tracking.entity.Tracking;
import com.ms8.md.tracking.global.common.GpsEntity;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "amenities")
public class Amenities extends GpsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "amenities_id")
	@Comment("편의시설 ID")
	private Long amenitiesId;

	@Column(name = "amenities_code_id")
	@Comment("편의시설 코드 ID")
	private Integer amenitiesCodeId;
}
