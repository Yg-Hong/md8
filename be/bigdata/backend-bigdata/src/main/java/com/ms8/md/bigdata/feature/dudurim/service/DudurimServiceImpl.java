package com.ms8.md.bigdata.feature.dudurim.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.ms8.md.bigdata.feature.dudurim.dto.common.DudurimDto;
import com.ms8.md.bigdata.feature.dudurim.dto.response.ResponseDudurimDetailDto;
import com.ms8.md.bigdata.feature.dudurim.dto.response.ResponseDudurimDto;
import com.ms8.md.bigdata.feature.dudurim.entity.Dudurim;
import com.ms8.md.bigdata.feature.dudurim.repository.DudurimRepository;
import com.ms8.md.bigdata.global.common.code.ErrorCode;
import com.ms8.md.bigdata.global.exception.BusinessExceptionHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DudurimServiceImpl implements DudurimService{
	private final DudurimRepository dudurimRepository;
	@Override
	public ResponseDudurimDto getDudurim(Double lat, Double lon) {
		List<Dudurim> nearestLocations = dudurimRepository.findNearestLocations(lat, lon);
		if(nearestLocations == null)throw new BusinessExceptionHandler("두드림길을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_ERROR);

		try {
			List<DudurimDto> result = new ArrayList<>();
			for (Dudurim nearestLocation : nearestLocations) {
				String title;
				if(nearestLocation.getTitle().equals(nearestLocation.getSubTitle())){
					title = nearestLocation.getTitle();
				}
				else{
					title = nearestLocation.getTitle() + " " + nearestLocation.getSubTitle();
				}
				DudurimDto dudurimDto = DudurimDto.builder()
					.id(nearestLocation.getId())
					.title(title)
					.congestion(nearestLocation.getCongestion())
					.address(nearestLocation.getAddress())
					.weather(nearestLocation.getWeather())
					.degree(nearestLocation.getDegree())
					.fineDust(nearestLocation.getFineDust())
					.level(nearestLocation.getLevel())
					.distance(nearestLocation.getDistance())
					.time(nearestLocation.getTime())
					.water(nearestLocation.getDrink())
					.store(nearestLocation.getConvience())
					.toilet(nearestLocation.getToilet())
					.image(nearestLocation.getImage())
					.icon(nearestLocation.getIcon())
					.build();
				result.add(dudurimDto);
			}

			return ResponseDudurimDto.builder().dudurimList(result).build();
		}catch (Exception e){
			e.printStackTrace();
			throw new BusinessExceptionHandler("서버 오류입니다.", ErrorCode.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseDudurimDetailDto getDudurimDetail(Integer ptrackingId) {
		try {
			Dudurim dudurim = dudurimRepository.findById(ptrackingId).orElseThrow();
			String title;
			if(dudurim.getTitle().equals(dudurim.getSubTitle())){
				title = dudurim.getTitle();
			}
			else{
				title = dudurim.getTitle() + " " + dudurim.getSubTitle();
			}

			ResponseDudurimDetailDto dudurimDto = ResponseDudurimDetailDto.builder()
				.id(dudurim.getId())
				.title(title)
				.congestion(dudurim.getCongestion())
				.address(dudurim.getAddress())
				.weather(dudurim.getWeather())
				.degree(dudurim.getDegree())
				.fineDust(dudurim.getFineDust())
				.level(dudurim.getLevel())
				.distance(dudurim.getDistance())
				.time(dudurim.getTime())
				.drink(dudurim.getDrink())
				.convience(dudurim.getConvience())
				.toilet(dudurim.getToilet())
				.image(dudurim.getImage())
				.keywords(dudurim.getKeywords())
				.content(dudurim.getContent())
				.image(dudurim.getImage())
				.icon(dudurim.getIcon())
				.build();
			return dudurimDto;
		}catch (NoSuchElementException e){
			throw new BusinessExceptionHandler("해당 두드림길은 없습니다.", ErrorCode.BAD_REQUEST_ERROR);
		}catch (Exception e){
			e.printStackTrace();
			throw new BusinessExceptionHandler("서버 오류입니다.", ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
