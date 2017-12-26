/**
 * 
 */
package com.fintech.platform.tools.excel;

import java.io.InputStream;
import java.util.List;

/**
 * <p>导入模板</p>	
 * @author
 * @2014年12月11日 上午11:22:27
 */
public abstract class Template {

	/**
	 * 导入数据到内存中
	 * @param inputStream  上传文件输入流
	 * @param clazz DTO Class对象
	 * @param excelHeader Excel头部对象
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> importData(InputStream inputStream,Class clazz,ExcelHeader excelHeader)throws Exception;
	
	
	
	/**
	 * 导入数据到内存中
	 * @param inputStream  上传文件输入流
	 * @param clazz DTO Class对象
	 * @param TxtHeader txt头部对象
	 * @return
	 * @throws Exception
	 */
	public abstract List<?> importData(InputStream inputStream,Class clazz,TxtHeader txtHeader)throws Exception;
	
}
