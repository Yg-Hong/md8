package com.ms8.md.user.feature.code.dto.response;

import com.ms8.md.user.feature.code.entity.Code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "코드 반환 정보")
public class CodeResponse {

	@Schema(description = "코드ID", example = "1")
	private Integer codeId;
	@Schema(description = "코드명", example = "Content")
	private String name;
	@Schema(description = "코드설명", example = "컨텐츠 유형코드")
	private String description;

	public static CodeResponse toDto(Code code) {
		CodeResponse response = new CodeResponse();

		if (code != null) {
			response.setCodeId(code.getCodeId());
			response.setName(code.getName());
			response.setDescription(code.getDescription());
		}

		return response;
	}
}