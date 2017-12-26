
/**
* @title: Constants.java
* @package com.pt.demo.dao.util
* @author
* @date 2014年8月19日 下午2:06:57
* @version v1.00
* @description: TODO(用一句话描述该文件做什么)
*/ 

package com.fintech.platform.core.common;


/**
 * @classname: Constants
 * @description: 常量类
 */

public interface JYConstants {

	/** 公司常量 */
	public String corporation = "ptcompany";

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
