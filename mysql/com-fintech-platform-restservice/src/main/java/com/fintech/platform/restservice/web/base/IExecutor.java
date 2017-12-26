/**
 * 
 * @title: IExecutor.java
 * @package com.pt.core.web.base
 * @author
 * @date 2014-9-17 下午4:32:03
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package com.fintech.platform.restservice.web.base;

import org.springframework.web.servlet.ModelAndView;

import com.fintech.platform.restservice.exception.AbaboonException;

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
