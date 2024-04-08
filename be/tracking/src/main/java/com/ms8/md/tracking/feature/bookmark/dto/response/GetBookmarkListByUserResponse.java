package com.ms8.md.tracking.feature.bookmark.dto.response;

import com.ms8.md.tracking.feature.bookmark.dto.common.BookmarkResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBookmarkListByUserResponse {
    private Long totalSize;
    private Integer totalPage;
    private List<BookmarkResponse> bookmarkResponses;
}
