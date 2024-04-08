package com.ms8.md.user.feature.code.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ms8.md.user.feature.code.entity.Code;
import com.ms8.md.user.feature.code.entity.DetailCode;
import com.ms8.md.user.feature.code.repository.custom.DetailCodeCustom;

public interface DetailCodeRepository extends JpaRepository<DetailCode, Integer>, DetailCodeCustom {

	List<DetailCode> findDetailCodeByCode(Code code);

	@Query("select d from DetailCode d where d.code.codeId = :codeId and d.name = :name ")
	DetailCode findDetailCodeResponse(@Param("codeId") Integer codeId, @Param("name") String name);

}
