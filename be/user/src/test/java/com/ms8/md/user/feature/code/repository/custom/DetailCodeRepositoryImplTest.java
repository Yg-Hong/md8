// package com.ms8.md.user.feature.code.repository.custom;
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
// import com.ms8.md.user.feature.code.entity.Code;
// import com.ms8.md.user.feature.code.entity.DetailCode;
// import com.ms8.md.user.feature.code.repository.CodeRepository;
// import com.ms8.md.user.feature.code.repository.DetailCodeRepository;
//
// @SpringBootTest
// @Transactional
// class DetailCodeRepositoryImplTest {
//
// 	@Autowired
// 	private DetailCodeRepository detailCodeRepository;
//
// 	@Autowired
// 	private CodeRepository codeRepository;
//
// 	@Test
// 	public void 상세코드조회 () throws Exception {
// 	    //given
// 		Integer codeId = 6;
// 		Code code = codeRepository.findById(codeId).get();
//
// 		//when
// 		List<DetailCode> detailCodes = detailCodeRepository.selectListDetailCode(codeId);
//
// 		//then
// 		detailCodes.forEach(System.out::println);
// 		assertThat(detailCodes.size()).isEqualTo(4);
//
// 	}
//
// }