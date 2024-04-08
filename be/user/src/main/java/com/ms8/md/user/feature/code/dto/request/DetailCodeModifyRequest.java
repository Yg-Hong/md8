package com.ms8.md.user.feature.code.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DetailCodeModifyRequest(@NotBlank @Size(max = 60)
									  @Schema(description = "상세코드명", example = "feed")
									  String name,
									  @NotBlank @Size(max = 255)
									  @Schema(description = "상세코드설명", example = "피드")
									  String description) {
}
