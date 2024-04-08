package com.ms8.md.tracking.feature.coordinates.service;

import static com.ms8.md.tracking.global.common.GetAddress.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.stereotype.Service;

import com.ms8.md.tracking.feature.coordinates.dto.request.CoordinatesGetRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoordinatesServiceImpl implements CoordinatesService {

	private final GeoOperations<String, String> geoOperations;

	@Override
	public void create(Long ownerId, String ownerType, Double latitude, Double longitude) {
		Point point = new Point(latitude, longitude);
		geoOperations.add(ownerType, point, String.valueOf(ownerId));
	}

	@Override
	public List<String> getAllByCoordinates(String ownerType, CoordinatesGetRequest request) {
		Double latitude = request.getLatitude();
		Double longitude = request.getLongitude();
		Double distance = request.getDistance()/1000;
		Point point = new Point(longitude, latitude);
		Distance radius = new Distance(distance, Metrics.KILOMETERS);
		Circle circle = new Circle(point, radius);
		GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOperations.radius(ownerType, circle);
		return results == null ? new ArrayList<>() :
			results.getContent().stream().map(GeoResult::getContent).map(GeoLocation::getName).toList();
	}

	@Override
	public String getSido(Double latitude, Double longitude) {
		String url = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=" + longitude + "&y=" + latitude;
		String apiUrl;
		String sido = "";
		try {
			apiUrl = getJSONData(url);
			sido = getRegionAddress(apiUrl);
		} catch (Exception e) {
			log.error("getSido error", e);
		} return sido;
	}

}
