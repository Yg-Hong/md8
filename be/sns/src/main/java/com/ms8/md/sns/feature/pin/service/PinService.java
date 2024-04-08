package com.ms8.md.sns.feature.pin.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ms8.md.sns.feature.feignclient.dto.CodeResponse;
import com.ms8.md.sns.feature.feignclient.service.CodeClient;
import com.ms8.md.sns.feature.photo.service.PhotoService;
import com.ms8.md.sns.feature.pin.dto.request.PinSaveRequest;
import com.ms8.md.sns.feature.pin.dto.response.MyPinResponse;
import com.ms8.md.sns.feature.pin.dto.response.PinResponse;
import com.ms8.md.sns.feature.pin.entity.Pin;
import com.ms8.md.sns.feature.pin.repository.PinRepository;
import com.ms8.md.sns.global.S3.service.S3FileUploadService;
import com.ms8.md.sns.global.common.code.ErrorCode;
import com.ms8.md.sns.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@Service
@RequiredArgsConstructor
public class PinService {
	public static final String CONTENT_CODE_PIN = "Pin";
	public static final String GEO_HASH_PATTERN = "*:location";
	public static final String INFO_HASH_PATTERN = "*:info";
	public static final String INFO_MAKE_KEY = ":info";
	public static final String GEO_MAKE_KEY = ":location";
	public static final String PIN_HASH_KEY = "pinId";
	public static final String PHOTO_HASH_KEY = "photoURL";

	private final PinRepository pinRepository;
	private final PhotoService photoService;

	private final S3FileUploadService s3FileUploadService;
	private final CodeClient codeClient;
	private final StringRedisTemplate redisTemplate;
	private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

	@Transactional
	public void createPin(Integer userId, PinSaveRequest request, MultipartFile file) {
		// #1. 핀 저장
		Pin pin = Pin.builder()
			.userId(userId)
			.lat(request.lat())
			.lng(request.lng())
			.build();
		Pin savedPin = pinRepository.save(pin);

		// #2. 사진 저장
		String photoURL = "";
		if (file != null) {
			// photoURL = s3FileUploadService.uploadFileAndReturnSavedName(file);
			photoURL = s3FileUploadService.uploadFile(file);
			Integer codeId = getCodeIdByContent();
			photoService.savePhoto(photoURL, codeId, CONTENT_CODE_PIN, savedPin.getId());
		}

		// #3. Redis 저장
		String pinId = pin.getId().toString();
		String geoKey = pinId + GEO_MAKE_KEY;
		String hashKey = pinId + INFO_MAKE_KEY;
		// String hashKey = HASH_KEY_INFO;

		Double lat = savedPin.getLat();
		Double lng = savedPin.getLng();

		// 하나의 핀에는 하나의 고유한 값이 들어가야함, 만약 하나의 핀에 여러개의 위치가 들어간다면 key는 중첩되게 사용하되 member로 오는 인자값은 고유하게 변경
		redisTemplate.opsForGeo().add(geoKey, new Point(lng, lat), pinId);

		redisTemplate.opsForHash().put(hashKey, PIN_HASH_KEY, pinId);
		redisTemplate.opsForHash().put(hashKey, PHOTO_HASH_KEY, photoURL);

		redisTemplate.expire(hashKey, 24L, TimeUnit.HOURS);
		redisTemplate.expire(geoKey, 24L, TimeUnit.HOURS);
	}

	/**
	 * Point X: 경도, Y: 위도
	 * @param lat 중앙좌표 위도
	 * @param lng 중앙좌표 경도
	 * @param radius 반경 (m 단위)
	 * @return 중앙 좌표 기준 반경에 있는 pin 목록
	 */
	public List<PinResponse> getAllPins(Double lat, Double lng, Double radius) {
		Circle circle = new Circle(new Point(lng, lat), radius);
		RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
			.newGeoRadiusArgs()
			.includeCoordinates();

		Flux<String> geoKeysFlux = keysByPattern(GEO_HASH_PATTERN);
		Flux<GeoResult<RedisGeoCommands.GeoLocation<String>>> geoResultFlux = geoKeysFlux.flatMap(key ->
			reactiveRedisTemplate.opsForGeo()
				.radius(key, circle, args));

		List<PinResponse> pinResponseList = new ArrayList<>();

		geoResultFlux.collectList().blockOptional().ifPresent(values -> {
			for (GeoResult<RedisGeoCommands.GeoLocation<String>> value : values) {
				String hashKey = value.getContent().getName();
				Point point = value.getContent().getPoint();
				// x 경도 y 위도
				String infoKey = hashKey + INFO_MAKE_KEY;

				// 만료시간
				Long ttl = getTTL(infoKey);

				LocalDateTime expiryDateTime = LocalDateTime.now().plusSeconds(ttl);
				Duration remainingTime = Duration.between(LocalDateTime.now(), expiryDateTime);

				String remainTimeToString = String.format("%02d 시간 %02d분 %02d", remainingTime.toHoursPart(),
					remainingTime.toMinutesPart(),
					remainingTime.toSecondsPart());

				Long pinId = Long.parseLong(getPinInfo(infoKey, PIN_HASH_KEY));
				String photoUrl = s3FileUploadService.getURL(getPinInfo(infoKey, PHOTO_HASH_KEY));

				PinResponse pin = PinResponse.builder()
					.pinId(pinId)
					.lat(point.getY())
					.lng(point.getX())
					.photoURL(photoUrl)
					.expireTime(expiryDateTime)
					.remainTime(remainTimeToString)
					.build();

				pinResponseList.add(pin);
			}
		});

		pinResponseList.forEach(System.out::println);
		return pinResponseList;
	}

