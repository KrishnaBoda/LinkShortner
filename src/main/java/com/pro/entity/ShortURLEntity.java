package com.pro.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "LinkShorter")
public class ShortURLEntity {
	
	@Id
	private String id;
	private String originalURL;
	private String shortURL;
	private String urlCreatedTime;
	private String randomString;
	

}
