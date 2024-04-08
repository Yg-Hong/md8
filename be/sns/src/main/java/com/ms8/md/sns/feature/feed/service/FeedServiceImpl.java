package com.ms8.md.sns.feature.feed.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.sns.feature.feed.dto.request.FeedModifyRequest;
import com.ms8.md.sns.feature.feed.dto.request.FeedSaveRequest;
import com.ms8.md.sns.feature.feed.dto.request.HashtagModifyRequest;
import com.ms8.md.sns.feature.feed.dto.request.HashtagSaveRequest;
import com.ms8.md.sns.feature.feed.dto.response.FeedDetailResponse;
import com.ms8.md.sns.feature.feed.dto.response.FeedSliceResponse;
import com.ms8.md.sns.feature.feed.dto.response.HashtagResponse;
import com.ms8.md.sns.feature.feed.dto.response.MyFeedSliceResponse;
import com.ms8.md.sns.feature.feed.dto.response.MyFeedThumbnailResponse;
import com.ms8.md.sns.feature.feed.entity.Feed;
import com.ms8.md.sns.feature.feed.repository.FeedRepository;
import com.ms8.md.sns.feature.feignclient.dto.CodeResponse;
import com.ms8.md.sns.feature.feignclient.dto.FollowResponse;
import com.ms8.md.sns.feature.feignclient.dto.UserResponse;
import com.ms8.md.sns.feature.feignclient.service.CodeClient;
import com.ms8.md.sns.feature.feignclient.service.FollowClient;
import com.ms8.md.sns.feature.feignclient.service.UserClient;
import com.ms8.md.sns.feature.photo.dto.response.PhotoResponse;
import com.ms8.md.sns.feature.photo.service.PhotoService;
import com.ms8.md.sns.global.S3.service.S3FileUploadService;
import com.ms8.md.sns.global.common.code.ErrorCode;
import com.ms8.md.sns.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

	public static final String CONTENT_CODE_FEED = "Feed";
	public static final int THUMBNAIL_PAGE_SIZE = 12;
	private static final int PAGE_SIZE = 10;

	private final FeedRepository feedRepository;

	private final HashtagService hashtagService;
	private final PhotoService photoService;
	private final S3FileUploadService s3FileUploadService;

	private final UserClient userClient;
	private final FollowClient followClient;
	private final CodeClient codeClient;


	@Override
	@Transactional
	public void createFeed(FeedSaveRequest request, List<MultipartFile> fileList) {
		// #1. 피드 저장
		Feed feed = Feed.builder()
			.memberId(request.userId())
			.trackingId(request.trackingId())
			.content(request.content())
			.build();

		Feed savedFeed = feedRepository.save(feed);

		// #2. 해시태그 저장
		request.hashtagList()
			.forEach(hashtag -> createHashtag(savedFeed, hashtag));

		// #3. S3 사진 업로드 후 DB 사진 URL 저장
		List<String> photoUrlList = new ArrayList<>();
		if (fileList != null && !fileList.isEmpty()) {
			photoUrlList = saveImageOfS3(fileList);
		}

		photoService.savePhoto(photoUrlList, getCodeIdByContent(), CONTENT_CODE_FEED, savedFeed.getId());
	}

	private List<String> saveImageOfS3(List<MultipartFile> fileList) {
		return fileList.stream()
			.map(s3FileUploadService::uploadFile)
			.toList();
	}

	@Override
	public FeedSliceResponse getFeedList(Integer userId, Long lastFeedId) {
		// 1. 회원이 팔로잉 하고있는 대상 조회
		List<FollowResponse> followingList = followClient.getFollwingList(userId).getData();

		List<Integer> userIdList = followingList.stream()
			.map(FollowResponse::getFollowingId)
			.toList();
		PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE);

		Slice<Feed> feedSlice = feedRepository.selectFeedListByLastId(userIdList, pageRequest, lastFeedId);
		List<Feed> feedList = feedSlice.getContent();

		Map<Integer, FollowResponse> followingMap = followingList.stream()
			.collect(Collectors.toMap(FollowResponse::getFollowingId, Function.identity()));

		List<FeedDetailResponse> list = feedList.stream()
			.filter(feed -> followingMap.containsKey(feed.getUserId()))
			.map(feed -> {
				FollowResponse map = followingMap.get(feed.getUserId());
				UserResponse userResponse = UserResponse.builder()
					.id(map.getFollowingId())
					.nickName(map.getFollowingName())
					.profile(map.getFollowingProfile())
					.build();

				List<HashtagResponse> hashtags = hashtagService.getHashtags(feed.getId());
				List<PhotoResponse> photos = getPhotosByFeedId(feed.getId());

				return new FeedDetailResponse().toDto(userResponse, photos, feed, hashtags);
			}).toList();

		// 피드 결과 row userID를 가지고 다시 조회해서 회원 정보를 가지고 올것인지?
		Long newLastFeedId = !list.isEmpty() ? list.get(list.size() - 1).getFeedId() : null;

		return new FeedSliceResponse().toDto(list, newLastFeedId, feedSlice.hasNext());
	}

	@Override
	public MyFeedSliceResponse getMyFeedList(Integer userId, Long lastFeedId) {
		PageRequest pageRequest = PageRequest.of(0, THUMBNAIL_PAGE_SIZE);
		Slice<Feed> myFeedsSlice = feedRepository.selectMyFeedListByLastId(userId, pageRequest, lastFeedId);
		List<MyFeedThumbnailResponse> myFeedList = getMyFeedThumbnailResponseList(
			myFeedsSlice);

		Long newLastFeedId = !myFeedList.isEmpty() ? myFeedList.get(myFeedList.size() - 1).getFeedId() : null;

		return new MyFeedSliceResponse().toDto(myFeedList, newLastFeedId ,myFeedsSlice.hasNext());
	}

	private List<MyFeedThumbnailResponse> getMyFeedThumbnailResponseList(Slice<Feed> myFeedsSlice) {
		return myFeedsSlice.stream()
			.map(feed -> {
				PhotoResponse photo = photoService.getPhotoOfOne(getContentCode(), feed.getId());
				String photoURL = s3FileUploadService.getURL(photo.getFileUrl());

				return new MyFeedThumbnailResponse().toDto(feed.getId(), photoURL);
			}).toList();
	}

	@Override
	public FeedDetailResponse getFeedDetail(Long feedId) {

		return feedRepository.findById(feedId)
			.map(feed -> {
				List<HashtagResponse> hashtags = hashtagService.getHashtags(feedId);
				UserResponse user = userClient.getUser(feed.getUserId()).getData();
				List<PhotoResponse> photoList = getPhotosByFeedId(feedId);

				return new FeedDetailResponse().toDto(user, photoList, feed, hashtags);
			})
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST));
	}

	@Override
	@Transactional
	public void modifyFeed(Long feedId, FeedModifyRequest request, List<MultipartFile> fileList) {
		Feed feed = feedRepository.findById(feedId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST));

		// #1. 피드 수정
		feed.updateFeed(request.content());

		// #2. 해시태그 수정
		List<HashtagModifyRequest> hashtagList = request.hashtagList();
		hashtagList.forEach(tag -> processHashtag(tag, feed));

		// #3. 사진수정 (삭제 후 저장)
		if (fileList != null && !fileList.isEmpty()) {
			Integer codeId = getCodeIdByContent();
			Integer contentCode = getContentCode();

			// #3-1. 사진 삭제처리 DB, S3
			photoService.removePhoto(feed.getId(), contentCode);
			photoService.getAllPhotos(contentCode, feed.getId())
				.forEach(photo -> s3FileUploadService.deleteFile(photo.getFileUrl()));

			// #3-2. 사진 저장 S3, DB
			List<String> photoUrlList = saveImageOfS3(fileList);
			photoService.savePhoto(photoUrlList, codeId, CONTENT_CODE_FEED, feed.getId());
		}
	}


	/**
	 * 2-1. 수정 : 해시태그 ID, 명 둘 다 있는 경우 -> dirty checking
	 * 2-2. 신규 : 해시태그명만 있는경우
	 * 2-3. 삭제 : 해시태그 ID만 있고 해시태그명 없는 경우
	 * @param hashtagRequest 해시태그 수정 요청 데이터
	 * @param feed 해시태그가 작성된 피드
	 */
	private void processHashtag(HashtagModifyRequest hashtagRequest, Feed feed) {
		if (hashtagRequest.hashtagId() == null) {
			createHashtag(feed, hashtagRequest.hashtagName());
		} else if (!StringUtils.hasText(hashtagRequest.hashtagName())) {
			removeHashtag(hashtagRequest);
		} else {
			modifyHashtag(hashtagRequest);
		}
	}

	private void createHashtag(Feed feed, String tag) {
		hashtagService.createHashtag(new HashtagSaveRequest(feed, tag));
	}

	private void removeHashtag(HashtagModifyRequest tag) {
		hashtagService.removeHashtag(tag.hashtagId());
	}

	private void modifyHashtag(HashtagModifyRequest tag) {
		hashtagService.modifyHashtag(tag);
	}

	@Override
	@Transactional
	public void removeFeed(Long feedId) {
		Feed feed = feedRepository.findById(feedId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST));

		feed.deleteData();
	}

	@Override
	public MyFeedSliceResponse searchFeedByHashtag(String hashtag, Long lastFeedId) {
		PageRequest pageRequest = PageRequest.of(0, THUMBNAIL_PAGE_SIZE);
		Slice<Feed> searchFeedSlice = feedRepository.selectFeedSearchHashtag(hashtag, pageRequest, lastFeedId);
		List<MyFeedThumbnailResponse> searchFeedList = getMyFeedThumbnailResponseList(searchFeedSlice);

		Long newLastFeedId = !searchFeedList.isEmpty() ? searchFeedList.get(searchFeedList.size() - 1).getFeedId() : null;

		return new MyFeedSliceResponse().toDto(searchFeedList, newLastFeedId ,searchFeedSlice.hasNext());
	}

	private List<PhotoResponse> getPhotosByFeedId(Long feedId) {
		return photoService.getAllPhotos(getCodeIdByContent(), feedId).stream()
			.peek(photo -> {
				String url = s3FileUploadService.getURL(photo.getFileUrl());
				photo.setFileUrl(url);
			}).toList();
	}

	private Integer getCodeIdByContent() {
		// Content 는 고정된 값 => DB에 저장되고 관리됨
		CodeResponse data = codeClient.getCode("Content").getData();
		return data.getCodeId();
	}

	private Integer getContentCode() {
		Integer codeId = getCodeIdByContent();
		return codeClient.getDetailCodeByName(codeId, CONTENT_CODE_FEED)
			.getData().getDetailId();
	}
}
