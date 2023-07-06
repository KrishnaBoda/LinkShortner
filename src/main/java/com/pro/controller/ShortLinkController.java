package com.pro.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pro.entity.RequestShortURLEntity;
import com.pro.entity.Response;
import com.pro.exceptions.InSufficientInputException;
import com.pro.serviceimpl.RedirectShortURLImpl;
import com.pro.serviceimpl.ShortURLServiceImplementation;
import com.pro.wrapper.GeneratedShortURLWrapper;

@RestController
@CrossOrigin
public class ShortLinkController {

	@Autowired
	ShortURLServiceImplementation shortURLService;
	
	@Autowired
	RedirectShortURLImpl redirectShortURLImpl;

	/**
	 * @author : Krishna Boda
	 * @throws InSufficientInputException
	 * @URL : http://localhost:8080/generate-Short-url
	 * @Inputs : { "originalURL" : "www.google.com" }
	 */

	@PostMapping(value = "/generate-Short-url",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public Response generateShortLink(@RequestBody RequestShortURLEntity entity) throws InSufficientInputException {
		if(entity.getOriginalURL() == null) {
			throw new InSufficientInputException("");
		}else {
			GeneratedShortURLWrapper response = new GeneratedShortURLWrapper();
			String generatedShortURL = shortURLService.generateShortURLink(entity);
			response.setResponseCode(org.springframework.http.HttpStatus.OK.value());
			response.setStatus(org.springframework.http.HttpStatus.OK.getReasonPhrase());
			response.setGeneratedShortURL(generatedShortURL);
			return response;
		}
	}

	/**
	 * @author : Krishna Boda
	 * @throws ParseException 
	 * @Des : getURL
	 * @URL : http://localhost:8080/S68vSAghs
	 */

	@GetMapping(value = "/{randomString}" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> redirectShorter(@PathVariable("randomString") String randomString)
			throws InSufficientInputException, ParseException  {
		if (randomString == null) {
			throw new InSufficientInputException("");
		} else {
			ResponseEntity<String> response = redirectShortURLImpl.redirectShortURL(randomString);
			return response;
		}
	}

}
