package com.ms8.md.tracking.feature.tracking.dto.response;

import com.ms8.md.tracking.feature.bookmark.dto.common.BookmarkResponse;
import com.ms8.md.tracking.feature.tracking.dto.common.TrackingResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTrackingListByCreatorResponse {
    private Long totalSize;
    private Integer totalPage;
    private List<TrackingResponse> trackingResponses;
}
