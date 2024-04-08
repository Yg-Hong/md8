package com.ms8.md.tracking.feature.tracking.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.ms8.md.tracking.feature.bookmark.entity.TrackingBookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms8.md.tracking.feature.tracking.entity.Tracking;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
	@EntityGraph(attributePaths = {"user", "trackingBookmarks"})
	@Query("SELECT t FROM Tracking t WHERE t.trackingId = :trackingId AND t.isDeleted = false")
	Optional<Tracking> findByNotIsDeleted(Long trackingId);

	@EntityGraph(attributePaths = {"user", "trackingBookmarks"})
	@Query("SELECT t FROM Tracking t WHERE t.trackingId IN :trackingIds AND t.isDeleted = false")
	List<Tracking> findByNotIsDeletedAndTrackingIdIn(List<Long> trackingIds, Pageable pageable);

	@Override
	@EntityGraph(attributePaths = {"user"})
	Optional<Tracking> findById(Long trackingId);

	public Page<Tracking> findByUserIdIn(Collection<Integer> id, Pageable pageable);
}
