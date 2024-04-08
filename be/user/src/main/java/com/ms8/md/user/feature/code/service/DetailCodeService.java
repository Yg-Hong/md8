package com.ms8.md.user.feature.code.service;

import java.util.List;

import com.ms8.md.user.feature.code.dto.request.DetailCodeModifyRequest;
import com.ms8.md.user.feature.code.dto.request.DetailCodeSaveRequest;
import com.ms8.md.user.feature.code.dto.response.DetailCodeResponse;
import com.ms8.md.user.feature.code.dto.response.SimpleDetailCodeResponse;
import com.ms8.md.user.feature.code.entity.DetailCode;

public interface DetailCodeService {

	void createDetailCode(Integer codeId, DetailCodeSaveRequest request);

	List<DetailCodeResponse> getDetailCodeList(Integer codeId);

	List<SimpleDetailCodeResponse> getSimpleDetailCodeList(String codeName);

	DetailCodeResponse getDetailCodeResponse(Integer codeId, String detailCodeName);
	DetailCode getDetailCode(Integer detailId);
	void modifyDetailCode(Integer detailId, DetailCodeModifyRequest request);

	void removeDetailCode(Integer detailId);

}
