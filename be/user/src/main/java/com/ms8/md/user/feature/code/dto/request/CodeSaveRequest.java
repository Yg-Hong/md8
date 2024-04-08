package com.ms8.md.user.feature.code.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CodeSaveRequest(@NotBlank @Size(max = 60)
							  @Schema(description = "코드명", example = "Content")
							  String name,
							  @NotBlank @Size(max = 255)
							  @Schema(description = "코드설명", example = "컨텐츠 종류")
							  String description) {
}
