package com.ms8.md.user.feature.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.user.feature.user.dto.request.UserModifyMyInfoRequest;
import com.ms8.md.user.feature.user.dto.response.UserResponse;
import com.ms8.md.user.feature.user.entity.User;

public interface UserService {

	UserResponse getUserByEmail(String email);

	void modifyUser(Integer userId, UserModifyMyInfoRequest userInfo, MultipartFile file);

	void modifyUserTrackingInfo(Integer userId, Integer trackingDistance);

	void logOutUser(Integer userId);

	User getuserEntity(Integer userId);

}
