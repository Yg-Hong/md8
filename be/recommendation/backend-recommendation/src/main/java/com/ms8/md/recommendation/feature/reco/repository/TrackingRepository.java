package com.ms8.md.recommendation.feature.reco.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ms8.md.recommendation.feature.reco.entity.Tracking;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface TrackingRepository extends JpaRepository<Tracking, Long> {
	List<Tracking> findByIdInAndIsDeleted(Collection<Long> id, Boolean isDeleted);
	List<Tracking> findByIdIn(Collection<Long> ids);
}
