package com.ms8.md.user.feature.code.repository.custom;

import static com.ms8.md.user.feature.code.entity.QCode.*;
import static com.ms8.md.user.feature.code.entity.QDetailCode.*;

import java.util.List;
import java.util.Optional;

import com.ms8.md.user.feature.code.entity.DetailCode;
import com.ms8.md.user.feature.code.entity.QCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DetailCodeRepositoryImpl implements DetailCodeCustom {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public List<DetailCode> selectListDetailCode(Integer codeId) {

		return jpaQueryFactory.selectFrom(detailCode)
			.where(eqCodeId(codeId))
			.fetch();
	}

	@Override
	public Optional<DetailCode> selectOneDetailCode(Integer codeId, String name) {
		DetailCode result = jpaQueryFactory.selectFrom(detailCode)
			.where(eqCodeId(codeId), eqName(name))
			.fetchOne();
		return Optional.ofNullable(result);
	}

	@Override
	public List<DetailCode> selecDetailCodeByCodeName(String codeName) {
		return jpaQueryFactory.selectFrom(detailCode)
			.innerJoin(detailCode.code, code)
			.on(code.eq(detailCode.code))
			.where(code.name.eq(codeName))
			.fetch();
	}

	private BooleanExpression eqCodeId(Integer codeId) {
		return detailCode.code.codeId.eq(codeId);
	}

	private BooleanExpression eqName(String name) {
		return detailCode.name.eq(name);
	}
}
