package com.ms8.md.tracking.feature.bookmark.controller;

import com.ms8.md.tracking.feature.bookmark.dto.response.GetBookmarkListByUserResponse;
import org.springframework.web.bind.annotation.*;

import com.ms8.md.tracking.feature.bookmark.service.BookmarkService;
import com.ms8.md.tracking.global.common.code.SuccessCode;
import com.ms8.md.tracking.global.common.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmark")
@Tag(name = "bookmark", description = "북마크 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    @Operation(summary = "북마크 등록", description = "북마크 등록")
    public SuccessResponse<?> registerBookmark(@RequestParam Long trackingId, @RequestParam Integer userId) {
        bookmarkService.createBookmark(trackingId, userId);
        return SuccessResponse.builder().data(true).status(SuccessCode.INSERT_SUCCESS).build();
    }

    @GetMapping
    @Operation(summary = "북마크 리스트 조회", description = "북마크 리스트 조회")
    public SuccessResponse<?> getBookmarkList(@RequestParam Integer userId, @RequestParam Integer page, @RequestParam Integer size) {
        GetBookmarkListByUserResponse result = bookmarkService.getBookmarkListByUserId(userId, page, size);
        return SuccessResponse.builder().data(result).status(SuccessCode.SELECT_SUCCESS).build();
    }
}
