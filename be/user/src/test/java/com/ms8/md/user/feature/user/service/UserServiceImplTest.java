// package com.ms8.md.user.feature.user.service;
//
// import org.assertj.core.api.Assertions;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.ms8.md.user.feature.user.dto.response.UserResponse;
//
// @SpringBootTest
// @Transactional
// class UserServiceImplTest {
//
// 	@Autowired
// 	UserService userService;
//
// 	@Test
// 	public void 산책후업데이트  () throws Exception {
// 	    //given
// 		Integer userId = 1;
// 		Integer trackingDistance = 49_999;
//
// 	    //when
// 		userService.modifyUserTrackingInfo(userId, trackingDistance);
//
// 		UserResponse user = userService.getUser(userId);
// 		System.out.println(user);
// 		//then
//
// 		Assertions.assertThat(user.getLevel()).isEqualTo(2);
// 	}
//
// }