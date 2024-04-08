package com.ms8.md.tracking.feature.tracking.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TrackingModifyRequest {

	@NotBlank
	@Size(max = 60)
	@Schema(description = "제목", example = "Title")
	private String title;

	@Schema(description = "내용", example = "Content")
	private String content;
}
