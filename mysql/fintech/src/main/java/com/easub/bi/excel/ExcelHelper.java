package com.easub.bi.excel;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

import com.fintech.platform.tools.common.DateUtil;

public class ExcelHelper {

	
	
	public static void setExcelValue(Cell cell,Object value) throws Exception{
		if(value == null) {
			cell.setCellValue("");
		}else if(value instanceof String) {
			cell.setCellValue(value.toString());
		}else if(value instanceof Integer) {
			cell.setCellValue(Integer.parseInt(value.toString()));
		}else if(value instanceof Date) {
//			cell.setCellValue((Date)value);
			cell.setCellValue(DateUtil.format((Date) value, "yyyy-MM-dd"));
		}else {
			cell.setCellValue(value.toString());
		}
	}
	
}
