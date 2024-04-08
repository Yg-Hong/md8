package com.ms8.md.recommendation.feature.reco.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.Comment;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "amenities")
public class Amenities{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "amenities_id")
	@Comment("편의시설 ID")
	private Long amenitiesId;

	@Column(name = "amenities_code_id")
	@Comment("편의시설 코드 ID")
	private Integer amenitiesCodeId;

	@Comment("위도")
	@DecimalMin(value = "-90.0", message = "위도는 -90에서 90 사이의 값이어야 합니다.")
	@DecimalMax(value = "90.0", message = "위도는 -90에서 90 사이의 값이어야 합니다.")
	@Schema(description = "위도", example = "15.087269")
	private Double latitude;

	@Comment("경도")
	@DecimalMin(value = "-180.0", message = "경도는 -180에서 180 사이의 값이어야 합니다.")
	@DecimalMax(value = "180.0", message = "경도는 -180에서 180 사이의 값이어야 합니다.")
	@Schema(description = "경도", example = "37.502669")
	private Double longitude;

	public void updateGps(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
