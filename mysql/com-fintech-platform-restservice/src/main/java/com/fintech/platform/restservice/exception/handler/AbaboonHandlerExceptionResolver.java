/**
 * 
 * @title: AbaboonHandlerExceptionResolver.java
 * @package com.pt.core.exception.handler
 * @author
 * @date 2014-9-20 下午3:22:08
 * @version v1.00
 * @description: 异常处理类
 */

package com.fintech.platform.restservice.exception.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.exception.ValidationException;
import com.fintech.platform.tools.common.DataSourceSwitch;

/**
 * @classname: AbaboonHandlerExceptionResolver
 * @description: 当controller 抛出validationException页面不跳转，其他异常跳转到公共错误页面
 */
public class AbaboonHandlerExceptionResolver extends SimpleMappingExceptionResolver {


    static Logger logger = LoggerFactory.getLogger(AbaboonHandlerExceptionResolver.class);

    private final static String VALIDATION_MESSAGE = "V_MESSAGE";

    private final static String VIEW_RECALL_URI = "V_RecallURI";

    private final static String VIEW_ERROR = "error";

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,Exception ex) {
    	String errorMsg = ex.getMessage();
    	if(errorMsg == null){
    		errorMsg = "null";
    	}
    	
    	logger.error("AbaboonHandlerExceptionResolver Exception: ", ex );
        ModelAndView view = null;
        String accHead = request.getHeader("accept");
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            view = new ModelAndView();
            view.addObject(VALIDATION_MESSAGE, errorMsg);
            if (ex instanceof ValidationException) {
                view.setViewName("redirect:" + request.getParameter(VIEW_RECALL_URI) == null ? null : (String) request
                        .getParameter(VIEW_RECALL_URI));
            } else {
                view.setViewName(VIEW_ERROR);
            }

        } else {
            PrintWriter writer = null;
            try {
            	response.setCharacterEncoding("UTF-8");
    			response.setContentType("application/json; charset=utf-8");
                writer = response.getWriter();
                StringBuilder sb = new StringBuilder("{");
                sb.append("\"status\":\"").append(DataMsg.STATUS_FAILED).append("\"")
                .append(",\"msg\":\"").append(errorMsg.replaceAll("[\\t\\n\\r]", "").replaceAll("[\\\\]", ".")).append("\"")
                .append("}");
                writer.append(sb);
                writer.flush();
            }catch (Exception e) {
                logger.error("ajax请求返回异常："+e.getMessage(), e);
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
        return view;
    }
}
