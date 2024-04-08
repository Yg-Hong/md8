package com.ms8.md.user.feature.user.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.util.StringUtils;

import com.ms8.md.user.feature.code.entity.DetailCode;
import com.ms8.md.user.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@DynamicInsert
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("회원ID")
	private Integer id;

	@Comment("카카오이메일ID")
	@Column(length = 100)
	private String email;

	@Comment("닉네임")
	@Column(length = 30)
	private String nickName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "age_code")
	@Comment("나이코드")
	private DetailCode ageCode;

	@Comment("성별")
	private Character gender;

	@Comment("레벨")
	@ColumnDefault("1")
	private Integer level;

	@Comment("총산책거리")
	@ColumnDefault("0")
	private Integer totalDistance;

	@Comment("프로필")
	private String profile;

	@Comment("탈퇴여부")
	@ColumnDefault("false")
	private boolean isWithdrawal;

	@Comment("산책선호시간")
	private Integer time;

	@Comment("산책선호시간")
	private Integer distance;

	@Builder
	private User(String email, String nickName, DetailCode ageCode, Character gender, Integer level,
		Integer totalDistance, String profile, boolean isWithdrawal, Integer time, Integer distance) {
		this.email = email;
		this.nickName = nickName;
		this.ageCode = ageCode;
		this.gender = gender;
		this.level = level;
		this.totalDistance = totalDistance;
		this.profile = profile;
		this.isWithdrawal = isWithdrawal;
		this.time = time;
		this.distance = distance;
	}

	public void updateMyInfo(String nickName, Character gender, Integer time, Integer distance) {
		if (StringUtils.hasText(nickName)) {
			this.nickName = nickName;
		}
		if (gender != null) {
			this.gender = gender;
		}
		if (time != null) {
			this.time = time;
		}
		if (distance != null) {
			this.distance = distance;
		}
	}

	public void updateAgeCode(DetailCode ageCode) {
		if (ageCode != null) {
			this.ageCode = ageCode;
		}
	}

	public void updateProfile(String profile) {
		if (StringUtils.hasText(profile)) {
			this.profile = profile;
		}
	}

	/* 레벨 등급 1- 낮 : 0-10km
			등급 2 :  -50
			등급 3: -100
			등급 4- 높 -200 */

	/**
	 * 레벨별 거리 설명
	 * 1: 0-10km
	 * 2: 10-50km
	 * 3: 50-100km
	 * 4: 100km 이상
	 */
	public void updateLevel() {
		double distanceKM = (double)totalDistance / 1000;

		if (distanceKM >= 100) {
			this.level = 4;
		} else if (distanceKM >= 50) {
			this.level = 3;
		} else if (distanceKM >= 10) {
			this.level = 2;
		} else {
			this.level = 1;
		}
	}

	public void updateTotalDistance(Integer totalDistance) {
		this.totalDistance += totalDistance;
	}
}
