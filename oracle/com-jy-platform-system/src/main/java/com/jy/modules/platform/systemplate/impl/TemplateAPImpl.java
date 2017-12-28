package com.jy.modules.platform.systemplate.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Blob;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jy.modules.platform.systemplate.dto.SysTemplateDTO;
import com.jy.modules.platform.systemplate.service.SysTemplateService;
import com.jy.platform.api.systemplate.TemplateAPI;
import com.jy.platform.core.message.QueryReqBean;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;


/**<pre>
 * 类名中文描述:
 *
 * 基本操作功能:
 *
 * Module ID  : com-jy-platform-system 
 *
 * Create Date：2014年10月28日 下午5:36:09
 * 
 * CopyRight  :  Copyright(C) 2014-xxxx  捷越联合 <br/>
 * 
 * @since 0.1
 * @version: 0.1
 * @author <a href="mailto:chengyangyu@jieyuechina.com">cyy</a>
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2014年10月28日 | 0.1     | cyy| CREATE THE JAVA FILE: TemplateAPImpl.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
@Component
public class TemplateAPImpl implements TemplateAPI {
	
    private static final Logger logger =  LoggerFactory.getLogger(TemplateAPImpl.class);
//	private String jyptAppId = "jypt"; //rest服务appId
//	private String jyptURL = RestService.getServiceUrl(jyptAppId);//rest服务地址
	
	Configuration cfg = new Configuration();
	StringTemplateLoader stringLoader = new StringTemplateLoader();
	
	@Autowired
    @Qualifier("com.jy.modules.platform.systemplate.service.SysTemplateService")
    private SysTemplateService sysTemplateService;
	
//	@Override
//	public String getContent(String templateNo, Map<String, Object> param) {
//    	Map<String, Object> searchParams = new HashMap<String, Object>();
//    	SysTemplateDTO paramDto = new SysTemplateDTO();
//    	paramDto.setTemplateNo(templateNo);
//        searchParams.put("dto",paramDto);
//        QueryReqBean params = new QueryReqBean();
//        params.setSearchParams(searchParams);
//		String resultStr = null;
//		try {
//			String url= jyptURL+"/api/platform/systemplate/SysTemplateRest/getTemplateContentByNo/v1";
//			ResponseMsg<String> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<String>>(){});
//			String content = responseMsg.getResponseBody();
//            stringLoader.putTemplate(templateNo, content);
//            cfg.setTemplateLoader(stringLoader);
//            	Template temp = cfg.getTemplate(templateNo,"UTF-8");
//            	Writer out = new StringWriter(2048);
//            	temp.process(param, out);
//            	resultStr = out.toString();
//		} catch (Exception e) {
//        	logger.error("执行方法queryListSysUser异常：", e);
//		}
//		return resultStr;
//	}
	
	
	@Override
	public String getContent(String templateNo, Map<String, Object> param) {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	SysTemplateDTO paramDto = new SysTemplateDTO();
    	paramDto.setTemplateNo(templateNo);
        searchParams.put("dto",paramDto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
		String resultStr = null;
		ByteArrayOutputStream baos  = null;
		
		try {
			List<SysTemplateDTO> list = sysTemplateService.searchSysTemplate(params.getSearchParams());
			Blob blobContent = (Blob) list.get(0).getTemplateContent();
			InputStream  ins = blobContent.getBinaryStream();
			baos = new ByteArrayOutputStream();
			int i = -1;
			while ((i= ins.read()) != -1) {
				baos.write(i);
			}
			String content =  baos.toString("UTF-8");
			stringLoader.putTemplate(templateNo, content);
            cfg.setTemplateLoader(stringLoader);
        	Template temp = cfg.getTemplate(templateNo,"UTF-8");
        	Writer out = new StringWriter(2048);
        	temp.process(param, out);
        	resultStr = out.toString();
		} 
		catch(Exception e){
        	logger.error("执行方法TemplateAPImpl.getContent()异常：", e);
		}
		finally{
			if(baos!=null){
				try{
					baos.close();//关闭
				}
				catch(Exception e){
					logger.error("TemplateAPImpl.getContent() ByteArrayOutputStream.close()异常", e);
				}
			}
		}
		
		return resultStr;
	}

}
