package com.ms8.md.tracking.global.common;

import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class GpsEntity extends DeleteEntity {

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
