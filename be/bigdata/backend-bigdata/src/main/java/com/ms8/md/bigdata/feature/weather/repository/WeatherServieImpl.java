package com.ms8.md.bigdata.feature.weather.repository;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ms8.md.bigdata.feature.weather.dto.response.ResponseAddressDto;
import com.ms8.md.bigdata.feature.weather.dto.response.ResponseConditionDto;
import com.ms8.md.bigdata.feature.weather.dto.response.ResponseDustDto;
import com.ms8.md.bigdata.feature.weather.utils.AirQualityClient;
import com.ms8.md.bigdata.feature.weather.utils.ConditionClient;
import com.ms8.md.bigdata.feature.weather.utils.KakaoLocalApiClient;
import com.ms8.md.bigdata.global.common.code.ErrorCode;
import com.ms8.md.bigdata.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherServieImpl implements WeatherService{

	private final ConditionClient conditionClient;
	private final KakaoLocalApiClient kakaoLocalApiClient;
	private final AirQualityClient airQualityClient;
	private static final String CONDITION_API_KEY = "e18a980ff119920b7d48eee9479f80c1";
	private static final String KAKAO_LOCAL_API_KEY = "KakaoAK 768fac49f26e49b39ca9b6c6549a11b1";
	private static final String AIR_QUALITY_API_KEY = "2i4yvqb6mCGDvHqz6FSbkJe%2BrOY1SaKkoAKyTS7uo%2FnZsm%2By1xgLCU%2FSvFeMF6b7CsF75aemoCXbKCH3xWJwJg%3D%3D";

	@Override
	public ResponseConditionDto getCondition(Double lat, Double lon) {
		String conditionApiResult = conditionClient.getCondition(lat, lon, "kr", CONDITION_API_KEY, "metric");
		JSONObject obj = new JSONObject(conditionApiResult);
		JSONArray weatherArray = obj.getJSONArray("weather");
		String description = weatherArray.getJSONObject(0).getString("description");
		String icon = weatherArray.getJSONObject(0).getString("icon");
		double temp = obj.getJSONObject("main").getDouble("temp");

		return ResponseConditionDto.builder()
			.description(description)
			.temp(temp)
			.icon(icon).build();
	}

	@Override
	public ResponseDustDto getDust(Double lat, Double lon) {
		try {
			String regionCode = kakaoLocalApiClient.getRegionCode(lon, lat, KAKAO_LOCAL_API_KEY);
			JSONObject obj = new JSONObject(regionCode);
			JSONArray documents = obj.getJSONArray("documents");
			String region2DepthName = null;
			if (documents.length() > 0) {
				JSONObject firstDocument = documents.getJSONObject(0);
				region2DepthName = firstDocument.getString("region_2depth_name");
			}else{
				throw new BusinessExceptionHandler("미세먼지 농도를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_ERROR);
			}

			String airQuality = null;
			if(region2DepthName != null){
				airQuality = airQualityClient.getAirQuality(AIR_QUALITY_API_KEY, region2DepthName, "DAILY", "json");
			}else{
				throw new BusinessExceptionHandler("미세먼지 농도를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_ERROR);
			}

			if(airQuality != null) {
				JSONObject jsonObj = new JSONObject(airQuality);
				JSONObject responseBody = jsonObj.getJSONObject("response").getJSONObject("body");
				JSONArray items = responseBody.getJSONArray("items");
				if (items.length() > 0) {
					for (int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);
						if (!item.isNull("pm10Grade")) {
							String pm10Grade = item.getString("pm10Grade");
							return ResponseDustDto.builder().grade(Integer.parseInt(pm10Grade)).build();
						}
					}
				}else{
					throw new BusinessExceptionHandler("미세먼지 농도를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_ERROR);
				}
			}
		} catch (Exception e){
			e.printStackTrace();
			throw new BusinessExceptionHandler("처리 중 에러가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
		}

		throw new BusinessExceptionHandler("미세먼지 농도를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_ERROR);
	}

	@Override
	public ResponseAddressDto getAddress(Double lat, Double lon) {
		try {
			String regionCode = kakaoLocalApiClient.getRegionCode(lon, lat, KAKAO_LOCAL_API_KEY);
			JSONObject obj = new JSONObject(regionCode);
			JSONArray documents = obj.getJSONArray("documents");
			String region1DepthName = null;
			String region2DepthName = null;
			if (documents.length() > 0) {
				JSONObject firstDocument = documents.getJSONObject(0);
				region1DepthName = firstDocument.getString("region_1depth_name");
				region2DepthName = firstDocument.getString("region_2depth_name");
			}else{
				throw new BusinessExceptionHandler("주소를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_ERROR);
			}

			return ResponseAddressDto.builder().si(region1DepthName).gu(region2DepthName).build();
		} catch (Exception e){
			e.printStackTrace();
			throw new BusinessExceptionHandler("처리 중 에러가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
