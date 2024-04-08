package com.ms8.md.user.feature.code.entity;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

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
import com.ms8.md.user.global.common.entity.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@DynamicInsert
@Table(name = "code")
public class Code extends BaseTimeEntity {

	@Id
	@Column(name = "code_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("코드ID")
	private Integer codeId;

	@Column(name = "name", length = 60, unique = true)
	@Comment("코드명")
	private String name;

	@Column(name = "description")
	@Comment("코드설명")
	private String description;

	@Builder
	private Code(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void updateCode(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