	private Long getTTL(String infoKey) {
		Long ttl = redisTemplate.getExpire(infoKey, TimeUnit.SECONDS);
		if (ttl == null) {
			throw new IllegalArgumentException("키에 대한 만료 시간이 없습니다.");
		} else if (ttl < 0) {
			throw new IllegalArgumentException("설정된 만료 시간이 없습니다.");
		}
		return ttl;
	}

	public List<MyPinResponse> getMyPins(Integer userId) {
		//
		List<Pin> myPinList = pinRepository.getMyPinIsActive(userId);
		// 기준 시간
		long exireTime = TimeUnit.HOURS.toSeconds(24);
		/*
		 * 1. 내 아이디로 핀 조회 => 삭제x 살아있는 핀들 , 최근순
		 * 2. 핀 ID로 redis 조회
		 * 3. 데이터 조합
		 * */
		List<MyPinResponse> myPins = myPinList.stream()
			.filter(pin -> {
				String infoKey = pin.getId().toString() + INFO_MAKE_KEY;
				return redisTemplate.hasKey(infoKey);
			}).map(pin -> {
				String infoKey = pin.getId().toString() + INFO_MAKE_KEY;
				// String photoURL = (String)redisTemplate.opsForHash().get(infoKey, PHOTO_HASH_KEY);
				String getRedisURL = (String)redisTemplate.opsForHash().get(infoKey, PHOTO_HASH_KEY);
				String photoURL = s3FileUploadService.getURL(getRedisURL);


				long ttl = getTTL(infoKey);

				long hoursElapsed = TimeUnit.SECONDS.toHours(exireTime - ttl);
				String elapsedTime = "";

				if (hoursElapsed < 1) {
					long minutesElapsed = TimeUnit.SECONDS.toMinutes(exireTime - ttl);
					elapsedTime = minutesElapsed + "분";
				} else {
					elapsedTime = hoursElapsed + "시간";
				}

				return MyPinResponse.builder()
					.pinId(pin.getId())
					.photoURL(photoURL)
					.elapsedTime(elapsedTime)
					.build();

			}).toList();

		// TODO 마이핀 조회 테스트 해보기

		return myPins;
	}

	private String getPinInfo(String infoKey, String hashKey) {
		Object pinInfo = redisTemplate.opsForHash().get(infoKey, hashKey);
		if (pinInfo != null) {
			return pinInfo.toString();
		}
		throw new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST);
	}

	public Flux<String> keysByPattern(String pattern) {
		return reactiveRedisTemplate.keys(pattern);
	}

	private Set<String> findKeysByPattern(String pattern) {
		ScanOptions scanOptions = ScanOptions.scanOptions()
			.match(pattern)
			.build();
		Set<String> keys = new HashSet<>();

		redisTemplate.execute((RedisConnection connection) -> {
			try (Cursor<byte[]> cursor = connection.scan(scanOptions)) {
				while (cursor.hasNext()) {
					keys.add(new String(cursor.next()));
				}
				return null;
			}
		});
		return keys;
	}

	@Transactional
	public void removePin(Long pinId) {
		Pin pin = pinRepository.findById(pinId)
			.orElseThrow(() -> new BusinessExceptionHandler(ErrorCode.DATA_NOT_EXIST));

		pin.deleteData();
		// TODO photo DB에도 삭제처리하기
		redisTemplate.delete(pinId.toString());
	}

	// private Integer getCodeIdByContent() {
	public Integer getCodeIdByContent() {
		// Content 는 고정된 값 => DB에 저장되고 관리됨
		CodeResponse data = codeClient.getCode("Content").getData();
		return data.getCodeId();
	}
}
