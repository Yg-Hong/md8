package com.ms8.md.user.feature.user.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.user.feature.code.entity.DetailCode;
import com.ms8.md.user.feature.code.service.DetailCodeService;
import com.ms8.md.user.feature.feignclients.service.S3Client;
import com.ms8.md.user.feature.user.dto.request.UserModifyMyInfoRequest;
import com.ms8.md.user.feature.user.dto.response.UserResponse;
import com.ms8.md.user.feature.user.entity.User;
import com.ms8.md.user.feature.user.repository.UserRepository;
import com.ms8.md.user.global.common.code.ErrorCode;
import com.ms8.md.user.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final DetailCodeService detailCodeService;
	private final S3Client s3Client;

	private final StringRedisTemplate redisTemplate;

	@Override
	public UserResponse getUserByEmail(String email) {
		return userRepository.findByEmail(email)
			.map(new UserResponse()::toDtoWithCounts)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));
	}

	@Override
	@Transactional
	public void modifyUser(Integer userId, UserModifyMyInfoRequest userInfo, MultipartFile file) {
		userRepository.findById(userId)
			.map(user -> {
				if (userInfo.ageDetailCodeId() != null) {
					DetailCode ageDetailCode = detailCodeService.getDetailCode(userInfo.ageDetailCodeId());
					user.updateAgeCode(ageDetailCode);
				}

				if (file != null && !file.isEmpty()) {
					String photoURL = s3Client.createS3File(file).getData();
					user.updateProfile(photoURL);
				}

				user.updateMyInfo(userInfo.nickName(), userInfo.gender(), userInfo.time(),
					userInfo.distance());
				return user;
			})
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));
	}

	/**
	 * 산책 후 정보 업데이트
	 * UPDATE => 총 산책거리, 레벨
	 */
	@Override
	@Transactional
	public void modifyUserTrackingInfo(Integer userId, Integer trackingDistance) {
		userRepository.findById(userId)
			.map(user -> {
				user.updateTotalDistance(trackingDistance);
				user.updateLevel();
				return user;
			})
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));
	}

	@Override
	public void logOutUser(Integer userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));

		redisTemplate.delete(user.getEmail());
	}

	@Override
	public User getuserEntity(Integer userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.INVALID_USER_INFO));
	}
}
