package com.fintech.platform.restservice.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;
import com.fintech.platform.restservice.rest.RestException;
import com.fintech.platform.tools.common.DataSourceSwitch;
import com.fintech.platform.tools.common.JacksonMapper;
/**
 * 自定义rest服务异常拦截器
 * @author
 *
 */
public class CustomExceptionHandler implements HandlerExceptionResolver {  
	
	private static Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object object, Exception ex) {
    		logger.error("Restful Service Exception: ", ex );
	    	if(ex instanceof RestException) {
	    		ResponseMsg<Void> out = new ResponseMsg<Void>();
	    		out.setRetCode(ResponseStatus.HTTP_SERVER_ERROR);
	    		out.setErrorDesc(ex.getMessage());
	    		JacksonMapper jacksonMapper = JacksonMapper.getInstance();
	    		PrintWriter writer = null;
	    		try {
	    			writer = response.getWriter();
	    			writer.write(jacksonMapper.writeValueAsString(out));
					//response.addHeader("Status Code", "500 Internal Server Error");//为什么无效？
					response.addHeader("Content-Type", "application/json;charset=UTF-8");
					
					writer.flush();
					writer.close();
					response.flushBuffer();
				} catch (Exception e) {
					logger.error("CustomExceptionHandler 请求返回异常："+e.getMessage(), e);
				}finally {
	            	//清空切换的数据源
	                DataSourceSwitch.clearDataSourceType();
	                if (writer != null) {
	                    writer.close();
	                }
	            }
			}
	    	//清空切换的数据源
	        DataSourceSwitch.clearDataSourceType();
    		return null;  
    }  
       
}  