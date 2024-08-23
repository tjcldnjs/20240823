package com.tenco.bank.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Todo {

	private List<item> item;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class item {
		private String title;
		private String link;
		private String author;
		private String pubDate;
		private String description;
		private String isbn;
		private String isbn13;
		private double priceSales;
		private double priceStandard;
		private String mallType;
		private String stockStatus;
		private int mileage;
		private String cover;
		private String categoryId;
		private String categoryName;
		private String publisher;
		private int salesPoint;
		private boolean adult;
		private boolean fixedPrice;
		private int customerReviewRank;
	}
}