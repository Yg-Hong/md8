package com.ms8.md.tracking.feature.bookmark.service;

import com.ms8.md.tracking.feature.bookmark.dto.common.BookmarkResponse;
import com.ms8.md.tracking.feature.bookmark.dto.response.GetBookmarkListByUserResponse;
import com.ms8.md.tracking.feature.bookmark.entity.TrackingBookmark;
import com.ms8.md.tracking.feature.bookmark.repository.TrackingBookmarkRepository;
import com.ms8.md.tracking.feature.tracking.entity.Tracking;
import com.ms8.md.tracking.feature.tracking.entity.User;
import com.ms8.md.tracking.feature.tracking.repository.TrackingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService{

	private final TrackingBookmarkRepository trackingBookmarkRepository;
	private final TrackingRepository trackingRepository;

	@Override
	public void createBookmark(Long trackingId, Integer userId) {
		TrackingBookmark bookmark = TrackingBookmark.builder()
				.user(User.builder().id(userId).build())
				.tracking(Tracking.builder().trackingId(trackingId).build())
				.build();
		trackingBookmarkRepository.save(bookmark);
	}

	@Override
	public GetBookmarkListByUserResponse getBookmarkListByUserId(Integer userId, Integer page, Integer size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
		Page<TrackingBookmark> byUserIdIn = trackingBookmarkRepository.findByUserIdIn(Collections.singleton(userId), pageable);

		List<BookmarkResponse> result = new ArrayList<>();
		for (TrackingBookmark trackingBookmark : byUserIdIn) {
			BookmarkResponse bookmarkResponse = new BookmarkResponse(trackingBookmark.getTracking());
			result.add(bookmarkResponse);

		}

		return GetBookmarkListByUserResponse.builder()
				.bookmarkResponses(result)
				.totalPage(byUserIdIn.getTotalPages())
				.totalSize(byUserIdIn.getTotalElements())
				.build();
	}
}
