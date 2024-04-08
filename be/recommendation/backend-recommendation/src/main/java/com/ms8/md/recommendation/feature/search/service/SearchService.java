package com.ms8.md.recommendation.feature.search.service;

import com.ms8.md.recommendation.feature.search.dto.response.ResponseSearchByContentDto;
import com.ms8.md.recommendation.feature.search.dto.response.ResponseSearchByTitleDto;

public interface SearchService {
	ResponseSearchByTitleDto searchByTitle(String title, Integer page, Integer size);
	ResponseSearchByContentDto searchByContent(String content, Integer page, Integer size);
}
