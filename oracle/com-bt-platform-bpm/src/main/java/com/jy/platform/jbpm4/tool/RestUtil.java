package com.jy.platform.jbpm4.tool;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author JY-IT-D001
 *
 */
public class RestUtil {

	public static String doGet(String url, String param){
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
		RestTemplate rt = new RestTemplate();
		return rt.getForObject(url, String.class, param);
	}
}
