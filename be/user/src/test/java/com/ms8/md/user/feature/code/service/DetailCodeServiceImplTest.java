// package com.ms8.md.user.feature.code.service;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.List;
//
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.ms8.md.user.feature.code.dto.request.DetailCodeModifyRequest;
// import com.ms8.md.user.feature.code.dto.request.DetailCodeSaveRequest;
// import com.ms8.md.user.feature.code.dto.response.DetailCodeResponse;
// import com.ms8.md.user.feature.code.entity.Code;
// import com.ms8.md.user.feature.code.entity.DetailCode;
// import com.ms8.md.user.feature.code.repository.DetailCodeRepository;
// import com.ms8.md.user.global.common.code.ErrorCode;
// import com.ms8.md.user.global.exception.BusinessExceptionHandler;
//
// @SpringBootTest
// @Transactional
// class DetailCodeServiceImplTest {
//
// 	@Autowired
// 	private DetailCodeService detailCodeService;
//
// 	@Autowired
// 	private DetailCodeRepository detailCodeRepository;
//
// 	@Autowired
// 	private CodeService codeService;
//
// 	// private DetailCodeSaveRequest request;
//
// 	// @BeforeEach
// 	// void init() {
// 	// 	request = new DetailCodeSaveRequest()
// 	// }
//
//
// 	@Test
// 	public void 코드생성_코드존재하지않을때 () throws Exception {
// 	    //given
// 		DetailCodeSaveRequest request = new DetailCodeSaveRequest(1, "test", "ttt");
//
// 		//when
// 		// new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST)
//
// 		//then
// 		assertThatThrownBy(() -> detailCodeService.createDetailCode(request))
// 			.isInstanceOf(BusinessExceptionHandler.class)
// 			.hasFieldOrPropertyWithValue("errorCode",ErrorCode.DATA_NOT_EXIST)
// 			.hasMessage(ErrorCode.DATA_NOT_EXIST.getMessage());
// 	}
//
// 	@Test
// 	public void 코드생성_상세코드명같을때 () throws Exception {
// 	    //given
// 		DetailCodeSaveRequest request = new DetailCodeSaveRequest(6, "상세코드1", "1234");
//
// 		//when
//
// 		//then
// 		assertThatThrownBy(() -> detailCodeService.createDetailCode(request))
// 			.isInstanceOf(BusinessExceptionHandler.class)
// 			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.DATA_ALREADY_EXIST);
// 			// .hasMessage(ErrorCode.DATA_ALREADY_EXIST.getMessage());
// 	}
//
// 	@Test
// 	public void 코드생성 () throws Exception {
// 	    //given
// 		DetailCodeSaveRequest request = new DetailCodeSaveRequest(6, "추가코드", "1234");
//
// 		//when
// 		detailCodeService.createDetailCode(request);
//
// 		Code code = codeService.getCodeByCodeId(6);
//
// 		List<DetailCode> detailCodeList = detailCodeRepository.findDetailCodeByCode(code);
//
// 		// List<DetailCodeResponse> detailCodeList = detailCodeService.getDetailCodeList(6);
// 		//then
// 		assertThat(detailCodeList.size()).isEqualTo(5);
// 	}
//
// 	@Test
// 	public void 상세코드목록조회 () throws Exception {
// 	    //given
// 		Integer codeId = 6;
//
// 	    //when
// 		List<DetailCodeResponse> detailCodeList = detailCodeService.getDetailCodeList(codeId);
//
// 		//then
// 		detailCodeList.forEach(System.out::println);
// 		assertThat(detailCodeList.size()).isEqualTo(4);
// 	}
//
// 	@Test
// 	public void 상세코드_수정_존재하지않는상세코드 () throws Exception {
// 	    //given
// 		DetailCodeModifyRequest request = new DetailCodeModifyRequest(1, "test", "test");
//
// 		//when
// 		assertThatThrownBy(() -> detailCodeService.modifyDetailCode(request))
// 			.isInstanceOf(BusinessExceptionHandler.class)
// 			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.DATA_NOT_EXIST);
// 	}
//
// 	@Test
// 	public void 상세코드_수정 () throws Exception {
//  	    //given
// 		String name = "test";
// 		DetailCodeModifyRequest request = new DetailCodeModifyRequest(1234, name, "test");
//
// 	    //when
// 		detailCodeService.modifyDetailCode(request);
//
// 		DetailCode detailCode = detailCodeRepository.findById(1234).get();
//
// 		//then
// 		System.out.println(detailCode);
// 		assertThat(detailCode.getName()).isEqualTo(name);
// 	}
//
// 	@Test
// 	public void 상세코드_삭제 () throws Exception {
// 	    //given
// 		Integer detailId = 1234;
// 		Integer codeId = 6;
//
// 	    //when
// 		detailCodeService.removeDetailCode(detailId);
// 		List<DetailCodeResponse> detailCodeList = detailCodeService.getDetailCodeList(codeId);
// 		//then
// 		detailCodeList.forEach(System.out::println);
// 		assertThat(detailCodeList.size()).isEqualTo(3);
// 	}
//
// }