package com.ms8.md.bigdata.feature.dudurim.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DudurimDto {
	private Integer id;
	private String title;
	private String address;
	private String level;
	private Double distance;
	private String time;
	private Boolean water;
	private Boolean toilet;
	private Boolean store;
	private Integer congestion;
	private String weather;
	private String fineDust;
	private String degree;
	private String image;
	private String icon;
}
