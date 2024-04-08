package com.ms8.md.sns.feature.feignclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
public class DetailCodeResponse {

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


}
