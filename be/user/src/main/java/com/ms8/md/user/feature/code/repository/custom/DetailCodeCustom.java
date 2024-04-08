package com.ms8.md.user.feature.code.repository.custom;

import java.util.List;
import java.util.Optional;

import com.ms8.md.user.feature.code.entity.DetailCode;

public interface DetailCodeCustom {

	List<DetailCode> selectListDetailCode(Integer codeId);

	Optional<DetailCode> selectOneDetailCode(Integer codeId, String name);

	List<DetailCode> selecDetailCodeByCodeName(String codeName);
}
