package com.fintech.platform.restservice.cfg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
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
 * @creater：cg 
 * @createrTime：2014年5月13日 下午4:10:19   
 * @changeNote：   
 * @version 
 *
 */
public class AuthConfig {

	static Logger logger = LoggerFactory.getLogger(AuthConfig.class);
	private static final String CONFIG_LOCATION = "auth-service.xml";
	private static Map<String,AuthVo> authMap = null;

	/**
	 * 加载配置文件
	 */
	public static void load(){
		authMap = new HashMap<String,AuthVo>();
		try {
			parseAutoConfigXml();
		} catch (Exception e) {
			logger.error("==load====解析auth-service.xml文件出错：", e);
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
			ClassLoader cl=AuthConfig.class.getClassLoader();
			URL url=cl.getResource(CONFIG_LOCATION);
	        d =  reader.read(new File(url.getFile()));
		}catch(Exception e1){
			
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
			authMap.put(appid, new AuthVo(appid,apppwd,validtime));
			logger.info("appid="+appid+",apppwd="+apppwd+",validtime="+validtime);
		}
	}
	/**
	 * 校验授权信息
	 * @param appId
	 * @param appKey
	 * @return
	 */
	public static boolean check(String appId, String appPwd){
		boolean b = false;
		if(authMap==null){
			load();
		}
		AuthVo authVo = authMap.get(appId);
		if(authVo!=null){
			String validtimeStr = authVo.getValidTime();
			long compare = 0;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
				Date validtime = sdf.parse(validtimeStr);
				compare = validtime.getTime() - new Date().getTime();
			} catch (ParseException e) {
				logger.error("解析validtime出错：", e);
			}
			if(compare>0&&authVo.getAppPwd()!=null&&authVo.getAppPwd().equals(appPwd)){ //有效期内
				b = true;
			}
		}
		return b;
	}
	
	
	/**Description: 获得  authorization中的 appid  authorization通常的格式是  appId:appwd
	 * Create Date: 2015年1月23日下午4:02:15<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param authorization
	 * @return
	 */
	public static String  getAppId(String authorization){
		String appId= null;
		if(authorization!=null&&!authorization.equals("")){
			  String idPwd = new String(Base64.decodeBase64(authorization));
			  appId=idPwd.split(":")[0];
		}
		
		return appId;
	}
	
	public static void main(String[] args) {
		String authorization ="cHRwdDp4eGE=";
		 String idPwd = new String(Base64.decodeBase64(authorization));
		 System.out.println("========idPwd:"+idPwd);
	}
	
}
