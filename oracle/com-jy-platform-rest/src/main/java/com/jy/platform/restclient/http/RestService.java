package com.jy.platform.restclient.http;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;


/**
 * 
 * @description：读取默认配置文件
 * @creater：wangxz 
 * @createrTime：2014年5月13日 下午4:10:19   
 * @changeNote：   
 * @version 
 *
 */
public class RestService {

	static Logger logger = LoggerFactory.getLogger(RestService.class);
	private static final String CONFIG_LOCATION = "auth-client.xml";
	private static Map<String,AuthVo> authMap = new HashMap<String,AuthVo>();

	/**
	 * 加载配置文件
	 */
	public static void load(){
		authMap.clear();
		try {
            parseAutoConfigXml();
        } catch (Exception e) {
            logger.error("load=====解析auth-client.xml文件出错：", e);
        }
	}
	/**
	 * 解析xml配置文件
	 * @throws IOException 
	 * @throws DocumentException 
	 */
	public static void parseAutoConfigXml() throws Exception{
	    Document d = null;
		try{
	        SAXReader reader = new SAXReader();
			ClassLoader cl=RestService.class.getClassLoader();
			URL url=cl.getResource(CONFIG_LOCATION);
	        d =  reader.read(new File(url.getFile()));
			
		}catch(Exception e){
		    ClassPathResource cp =  new ClassPathResource(CONFIG_LOCATION);
            SAXReader reader = new SAXReader();
            d = reader.read(cp.getInputStream());
		}
		
		Element root = d.getRootElement();
        Element serviceElement = root.element("authlist");
        List<Element> list = serviceElement.elements("authinfo");

        for(Element e : list){
            String appid = e.attributeValue("appid");
            String apppwd = e.attributeValue("apppwd");
            String validtime = e.attributeValue("validtime");
            String serviceurl = e.attributeValue("serviceurl");
            authMap.put(appid, new AuthVo(appid,apppwd,validtime,serviceurl));
            logger.info("appid="+appid+",apppwd="+apppwd+",validtime="+validtime+",serviceurl="+serviceurl);
        }
	}
	
	public static void checkLoad(){
		if(authMap==null||authMap.size()<=0){
			load();
		}
	}
	
	public static String getServiceUrl(String appId){
		checkLoad();
		AuthVo authVo = authMap.get(appId);
		if(authVo!=null){
			return authVo.getServiceUrl();
		}
		return "";
	}
	
	
	public static String getAppPwd(String appId){
		checkLoad();
		AuthVo authVo = authMap.get(appId);
		if(authVo!=null){
			return authVo.getAppPwd();
		}
		return "";
	}
	
//	/**
//	 * 校验授权信息
//	 * @param appId
//	 * @param appKey
//	 * @return
//	 */
//	public static boolean check(String appId, String appPwd){
//		boolean b = false;
//		if(authMap==null){
//			load();
//		}
//		AuthVo authVo = authMap.get(appId);
//		if(authVo!=null){
//			String validtimeStr = authVo.getValidTime();
//			long compare = 0;
//			try {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
//				Date validtime = sdf.parse(validtimeStr);
//				compare = validtime.getTime() - new Date().getTime();
//			} catch (ParseException e) {
//				logger.error("解析validtime出错：", e);
//			}
//			if(compare>0&&authVo.getAppPwd()!=null&&authVo.getAppPwd().equals(appPwd)){ //有效期内
//				b = true;
//			}
//		}
//		return b;
//	}
}

