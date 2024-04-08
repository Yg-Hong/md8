package com.ms8.md.sns.feature.photo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms8.md.sns.feature.photo.entity.Photo;
import com.ms8.md.sns.feature.photo.repository.custom.PhotoCustomRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer>, PhotoCustomRepository {

	@Query("select p from Photo p where p.isDeleted = false and p.contentCode = :contentCode and p.contentId = :contentId")
	List<Photo> findAllByContent(@Param("contentCode") Integer contentCode, @Param("contentId") Long contentId);


}
