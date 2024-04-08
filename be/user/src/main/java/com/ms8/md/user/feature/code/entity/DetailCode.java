package com.ms8.md.user.feature.code.entity;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

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

import com.ms8.md.user.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@ToString(exclude = "code")
@Table(name = "detail_code")
public class DetailCode extends BaseTimeEntity {
	@Id
	@Column(name = "detail_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("상세코드ID")
	private Integer detailId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "code_id")
	@Comment("코드ID")
	private Code code;

	@Column(name = "name", length = 60)
	@Comment("상세코드명")
	private String name;

	@Column(name = "description")
	@Comment("상세코드설명")
	private String description;

	@Builder
	private DetailCode(Code code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	public void update(String name, String description) {
		this.name = name;
		this.description = description;
	}
}



