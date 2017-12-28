/*
 * Copyright (C) 2006-2014
 * 版权所有者为北京捷越联合信息咨询有限公司。本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的。
 * @title: BaseController.java
 * @package com.jy.core.web.base
 * @author
 * @date 2014-9-17 下午3:48:55
 * @version v1.00
 * @description: 业务Controller层基础类
 */

package com.jy.platform.restservice.web.base;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.tools.common.DataSourceSwitch;

/**
 * @classname: BaseController
 * @description: 所有Controller必需继承此类并实现execute方法
 */

public abstract class BaseController implements IExecutor {
	private final static String VALIDATION_MESSAGE = "V_MESSAGE";
    private final static String VIEW_RECALL_URI = "V_RecallURI";
    private final static String VIEW_ERROR = "error";

    protected static Logger logger;

    private final static Locale DEFAULT_LOCALE = new Locale("zh", "CN");

    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private SessionAPI sessionAPI;

    /**
     * <p>
     * title:
     * </p>
     * <p>
     * description:
     * </p>
     */

    public BaseController() {
        logger = LoggerFactory.getLogger(this.getClass());
    }
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
        binder.registerCustomEditor(Timestamp.class, new CustomTimestampEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));  
        binder.registerCustomEditor(int.class, new IntegerEditor());  
        binder.registerCustomEditor(long.class, new LongEditor());  
        binder.registerCustomEditor(double.class, new DoubleEditor());  
        binder.registerCustomEditor(float.class, new FloatEditor());  
        
        binder.registerCustomEditor(Integer.class, new IntegerEditor());  
        binder.registerCustomEditor(Long.class, new LongEditor());  
        binder.registerCustomEditor(Double.class, new DoubleEditor());  
        binder.registerCustomEditor(Float.class, new FloatEditor()); 
        
        binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditor());
    } 
    /* (非 Javadoc)
    * <p>title: multiExecute</p>
    * <p>description: </p>
    * @param formDatas
    * @return
    * @throws AbaboonException
    * @see com.jy.core.web.base.IExecutor#multiExecute(java.lang.Object)
    */

    @Override
    public <T> ModelAndView multiExecute(T formDatas) throws AbaboonException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (非 Javadoc)
    * <p>title: multiExecute</p>
    * <p>description: </p>
    * @param firstData
    * @param secendData
    * @return
    * @throws AbaboonException
    * @see com.jy.core.web.base.IExecutor#multiExecute(java.lang.Object, java.lang.Object)
    */

    @Override
    public <T> ModelAndView multiExecute(T firstData, T secendData) throws AbaboonException {
        // TODO Auto-generated method stub
        return null;
    }

    /* (非 Javadoc)
    * <p>title: multiExecute</p>
    * <p>description: </p>
    * @param firstData
    * @param secendData
    * @param thirdData
    * @return
    * @throws AbaboonException
    * @see com.jy.core.web.base.IExecutor#multiExecute(java.lang.Object, java.lang.Object, java.lang.Object)
    */

    @Override
    public <T> ModelAndView multiExecute(T firstData, T secendData, T thirdData) throws AbaboonException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 获取国际化信息，缺省获取中文信息
     * 
     * @title: getMessage
     * @description:
     * @date 2014-9-17 下午4:03:07
     * @param messageCode
     * @return
     * @throws
     */
    public String getMessage(String messageCode) {
        return this.getMessage(messageCode, DEFAULT_LOCALE);
    }

    /**
     * 获取国际化信息
     * 
     * @title: getMessage
     * @description:
     * @date 2014-9-17 下午4:03:47
     * @param messageCode
     * @param locale
     * @return
     * @throws
     */
    public String getMessage(String messageCode, Locale locale) {
        return this.getMessage(messageCode, null, locale);
    }

    /**
     * 根据参数获取国际化信息 信息在属性文件中的定义，例如： uc.validation.1002=test {0} error {1}
     * ,args为0、1俩个动态替换的参数
     * 
     * @title: getMessage
     * @description:
     * @date 2014-9-17 下午4:04:00
     * @param messageCode
     * @param args
     * @return
     * @throws
     */
    public String getMessage(String messageCode, Object[] args) {
        return this.getMessage(messageCode, args, DEFAULT_LOCALE);
    }

    /**
     * 获取国际化信息
     * 
     * @title: getMessage
     * @description:
     * @date 2014-9-17 下午4:06:11
     * @param messageCode
     * @param args
     * @param locale
     * @return
     * @throws
     */
    public String getMessage(String messageCode, Object[] args, Locale locale) {
        return this.messageSource.getMessage(messageCode, args, locale);
    }

    /**
     * 获取国际化信息
     * 
     * @title: getMessage
     * @description:
     * @date 2014-9-17 下午4:06:24
     * @param messageCode
     * @param args
     * @param locale
     * @param defaultMessage
     * @return
     * @throws
     */
    public String getMessage(String messageCode, Object[] args, Locale locale, String defaultMessage) {
        return this.messageSource.getMessage(messageCode, args, defaultMessage, locale);
    }

    /**
     * 
     * @title: getParameter
     * @description:
     * @date 2014-9-17 下午5:07:45
     * @param name
     * @return
     * @throws
     */
    public Object getParameter(String name) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getParameter(name);
    }

    /**
     * 
     * @title: getString
     * @description:
     * @date 2014-9-17 下午5:09:50
     * @param name
     * @return
     * @throws
     */
    public String getParameterString(String name) {
        Object obj = getParameter(name);
        return obj == null ? null : (String) obj;
    }

    /* (非 Javadoc)
    * <p>title: execute</p>
    * <p>description: </p>
    * @return
    * @throws AbaboonException
    * @see com.jy.core.web.base.IExecutor#execute()
    */

    @Override
    public ModelAndView execute() throws AbaboonException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 获取BigDecimal类型，如果数据类型为BigDecimal则返回，否则全部返回null
     * 
     * @title: getParameterBigDecimal
     * @description:
     * @date 2014-9-17 下午5:23:12
     * @param name
     * @return
     * @throws
     */
    public BigDecimal getParameterBigDecimal(String name) {
        Object obj = getParameter(name);
        return obj == null ? null : (obj instanceof BigDecimal) ? (BigDecimal) obj : null;
    }

    /**
     * 
     * @title: getParameterMap
     * @description:
     * @date 2014-9-17 下午5:07:38
     * @return
     * @throws
     */
    public Map getParameterMap() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getParameterMap();
    }

    @ModelAttribute
    public void populateModel(HttpServletRequest request) {
        request.setAttribute(VIEW_RECALL_URI, request.getRequestURL().toString());
    }
    /**
     * @title: makeJSONData
     * @description:重新组装json数据返回到前端
     * @throws
     */
    public Map<String, Object> makeJSONData(Object id){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("id", id);
        return resultMap;
    }
    /**
     * 设置dto的公共属性，用户ID和机构ID
     * @param dto
     * @return
     */
    protected BaseDTO initDto(BaseDTO dto){
        if(dto==null){
        	dto = new BaseDTO();
        }
        UserInfo userInfo = sessionAPI.getCurrentUserInfo();
        if(userInfo != null){
        	Long orgId = userInfo.getOrgId();
        	Long userId = userInfo.getUserId();
            dto.setOpUserId(userId);
            dto.setCreateUserNameExt(userInfo.getUserName());
            dto.setCreateOrgNameExt(userInfo.getOrgName());
            dto.setUserOrgId(orgId);
        }
        if(dto.getOpUserId() == null)
        	dto.setOpUserId(-1L);
        if(dto.getUserOrgId() == null)
        	dto.setUserOrgId(-1L);
        return dto;
    }
    /**
     * 初始化dataMsg
     * @param dataMsg
     * @return
     */
    protected DataMsg initDataMsg(DataMsg dataMsg){
    	 if (dataMsg == null){
    		 dataMsg = new DataMsg();
    	 }
    	return dataMsg;
    }
    /**
     * 统一处理 controller BindException 
     * @param request
     * @param response
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
	@SuppressWarnings("all")
    public ModelAndView handleInvalidRequestError(HttpServletRequest request,HttpServletResponse response,BindException ex) {
    	String errorMsg = ex.getMessage();
    	if(errorMsg == null){
    		errorMsg = "null";
    	}
    	logger.error(errorMsg,ex);
        ModelAndView view = null;
        String accHead = request.getHeader("accept");
        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            view = new ModelAndView();
            view.addObject(VALIDATION_MESSAGE, errorMsg);
            view.setViewName(VIEW_ERROR);
        } else {
            PrintWriter writer = null;
            try {
            	response.setCharacterEncoding("UTF-8");
    			response.setContentType("application/json; charset=utf-8");
                writer = response.getWriter();
                StringBuilder sb = new StringBuilder("{");
                sb.append("\"status\":\"").append(DataMsg.STATUS_FAILED).append("\"")
                .append(",\"msg\":\"").append(errorMsg.replaceAll("[\\t\\n\\r]", "").replaceAll("[\\\\]", ".").replaceAll("\"", "")).append("\"")
                .append("}");
                writer.append(sb);
                writer.flush();
            }catch (Exception e) {
                logger.error("ajax请求参数异常："+e.getMessage(), e);
            }finally {
            	//清空切换的数据源
                DataSourceSwitch.clearDataSourceType();
                if (writer != null) {
                    writer.close();
                }
            }

        }
        return view;
    }
}
