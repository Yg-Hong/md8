package com.ms8.md.tracking.feature.bookmark.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ms8.md.tracking.feature.bookmark.entity.TrackingBookmark;

import java.util.Collection;
import java.util.List;

public interface TrackingBookmarkRepository extends JpaRepository<TrackingBookmark, Long> {
    public Page<TrackingBookmark> findByUserIdIn(Collection<Integer> id, Pageable pageable);
}
