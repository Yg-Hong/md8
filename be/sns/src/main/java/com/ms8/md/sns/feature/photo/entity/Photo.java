package com.ms8.md.sns.feature.photo.entity;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

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
@DynamicInsert
@Table(name = "photo")
public class Photo extends DeleteEntity {

	@Id
	@Column(name = "photo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("사진ID")
	private Integer id;

	@Comment("컨텐츠유형코드")
	private Integer contentCode;

	@Comment("컨텐츠ID")
	private Long contentId;

	@Comment("파일명")
	private String fileName;


	@Builder
	private Photo(Integer contentCode, Long contentId, String fileName) {
		this.contentCode = contentCode;
		this.contentId = contentId;
		this.fileName = fileName;
	}
}
