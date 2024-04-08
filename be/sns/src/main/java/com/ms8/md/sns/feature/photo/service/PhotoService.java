package com.ms8.md.sns.feature.photo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms8.md.sns.feature.feignclient.dto.DetailCodeResponse;
import com.ms8.md.sns.feature.feignclient.service.CodeClient;
import com.ms8.md.sns.feature.photo.dto.response.PhotoResponse;
import com.ms8.md.sns.feature.photo.entity.Photo;
import com.ms8.md.sns.feature.photo.repository.PhotoRepository;
import com.ms8.md.sns.global.common.entity.DeleteEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PhotoService {

	private final PhotoRepository photoRepository;
	private final CodeClient codeClient;

	/**
	 * @param photoUrlList  s3에 저장된 사진URL 목록
	 * @param codeId 'Content'로 저장된 코드 ID
	 * @param contentType 상세코드 DB에 저장된 'Feed', 'Pin'을 구분하기 위한 타입
	 * @param contentId 컨텐츠에 맞는 해당 ID (ex. Feed Id 또는 Pin Id)
	 */
	@Transactional
	public void savePhoto(List<String> photoUrlList, Integer codeId, String contentType, Long contentId) {
		DetailCodeResponse detailCode = codeClient.getDetailCodeByName(codeId, contentType).getData();
		Integer detailId = detailCode.getDetailId();

		photoUrlList.forEach(photoUrl -> {
			savePhoto(photoUrl, contentId, detailId);
		});

	}

	/**
	 * @param photoUrl s3에 저장된 사진 URL
	 * @param codeId 'Content'로 저장된 코드 ID
	 * @param contentType 상세코드 DB에 저장된 'Feed', 'Pin'을 구분하기 위한 타입
	 * @param contentId 컨텐츠에 맞는 해당 ID (ex. Feed Id 또는 Pin Id)
	 */
	@Transactional
	public void savePhoto(String photoUrl, Integer codeId, String contentType, Long contentId) {
		DetailCodeResponse detailCode = codeClient.getDetailCodeByName(codeId, contentType).getData();
		savePhoto(photoUrl, contentId, detailCode.getDetailId());
	}

	private void savePhoto(String photoUrl, Long contentId, Integer detailCode) {
		Photo photo = Photo.builder()
			.contentCode(detailCode)
			.contentId(contentId)
			.fileName(photoUrl)
			.build();

		photoRepository.save(photo);
	}

	public List<PhotoResponse> getAllPhotos(Integer contentCode, Long contentId) {
		List<Photo> photoList = photoRepository.findAllByContent(contentCode, contentId);

		return photoList.stream()
			.map(photo -> new PhotoResponse().toDto(photo))
			.toList();
	}

	public PhotoResponse getPhotoOfOne(Integer contentCode, Long contentId) {
		Photo photo = photoRepository.selectPhotoLimitOne(contentCode, contentId);
		return new PhotoResponse().toDto(photo);
	}

	@Transactional
	public void removePhoto(Long contentId, Integer contentCode) {
		List<Photo> photoList = photoRepository.findAllByContent(contentCode, contentId);
		photoList.forEach(DeleteEntity::deleteData);
	}

}
