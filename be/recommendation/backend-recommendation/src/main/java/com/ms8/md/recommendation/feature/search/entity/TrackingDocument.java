package com.ms8.md.recommendation.feature.search.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "tracking")
@Mapping(mappingPath = "elastic/tracking-mapping.json")
@Setting(settingPath = "elastic/tracking-setting.json")
public class TrackingDocument {
	@Id
	@Field(type = FieldType.Keyword)
	private Long id;

	@Field(name = "title", type=FieldType.Text)
	private String title;

	@Field(name = "content", type=FieldType.Text)
	private String content;

	@Field(name = "is_deleted", type=FieldType.Text)
	private Boolean isDeleted;
}
