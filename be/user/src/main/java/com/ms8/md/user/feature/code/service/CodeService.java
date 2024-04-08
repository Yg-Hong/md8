package com.ms8.md.user.feature.code.service;

import java.util.List;

import com.ms8.md.user.feature.code.dto.request.CodeModifyRequest;
import com.ms8.md.user.feature.code.dto.request.CodeSaveRequest;
import com.ms8.md.user.feature.code.dto.response.CodeResponse;
import com.ms8.md.user.feature.code.entity.Code;

public interface CodeService {

	// DTO 처리
	void createCode(CodeSaveRequest request);

	List<CodeResponse> getCodeList();

	CodeResponse getCodeResponse(Integer codeId);
	CodeResponse getCodeResponseByName(String name);

	void modifyCode(Integer codeId, CodeModifyRequest request);

	void removeCode(Integer codeId);

	// 다른 클래스에서 접근하기 위한 Entity 처리
	Code getCodeByCodeId(Integer codeId);
}
