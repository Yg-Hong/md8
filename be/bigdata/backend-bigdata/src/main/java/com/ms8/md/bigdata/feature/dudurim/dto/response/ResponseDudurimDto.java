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
public class ResponseDudurimDto {
	List<DudurimDto> dudurimList;
}
