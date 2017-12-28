/**
 * Copyright (C) 2006-2014
 * 版权所有者为北京捷越联合信息咨询有限公司。本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: IExecutor.java
 * @package com.jy.core.web.base
 * @author
 * @date 2014-9-17 下午4:32:03
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package com.jy.platform.restservice.web.base;

import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.restservice.exception.AbaboonException;

/**
 * @classname: IExecutor
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public interface IExecutor {

    public ModelAndView execute() throws AbaboonException;

    public <T> ModelAndView multiExecute(T formDatas) throws AbaboonException;

    public <T> ModelAndView multiExecute(T firstData, T secendData) throws AbaboonException;

    public <T> ModelAndView multiExecute(T firstData, T secendData, T thirdData) throws AbaboonException;

}
