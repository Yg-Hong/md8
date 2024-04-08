package com.ms8.md.tracking.feature.amenities.repository;

import static com.ms8.md.tracking.feature.amenities.entity.QAmenities.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.ms8.md.tracking.feature.amenities.entity.Amenities;
import com.ms8.md.tracking.feature.amenities.repository.custom.AmenitiesGetRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AmenitiesRepositoryImpl implements AmenitiesGetRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Amenities> selectAllByCoordinates(List<Long> idxList, Pageable pageable) {
		return queryFactory.selectFrom(amenities)
			.where(amenities.amenitiesId.in(idxList).and(isNotDeleted()))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();
	}

	private static BooleanExpression isNotDeleted() {
		return amenities.isDeleted.eq(Boolean.FALSE);
	}

}
