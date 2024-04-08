package com.ms8.md.user.feature.code.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms8.md.user.feature.code.dto.request.DetailCodeModifyRequest;
import com.ms8.md.user.feature.code.dto.request.DetailCodeSaveRequest;
import com.ms8.md.user.feature.code.dto.response.DetailCodeResponse;
import com.ms8.md.user.feature.code.dto.response.SimpleDetailCodeResponse;
import com.ms8.md.user.feature.code.entity.Code;
import com.ms8.md.user.feature.code.entity.DetailCode;
import com.ms8.md.user.feature.code.repository.DetailCodeRepository;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DetailCodeServiceImpl implements DetailCodeService{

	private final DetailCodeRepository detailCodeRepository;
	private final CodeService codeService;

	@Override
	@Transactional
	public void createDetailCode(Integer codeId, DetailCodeSaveRequest request) {
		// #1. 코드ID로 찾은 코드가 없는 경우 -> 에러 발생
		Code code = codeService.getCodeByCodeId(codeId);

		// #2. 같은 코드ID에 상세코드명이 같은 경우 에러 발생
		detailCodeRepository.selectOneDetailCode(codeId, request.name())
			.ifPresent( detailCode -> {
				String msg = String.format("중복 데이터 존재 코드ID : %d, 상세코드ID = %d", detailCode.getCode().getCodeId(),
					detailCode.getDetailId());
				throw new BusinessExceptionHandler(msg, ErrorCode.DATA_ALREADY_EXIST);
			});

		// #3. 상세코드 등록
		DetailCode detailCode = DetailCode.builder()
			.code(code)
			.name(request.name())
			.description(request.description())
			.build();

		detailCodeRepository.save(detailCode);
	}

	@Override
	public List<DetailCodeResponse> getDetailCodeList(Integer codeId) {
		// #1. 코드ID로 찾은 코드가 없는 경우 -> 에러 발생
		Code code = codeService.getCodeByCodeId(codeId);

		// #2. 코드정보로 상세코드 테이블 찾기
		List<DetailCode> detailCodeList = detailCodeRepository.findDetailCodeByCode(code);

		// #3. 찾은 상세코드 entity -> dto 변환
		return detailCodeList.stream()
			.map(DetailCodeResponse::toDto)
			.toList();
	}

	@Override
	public List<SimpleDetailCodeResponse> getSimpleDetailCodeList(String codeName) {
		return detailCodeRepository.selecDetailCodeByCodeName(codeName).stream()
			.map(SimpleDetailCodeResponse::toDto)
			.toList();
	}

	@Override
	public DetailCodeResponse getDetailCodeResponse(Integer codeId, String detailCodeName) {
		DetailCode detailCode = detailCodeRepository.findDetailCodeResponse(codeId, detailCodeName);
		return DetailCodeResponse.toDto(detailCode);
	}

	@Override
	@Transactional
	public void modifyDetailCode(Integer detailId, DetailCodeModifyRequest request) {
		// #1. 상세코드 ID로 찾기 -> 해당정보가 없으면 데이터가 존재하지않음 에러 발생
		DetailCode detailCode = getDetailCode(detailId);

		// #2. 상세코드 수정
		detailCode.update(request.name(), request.description());
	}

	@Override
	@Transactional
	public void removeDetailCode(Integer detailId) {
		// #1. 상세코드 ID로 찾기 -> 해당정보가 없으면 데이터가 존재하지않음 에러 발생
		DetailCode detailCode = getDetailCode(detailId);

		// #2. 상세코드 삭제
		detailCodeRepository.delete(detailCode);
	}

	@Override
	public DetailCode getDetailCode(Integer detailId) {
		return detailCodeRepository.findById(detailId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST));
	}
}
