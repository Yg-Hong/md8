package com.ms8.md.bigdata.feature.dudurim.entity;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;

@Getter
@Entity
@Table(schema = "md", name = "dudurim")
public class Dudurim {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "ptracking_id")
	private Integer id;
	private String title;
	private String subTitle;
	private String route;
	private String address;
	private String level;
	private Double distance;
	private String content;
	private String time;
	private Double lat;
	private Double lon;
	private Boolean drink;
	private Boolean toilet;
	private Boolean convience;
	private String keywords;
	private Integer congestion;
	private String weather;
	private String fineDust;
	private String degree;
	private String image;
	private String icon;

	private Point geom;
}
