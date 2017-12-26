/**
 * 
 */
package com.fintech.platform.tools.excel;

/**
 * <p></p>	
 * @author
 * @2014年12月11日 下午2:56:32
 */
public class TemplateFactory {

	public static Template getInstance(String fileExtName){

		if ("txt".equalsIgnoreCase(fileExtName)) {
			return new TxtTemplate();
		}else if ("xls".equalsIgnoreCase(fileExtName) || "xlsx".equalsIgnoreCase(fileExtName)) {
			return new ExcelTemplate();
		}else {
			throw new RuntimeException("没有合适的模板");
		}
		
	}
	
}
