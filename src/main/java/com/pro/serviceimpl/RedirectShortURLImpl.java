package com.pro.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLHandshakeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pro.entity.MessageEntity;
import com.pro.entity.ShortURLEntity;
import com.pro.repository.ShortURLRepository;
import com.pro.service.RedirectShortURLService;
import com.pro.utils.CommanConstants;
import com.pro.utils.SendNotificationToUser;

@Service
public class RedirectShortURLImpl implements RedirectShortURLService {
	
	@Autowired
	ShortURLRepository shortURLRepository;
	
	public static SimpleDateFormat dateFormat = CommanConstants.dateFormat;

	@Override
	public ResponseEntity<String> redirectShortURL(String randomString) throws ParseException {
		List<ShortURLEntity> urlExitOrNot = shortURLRepository.findURLExist(randomString);
		if(urlExitOrNot.size() > 0) {
			Date currentDateTime = new Date();
			String urlGeneratedDate = urlExitOrNot.get(0).getUrlCreatedTime();
		    Date parsedUrlGeneratedDate  = dateFormat.parse(urlGeneratedDate);   
			long duration  = currentDateTime.getTime() - parsedUrlGeneratedDate.getTime();
			long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
			if(diffInMinutes > 5) {
				HttpHeaders headers = new HttpHeaders();
	            headers.add("Location",CommanConstants. expiredURLHost);
	            MessageEntity messagEntity = new MessageEntity();
	            messagEntity.setStatusCode(HttpStatus.BAD_REQUEST.value());
	            SendNotificationToUser sendNotificationToUser = new SendNotificationToUser();
	            try {
					sendNotificationToUser.sendNotification(messagEntity);
				} catch (SSLHandshakeException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
			}else {
				HttpHeaders headers = new HttpHeaders();
	            headers.add("Location",CommanConstants. myHost);
	            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
			}

		}else {
			HttpHeaders headers = new HttpHeaders();
            headers.add("Location",CommanConstants. generateShortURLHost);
            MessageEntity messagEntity = new MessageEntity();
            messagEntity.setStatusCode(HttpStatus.NOT_FOUND.value());
            SendNotificationToUser sendNotificationToUser = new SendNotificationToUser();
            try {
				sendNotificationToUser.sendNotification(messagEntity);
			} catch (SSLHandshakeException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
		}
	}

}
