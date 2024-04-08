package com.ms8.md.sns.feature.photo.repository.custom;

import com.ms8.md.sns.feature.photo.entity.Photo;

public interface PhotoCustomRepository {

	Photo selectPhotoLimitOne(Integer contentCode, Long contentId);
}
