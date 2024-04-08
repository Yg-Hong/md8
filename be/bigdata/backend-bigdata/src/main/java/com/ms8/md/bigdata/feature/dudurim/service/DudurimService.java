package com.ms8.md.bigdata.feature.dudurim.service;

import com.ms8.md.bigdata.feature.dudurim.dto.response.ResponseDudurimDetailDto;
import com.ms8.md.bigdata.feature.dudurim.dto.response.ResponseDudurimDto;

public interface DudurimService {
		ResponseDudurimDto getDudurim(Double lat, Double lon);
		ResponseDudurimDetailDto getDudurimDetail(Integer ptrackingId);
}
