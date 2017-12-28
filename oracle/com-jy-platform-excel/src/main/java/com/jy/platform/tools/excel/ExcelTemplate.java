package com.jy.platform.tools.excel;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Excel模板对象</p>	
 * @author lin
 * @2014年12月11日 上午11:28:38
 */
public class ExcelTemplate extends Template {
	
	private static Logger logger = LoggerFactory.getLogger(ExcelTemplate.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<?> importData(InputStream inputStream, Class clazz,
			ExcelHeader excelHeader) throws Exception {
		ExcelHelper helper = ExcelHelper.getInstance();
		List<List> rows = helper.excelFileConvertToList(inputStream, excelHeader.getRowCount(),excelHeader.getTotalRows());
        Map<Integer, String> excelHeadMap = helper.convertExcelHeadToMap(excelHeader.getColumns());
        return  helper.buildDataObject(excelHeadMap, excelHeader.getColumnsConvertMap(), rows, clazz);
		
	}

	/* (non-Javadoc)
	 * @see com.jy.platform.tools.excel.Template#importData(java.io.InputStream, java.lang.Class, com.jy.platform.tools.excel.TxtHeader)
	 */
	@Override
	public List<?> importData(InputStream inputStream, Class clazz,
			TxtHeader txtHeader) throws Exception {
		return null;
	}
	
}
