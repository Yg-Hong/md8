package com.ms8.md.bigdata.feature.dudurim.dto.response;

import java.util.List;

import com.ms8.md.bigdata.feature.dudurim.dto.common.DudurimDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDudurimDetailDto {
	private Integer id;
	private String title;
	private String address;
	private String level;
	private Double distance;
	private String time;
	private Boolean drink;
	private Boolean toilet;
	private Boolean convience;
	private Integer congestion;
	private String weather;
	private String fineDust;
	private String degree;
	private String content;
	private String keywords;
	private String image;
	private String icon;
}
