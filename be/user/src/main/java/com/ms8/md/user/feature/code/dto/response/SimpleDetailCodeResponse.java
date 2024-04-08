package com.ms8.md.user.feature.code.dto.response;

import com.ms8.md.user.feature.code.entity.DetailCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "상세코드 간단 정보")
public class SimpleDetailCodeResponse {

	// 상세코드
	@Schema(description = "상세코드ID", example = "1")
	private Integer detailId;
	@Schema(description = "상세코드명", example = "feed")
	private String detailName;
	@Schema(description = "상세코드설명", example = "피드")
	private String description;

	public static SimpleDetailCodeResponse toDto(DetailCode detailCode) {
		SimpleDetailCodeResponse response = new SimpleDetailCodeResponse();

		if (detailCode != null) {
			response.setDetailId(detailCode.getDetailId());
			response.setDetailName(detailCode.getName());
			response.setDescription(detailCode.getDescription());
		}

		return response;
	}
}
