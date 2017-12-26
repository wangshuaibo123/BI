package com.fintech.platform.restservice.web.base;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 
 * @description:通过 异常编码获取 异常描述信息 支持国际化
 * @author
 * @date:2015年1月4日下午2:09:59
 */
@Component("com.fintech.platform.restservice.web.base.ObtainExceptionMsgUtil")
public class ObtainExceptionMsgUtil {
	private final static String VIEW_RECALL_URI = "V_RecallURI";

    protected static Logger logger;

    private final static Locale DEFAULT_LOCALE = new Locale("zh", "CN");

    @Autowired
    private MessageSource messageSource;
    
    @ModelAttribute
    public void populateModel(HttpServletRequest request) {
        request.setAttribute(VIEW_RECALL_URI, request.getRequestURL().toString());
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

}
