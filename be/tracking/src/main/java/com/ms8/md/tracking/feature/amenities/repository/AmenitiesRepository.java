package com.ms8.md.tracking.feature.amenities.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ms8.md.tracking.feature.amenities.entity.Amenities;
import com.ms8.md.tracking.feature.amenities.repository.custom.AmenitiesGetRepository;

public interface AmenitiesRepository extends JpaRepository<Amenities, Long>, AmenitiesGetRepository {

	@Query("SELECT a FROM Amenities a WHERE a.amenitiesId = :amenitiesId AND a.isDeleted = false")
	Optional<Amenities> findAmenitiesByNotDeleted(Long amenitiesId);
	@Override
	List<Amenities> findAllById(Iterable<Long> longs);
}
