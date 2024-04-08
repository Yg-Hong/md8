package com.ms8.md.recommendation.feature.reco.repository;

import com.ms8.md.recommendation.feature.reco.entity.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long>{
    @Override
    List<Amenities> findAllById(Iterable<Long> longs);
}