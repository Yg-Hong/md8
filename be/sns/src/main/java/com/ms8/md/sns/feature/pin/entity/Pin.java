package com.ms8.md.sns.feature.pin.entity;

import org.hibernate.annotations.Comment;

import com.ms8.md.sns.global.common.entity.DeleteEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "pin")
public class Pin extends DeleteEntity {

	@Id
	@Column(name = "pin_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("핀 ID")
	private Long id;

	@Comment("회원 ID")
	private Integer userId;

	@Comment("위도")
	private Double lat;

	@Comment("경도")
	private Double lng;

	@Builder
	private Pin(Integer userId, Double lat, Double lng) {
		this.userId = userId;
		this.lat = lat;
		this.lng = lng;
	}
}
