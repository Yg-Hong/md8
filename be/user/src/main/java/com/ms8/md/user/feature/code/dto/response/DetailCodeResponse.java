package com.ms8.md.user.feature.code.dto.response;

import com.ms8.md.user.feature.code.entity.DetailCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "상세코드 조회 response")
public class DetailCodeResponse {

	// 코드
	@Schema(description = "코드ID", example = "5")
	private Integer codeId;
	@Schema(description = "코드명", example = "Content")
	private String name;

	// 상세코드
	@Schema(description = "상세코드ID", example = "1")
	private Integer detailId;
	@Schema(description = "상세코드명", example = "feed")
	private String detailName;
	@Schema(description = "상세코드설명", example = "피드")
	private String description;

	public static DetailCodeResponse toDto(DetailCode detailCode) {
		DetailCodeResponse response = new DetailCodeResponse();

		if (detailCode != null) {
			response.setCodeId(detailCode.getCode().getCodeId());
			response.setName(detailCode.getCode().getName());
			response.setDetailId(detailCode.getDetailId());
			response.setDetailName(detailCode.getName());
			response.setDescription(detailCode.getDescription());
		}

		return response;
	}
}
