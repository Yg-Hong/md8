package com.ms8.md.sns.feature.pin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms8.md.sns.feature.pin.entity.Pin;

public interface PinRepository extends JpaRepository<Pin, Long> {

	@Query("select p from Pin p where p.isDeleted = false and p.userId = :userId order by p.id desc ")
	List<Pin> getMyPinIsActive(@Param("userId") Integer userId);
}
