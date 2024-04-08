package com.ms8.md.tracking.feature.bookmark.service;

import com.ms8.md.tracking.feature.bookmark.dto.common.BookmarkResponse;
import com.ms8.md.tracking.feature.bookmark.dto.response.GetBookmarkListByUserResponse;

import java.util.List;

public interface BookmarkService {
	void createBookmark(Long trackingId, Integer userId);
	GetBookmarkListByUserResponse getBookmarkListByUserId(Integer userId, Integer page, Integer size);
}
