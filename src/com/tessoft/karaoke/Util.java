package com.tessoft.karaoke;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Util {

	public  static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(Exception e)
	    {
	        e.printStackTrace();
	    }
	    return sha1;
	}
	
	public static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	public static boolean isEmptyString( String str )
	{
		if ( str == null ) return true;
		if ( "null".equals( str.toLowerCase() ) ) return true;
		if ( "".equals(str.trim())) return true;
		if (str.trim().length() == 0) return true;
		return false;
	}
	
	public static boolean isEmptyString( Object obj )
	{
		if ( obj == null ) return true;
		if ( "".equals( obj.toString().trim())) return true;
		return false;
	}
	
	public static boolean isEmptyForKey( Map hash, String key )
	{
		if ( hash == null ) return true;
		
		if ( !hash.containsKey(key) || Util.isEmptyString(hash.get(key)) ) return true;
		
		return false;
	}
	
	public static String getStringFromHash( Map hash, String key )
	{
		if ( Util.isEmptyForKey(hash, key) ) return "";
		
		return hash.get(key).toString();
	}
	
	public static int getInt( Object val )
	{
		if ( isEmptyString( val ) ) return 0;
		
		try
		{
			return Integer.parseInt( val.toString() );	
		}
		catch( Exception ex )
		{
			return 0;
		}
	}
	
	public static double getDouble( String val )
	{
		if ( isEmptyString( val ) ) return 0.0;
		
		try
		{
			return Double.parseDouble( val );	
		}
		catch( Exception ex )
		{
			return 0.0;
		}
	}
	
	public static String getNumberWithComma( String num )
	{
		double amount = Double.parseDouble(num);
		DecimalFormat formatter = new DecimalFormat("#,###");
		return formatter.format(amount);
	}
	
	public static String getString( Object obj )
	{
		if ( Util.isEmptyString(obj) ) return "";
		else return obj.toString();
	}
	
	public static Date getDateFromString( String dateString, String format ) throws Exception
	{
		if ( dateString == null || "".equals( dateString ) ) return null;

		DateFormat sdf = new SimpleDateFormat( format );
		Date date = sdf.parse(dateString);
		return date;
	}
	
	public static String getDateStringFromDate( Date date, String format )
	{
		if ( date == null ) return "";

		DateFormat sdf = new SimpleDateFormat(format);
		String tempDate = sdf.format(date);
		return tempDate;
	}
	
	public static String getNow( String format )
	{
		Date d = new Date();
		return getDateStringFromDate(d, format);
	}
	
	public static String getMonth( Date d )
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int month = cal.get(Calendar.MONTH);
		return String.valueOf(month);
	}
	
	public static String getDate( Date d )
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int month = cal.get(Calendar.DATE);
		return String.valueOf(month);
	}
	
	/**
	 * 특정 날짜에 대하여 요일을 구함(일 ~ 토)
	 * @param date
	 * @param dateType
	 * @return
	 * @throws Exception
	 */
	public static String getDateDay( Date d ) throws Exception {
	    String day = "" ;
	     
	    Calendar cal = Calendar.getInstance() ;
	    cal.setTime(d);
	     
	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
	     
	    switch(dayNum){
	        case 1:
	            day = "일";
	            break ;
	        case 2:
	            day = "월";
	            break ;
	        case 3:
	            day = "화";
	            break ;
	        case 4:
	            day = "수";
	            break ;
	        case 5:
	            day = "목";
	            break ;
	        case 6:
	            day = "금";
	            break ;
	        case 7:
	            day = "토";
	            break ;
	             
	    }
	     
	    return day ;
	}
	
	public static String getRandomSeed()
	{
		Random rnd = new Random();
		int n = 100000 + rnd.nextInt(900000);
		return String.valueOf( n );
	}
	
	public static String getShaHashString( String stringToEncrypt ) throws Exception
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(stringToEncrypt.getBytes());
		byte[] data = Base64.encodeBase64( messageDigest.digest() );
		return new String( data );
	}
	
	public static String getSha1HashString( String stringToEncrypt ) throws Exception
	{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		messageDigest.update(stringToEncrypt.getBytes());
		byte[] data = Base64.encodeBase64( messageDigest.digest() );
		return new String( data );
	}
	
}
