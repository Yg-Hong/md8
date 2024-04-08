package com.ms8.md.sns.feature.feignclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(title = "코드 정보")
public class CodeResponse {

	@Schema(description = "코드ID")
	private Integer codeId;
	@Schema(description = "코드명")
	private String name;
	@Schema(description = "코드설명")
	private String description;


}
