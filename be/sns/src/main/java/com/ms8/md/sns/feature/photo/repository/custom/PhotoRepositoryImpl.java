 package com.ms8.md.sns.feature.photo.repository.custom;

import static com.ms8.md.sns.feature.photo.entity.QPhoto.*;

import com.ms8.md.sns.feature.photo.entity.Photo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PhotoRepositoryImpl implements PhotoCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Photo selectPhotoLimitOne(Integer contentCode, Long contentId) {
		return jpaQueryFactory.selectFrom(photo)
			.where(photo.isDeleted.eq(false),
				photo.contentCode.eq(contentCode),
				photo.contentId.eq(contentId))
			.limit(1)
			.fetchOne();
	}
}
