package com.pro.utils;

import java.text.SimpleDateFormat;

public interface CommanConstants {
	
	public static int shorterLength = 9;
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
	public static String myHost = "https://www.google.com/";
	public static String generateShortURLHost = "http://localhost:3000/generateNewShortURL";
	public static String expiredURLHost = "http://localhost:3000/expiredURL/";
	public static String myServerHost = "http://localhost:8080/";

}
