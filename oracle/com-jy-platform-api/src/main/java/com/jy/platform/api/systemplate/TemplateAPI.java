package com.jy.platform.api.systemplate;

import java.util.Map;


/**<pre>
 * 类名中文描述:
 *
 * 基本操作功能:模板接口(用于文档模板，短信，邮件等)
 *
 * Module ID  : com-jy-platform-api 
 *
 * Create Date：2014年11月6日 下午2:43:01
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
 * 2014年11月6日 | 0.1     | cyy| CREATE THE JAVA FILE: TemplateAPI.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
public interface TemplateAPI {
	
	/**Description: 获取内容
	 * Create Date: 2014年10月28日下午5:30:52<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param templateNo 模板编码
	 * @param param 模板参数
	 * @return
	 */
	public String getContent(String templateNo, Map<String, Object> param);
}
