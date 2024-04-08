package com.ms8.md.user.global.common.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AutoCloseable.class)
public class DeleteEntity extends BaseTimeEntity{
	@Column(name = "is_deleted")
	@ColumnDefault("false")
	@Comment("삭제여부")
	private boolean isDeleted;

	public void deleteData() {
		this.isDeleted = true;
	}
}
