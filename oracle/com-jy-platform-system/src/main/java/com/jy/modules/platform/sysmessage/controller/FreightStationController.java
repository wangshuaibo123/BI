package com.jy.modules.platform.sysmessage.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.platform.api.sysconfig.SysConfigAPI;
import com.jy.platform.restclient.http.HttpFactory;
import com.jy.platform.restservice.web.base.BaseController;
/**
 * @description: 提供通过本地服务调用远程集中站内消息服务
 * @author chen_gang
 * @date: 2016年1月6日 下午1:56:59
 */
@Controller
@Scope("prototype")
@RequestMapping("/freightStation")
public class FreightStationController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(SysMessageController.class);

	@Autowired
	private SysConfigAPI sysConfig;

	@RequestMapping(value = "/doit")
    @ResponseBody
    public void doit(HttpServletRequest request,HttpServletResponse response) {
    	
		//获取推送根路径
		String MSG_PUSH_URL = sysConfig.getValue("MSG_PUSH_WEBROOT");
		Map<String,String[]> paramsMap = request.getParameterMap();
		StringBuffer params = new StringBuffer("?");
		int i=0;
		for(String key:paramsMap.keySet()){
			if(!"msg".equals(key)){
				params.append(key+"="+paramsMap.get(key)[0]);
			}else{
				try{
					params.append(key+"="+URLEncoder.encode(paramsMap.get(key)[0],"UTF-8"));
				}catch(Exception e){
					logger.error("消息发布内容转码异常, error:"+e);
				}
			}
			i++;
			if(i<paramsMap.size()){
				params.append("&");
			}
		}
		MSG_PUSH_URL = MSG_PUSH_URL+"/pushlet.srv"+params.toString();
		String jyptAppId = "jypt"; // rest服务appId
		
		try {
			org.apache.http.client.methods.HttpUriRequest httpUriRequest = 
					HttpFactory.getInstance().buildRequest(jyptAppId, MSG_PUSH_URL, "GET", "");
				
			CloseableHttpClient httpClient = HttpFactory.getInstance().getHttpClient();
			CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest);
			int statusCode = 200;
			statusCode = httpResponse.getStatusLine().getStatusCode();
			String result = "";
			if (statusCode == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
				logger.info("pushlet do request with freightStation sucess, response:"+result);
			}
			response.setContentType("text/xml;charset=UTF-8");
			ServletOutputStream out = response.getOutputStream();
			out.print(result);
			out.flush();
		}catch(Exception e){
			logger.error("pushlet do request with freightStation has something wroing, error:"+e);
		}
	}
}
