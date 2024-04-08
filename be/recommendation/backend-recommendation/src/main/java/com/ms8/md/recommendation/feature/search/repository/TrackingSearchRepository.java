package com.ms8.md.recommendation.feature.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.ms8.md.recommendation.feature.search.entity.TrackingDocument;

public interface TrackingSearchRepository extends ElasticsearchRepository<TrackingDocument, Long> {
	public Page<TrackingDocument> findByTitleContainingAndIsDeleted(String title, Boolean isDeleted, Pageable pageable);
	public Page<TrackingDocument> findByContentContainingAndIsDeleted(String content, Boolean isDeleted, Pageable pageable);
}
