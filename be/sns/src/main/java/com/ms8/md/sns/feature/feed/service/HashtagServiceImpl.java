package com.ms8.md.sns.feature.feed.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms8.md.sns.feature.feed.dto.request.HashtagModifyRequest;
import com.ms8.md.sns.feature.feed.dto.request.HashtagSaveRequest;
import com.ms8.md.sns.feature.feed.dto.response.HashtagResponse;
import com.ms8.md.sns.feature.feed.entity.Hashtag;
import com.ms8.md.sns.feature.feed.repository.HashtagRepository;
import com.ms8.md.sns.global.common.code.ErrorCode;
import com.ms8.md.sns.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {

	private final HashtagRepository hashtagRepository;

	@Override
	public List<HashtagResponse> getHashtags(Long feedId) {
		return hashtagRepository.findByFeedId(feedId).stream()
			.map(tag ->  new HashtagResponse().toDto(tag))
			.toList();
	}

	@Override
	@Transactional
	public void createHashtag(HashtagSaveRequest request) {
		Hashtag exist = hashtagRepository.isExist(request.feed(), request.hashtagName());

		if (exist != null) {
			throw new BusinessExceptionHandler(ErrorCode.DATA_ALREADY_EXIST);
		}

		Hashtag hashtag = Hashtag.builder()
			.feed(request.feed())
			.hashtagName(request.hashtagName())
			.build();

		hashtagRepository.save(hashtag);
	}

	@Override
	@Transactional
	public void modifyHashtag(HashtagModifyRequest request) {
		hashtagRepository.findById(request.hashtagId())
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST))
			.updateHashtag(request.hashtagName());
	}

	@Override
	@Transactional
	public void removeHashtag(Integer hashtagId) {
		hashtagRepository.findById(hashtagId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST))
			.deleteData();
	}
}
