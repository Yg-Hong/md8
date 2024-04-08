package com.ms8.md.tracking.feature.bookmark.dto.common;

import com.ms8.md.tracking.feature.tracking.entity.Tracking;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookmarkResponse {
    private Long trackingId;
    private String title;
    private Integer distance;
    private Integer time;
    private Integer kcal;
    private LocalDateTime createdAt;
    private Integer bookmark;

    @Builder
    public BookmarkResponse(Tracking tracking) {
        this.trackingId = tracking.getTrackingId();
        this.title = tracking.getTitle();
        this.distance = tracking.getDistance();
        this.time = tracking.getTime();
        this.kcal = tracking.getKcal();
        this.createdAt = tracking.getCreateAt();
        this.bookmark = tracking.getTrackingBookmarks().size();
    }
}
