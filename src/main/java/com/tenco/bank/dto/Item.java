package com.tenco.bank.dto;

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
public class Item {
	private String title;
	private String author;
	private String pubDate;
	private String description;
	private String cover;
	private String categoryName;
	private String publisher;
}
