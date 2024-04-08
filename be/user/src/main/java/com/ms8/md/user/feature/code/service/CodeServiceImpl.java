package com.ms8.md.user.feature.code.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms8.md.user.feature.code.dto.request.CodeModifyRequest;
import com.ms8.md.user.feature.code.dto.request.CodeSaveRequest;
import com.ms8.md.user.feature.code.dto.response.CodeResponse;
import com.ms8.md.user.feature.code.entity.Code;
import com.ms8.md.user.feature.code.repository.CodeRepository;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

	private final CodeRepository codeRepository;

	@Override
	@Transactional
	public void createCode(CodeSaveRequest request) {
		codeRepository.findByName(request.name())
			.ifPresent( a -> {
			throw new BusinessExceptionHandler("중복데이터 존재 코드ID = " + a.getCodeId(), ErrorCode.DATA_ALREADY_EXIST);
			});

		Code code = Code.builder()
			.name(request.name())
			.description(request.description())
			.build();
		codeRepository.save(code);
	}

	@Override
	public List<CodeResponse> getCodeList() {
		return codeRepository.findAll()
			.stream()
			.map(CodeResponse::toDto)
			.toList();
	}

	@Override
	public CodeResponse getCodeResponse(Integer codeId) {
		return codeRepository.findById(codeId)
			.map(CodeResponse::toDto)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST));
	}

	@Override
	public CodeResponse getCodeResponseByName(String name) {
		return codeRepository.findByName(name)
			.map(CodeResponse::toDto)
			.orElse(new CodeResponse());
	}

	@Override
	@Transactional
	public void modifyCode(Integer codeId, CodeModifyRequest request) {
		Code code = getCodeByCodeId(codeId);
		code.updateCode(request.name(), request.description());
		log.info("코드 업데이트 완료={}", code);
	}

	@Override
	@Transactional
	public void removeCode(Integer codeId) {
		Code code = getCodeByCodeId(codeId);
		codeRepository.delete(code);
		log.info("코드 삭제 완료={}", code);
	}

	@Override
	public Code getCodeByCodeId(Integer request) {
		return codeRepository.findById(request)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST));
	}
}