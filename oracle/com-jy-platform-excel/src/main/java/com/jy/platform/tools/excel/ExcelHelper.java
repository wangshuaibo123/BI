/**
 * 
 */
package com.jy.platform.tools.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jodd.bean.BeanUtil;
import jodd.datetime.JDateTime;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * excel操作辅助类
 * </p>
 * 
 * @author lin
 * @2014年12月11日 上午11:53:08
 */
public class ExcelHelper {
	
	private static Logger logger = LoggerFactory.getLogger(ExcelTemplate.class);
	

	private ExcelHelper() {
	}

	private static ExcelHelper instance = null;

	public static synchronized ExcelHelper getInstance() {

		if (instance == null) {
			instance = new ExcelHelper();
		}
		return instance;
	}

	/**
	 * 将EXCEL流转成List
	 * 
	 * @param fis
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<List> excelFileConvertToList(InputStream fis,int rowCounts,int totalRows)throws Exception {

		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheetAt(0);
		List<List> rows = new ArrayList<List>();
		int index = 0;
		Row firstRow = sheet.getRow(sheet.getFirstRowNum());
		
		for (Row row : sheet) {
			index++;
			if (index <= rowCounts) continue;//跳过标题，时间，等表头行
			if(totalRows>0 && index > totalRows){
				break;
			}
			List<Object> cells = new ArrayList<Object>();
			for(int i=0;i<firstRow.getPhysicalNumberOfCells();i++){
				Cell cell = row.getCell(i);
				Object obj = null;
				if(null!=cell){
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						obj = cell.getRichStringCellValue().getString().trim();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							obj = new JDateTime(cell.getDateCellValue());
						} else {
							obj = cell.getNumericCellValue();
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						obj = cell.getBooleanCellValue();
						break;
					case Cell.CELL_TYPE_FORMULA:
						obj = cell.getNumericCellValue();
						break;
					default:
						obj = null;
					}
				}
				
				cells.add(obj);
			}
			
//			for (Cell cell : row) {
//				Object obj = null;
//				//CellReference cellRef = new CellReference(row.getRowNum(),cell.getColumnIndex());
//				switch (cell.getCellType()) {
//				case Cell.CELL_TYPE_STRING:
//					obj = cell.getRichStringCellValue().getString().trim();
//					break;
//				case Cell.CELL_TYPE_NUMERIC:
//					if (DateUtil.isCellDateFormatted(cell)) {
//						obj = new JDateTime(cell.getDateCellValue());
//					} else {
//						obj = cell.getNumericCellValue();
//					}
//					break;
//				case Cell.CELL_TYPE_BOOLEAN:
//					obj = cell.getBooleanCellValue();
//					break;
//				case Cell.CELL_TYPE_FORMULA:
//					obj = cell.getNumericCellValue();
//					break;
//				default:
//					obj = null;
//				}
//				cells.add(obj);
//			}
			rows.add(cells);
		}
		return rows;
	}
	
	
    /**
     * 将报表结构转换成Map
     * @param excelColumns
     * @return void
     */
	public Map<Integer, String> convertExcelHeadToMap(List<ExcelColumn> excelColumns) {
        Map<Integer, String> excelHeadMap = new HashMap<Integer, String>();
        for (ExcelColumn excelColumn : excelColumns) {
        	final boolean isImport = excelColumn.getFieldName() != null 
        							&& !"".equals(excelColumn.getFieldName())
        							&& !"serialVersionUID".equalsIgnoreCase(excelColumn.getFieldName())
        							&& excelColumn.isImport();
            if(isImport) {
            	excelHeadMap.put(excelColumn.getIndex(), excelColumn.getFieldName());
            }
        }
        return excelHeadMap;
    }
	
	
	
	/**
     * 根据Excel生成数据对象
     * @param excelHeadMap 表头信息
     * @param excelHeadConvertMap 需要特殊转换的单元
     * @param rows
     * @param cls 
     */
    @SuppressWarnings("rawtypes")
	public List buildDataObject(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap, List<List> rows, Class cls) throws Exception{
        List contents = new ArrayList();
        for (List list : rows) {
            // 当前行的数据放入map中,生成<fieldName, value>的形式
            Map<String, Object> rowMap = rowListToMap(excelHeadMap, excelHeadConvertMap, list);
            if (logger.isDebugEnabled()) {
            	Set<String> keySet = rowMap.keySet();
            	for(String key:keySet){//遍历key
            	    logger.debug("key : "+key+",Value:"+rowMap.get(key));
            	}
			}
            Object obj = null;
            try {
                obj = cls.newInstance();
            } catch (InstantiationException ex) {
                throw new InstantiationException("dto is not instantiation");
            } catch (IllegalAccessException ex) {
                throw new IllegalAccessException("param dtoName is error");
            }
           BeanUtil.populateBean(obj, rowMap);
           contents.add(obj);
        }
        return contents;
    }

    
    /**
     * 将行转行成map,生成<fieldName, value>的形式
     * @param excelHeadMap 表头信息
     * @param excelHeadConvertMap excelHeadConvertMap
     * @param list
     * @return
     * @return Map<String,Object>
     */
    private Map<String, Object> rowListToMap(Map<Integer, String> excelHeadMap, Map<String, Map> excelHeadConvertMap, List list) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        for(int i = 0; i < list.size(); i++) {
            String fieldName =  excelHeadMap.get(i);
            // 存在所定义的列
            if(fieldName != null) {
                Object value = list.get(i);
                if(excelHeadConvertMap != null && excelHeadConvertMap.get(fieldName) != null) {
                   if (excelHeadConvertMap.get(fieldName).get(value)!=null) {
                	   value = excelHeadConvertMap.get(fieldName).get(value);
                   }
                }
                if(value != null){
                	//如果value是String，且为空串，不放入map，以解决dto中字段实际为日期类型，在空串转换时的数字格式化问题
                	if(value instanceof java.lang.String && "".equals(((java.lang.String)value).trim())){
                		//do nothing
                	}
                	else{
                		rowMap.put(fieldName, value);
                	}
                }
            }
        }
        return rowMap;
    }
    
}
