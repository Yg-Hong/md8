package com.ms8.md.tracking.feature.tracking.dto.common;

import com.ms8.md.tracking.global.common.GpsEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Embeddable
public class Route extends GpsEntity {

	@NotBlank
	@Schema(description = "경로순서", example = "1")
	private Integer orderNum;

}
