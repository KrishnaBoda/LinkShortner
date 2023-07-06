package com.pro.service;

import java.text.ParseException;

import org.springframework.http.ResponseEntity;

public interface RedirectShortURLService {
	
	public ResponseEntity<String> redirectShortURL(String randomString) throws ParseException;


}
