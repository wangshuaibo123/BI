
/**
* Copyright (C) 2006-2014 版权所有者为北京捷越联合信息咨询有限公司。本系统是商用软件,未经授权擅自复制或传播本程序的部分或全部将是非法的。 
* @title: Constants.java
* @package com.jy.demo.dao.util
* @author
* @date 2014年8月19日 下午2:06:57
* @version v1.00
* @description: TODO(用一句话描述该文件做什么)
*/ 

package com.jy.platform.core.common;


/**
 * @classname: Constants
 * @description: 常量类
 */

public interface JYConstants {

	/** 公司常量 */
	public String corporation = "jieyuechina";

	/**
	 * 初始的权限
	 */
	public int DATA_PERMISION_STATUS_INIT = 1;
	/**
	 * 被编辑过
	 */
	public int DATA_PERMISION_STATUS_EDITED = 2;
	/**
	 * 转移过来的
	 */
	public int DATA_PERMISION_STATUS_TRANS = 3;

    /** 权限类型1、2、3菜单；4控件 */
    public String FUNCTION_TYPE_1 = "1";

    public String FUNCTION_TYPE_2 = "2";

    public String FUNCTION_TYPE_3 = "3";

    public String FUNCTION_TYPE_4 = "4";
	
	public String SUCCESS = "success";
	
	public String FAILED = "failed";
}
