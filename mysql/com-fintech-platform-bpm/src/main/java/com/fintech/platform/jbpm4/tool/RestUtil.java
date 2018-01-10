package com.fintech.platform.jbpm4.tool;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author
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
