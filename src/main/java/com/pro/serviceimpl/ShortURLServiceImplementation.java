package com.pro.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.entity.RequestShortURLEntity;
import com.pro.entity.ShortURLEntity;
import com.pro.repository.ShortURLRepository;
import com.pro.service.GenerateRandomLink;
import com.pro.service.GenerateShortURLService;
import com.pro.utils.CommanConstants;
@Service
public class ShortURLServiceImplementation implements 
         GenerateShortURLService ,GenerateRandomLink {

	@Autowired
	ShortURLRepository shortURLRepository;
	
	public static int shorterLength = CommanConstants.shorterLength;
	public static SimpleDateFormat dateFormat = CommanConstants.dateFormat;
	public static String AlphaNumericString = CommanConstants.alphaNumericString;
	

	@Override
	public String generateShortURLink(RequestShortURLEntity entity) {
		String randomString = getAlphaNumericString(CommanConstants.shorterLength);
		String shortLink =CommanConstants.myServerHost + randomString;
		ShortURLEntity shortURLEntity = new ShortURLEntity();
		Date urlGeneratedDate = new Date();
		shortURLEntity.setOriginalURL(entity.getOriginalURL());
		shortURLEntity.setShortURL(shortLink);
		shortURLEntity.setUrlCreatedTime(dateFormat.format(urlGeneratedDate));
		shortURLEntity.setRandomString(randomString);
		return shortURLRepository.save(shortURLEntity).getShortURL();
	}
	
	@Override
	public String getAlphaNumericString(Integer shorterLength2) {
		StringBuilder randomString = new StringBuilder();
		for (int i = 0; i < shorterLength; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			randomString.append(AlphaNumericString.charAt(index));
		}
		return randomString.toString();
	}

}
