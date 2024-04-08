package com.ms8.md.bigdata.feature.dudurim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ms8.md.bigdata.feature.dudurim.entity.Dudurim;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface DudurimRepository extends JpaRepository<Dudurim, Integer> {
	@Query(value = "SELECT * FROM md.dudurim l ORDER BY l.geom <-> ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326) LIMIT 10", nativeQuery = true)
	List<Dudurim> findNearestLocations(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
