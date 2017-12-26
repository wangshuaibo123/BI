/**
 * 
 */
package com.fintech.platform.tools.excel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.bean.BeanUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p></p>	
 * @author
 * @2014年12月11日 下午3:02:19
 */
public class TxtTemplate extends Template {
	
	private static Logger logger = LoggerFactory.getLogger(TxtTemplate.class);
	
	private static final String SEPARATOR = ",";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<?> importData(InputStream inputStream, Class clazz,
			TxtHeader txtHeader) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		List result = new ArrayList();
		 Map<Integer, String> headerMap = convertHeadToMap(txtHeader.getColumns());
		while ((line = reader.readLine())!=null) {
			String [] dtoProperties  = line.split(SEPARATOR);
			if (dtoProperties.length != txtHeader.getColumns().size()) {
				throw new IllegalArgumentException("定义的列数量与导入的数据列数不符...");
			}
			result.add(buildDataObject(headerMap,txtHeader.getColumnsConvertMap(),dtoProperties,clazz));
		}
		return result;
	}
	
	private Map<Integer, String> convertHeadToMap(List<TxtColumn> txtColumns) {
        Map<Integer, String> headMap = new HashMap<Integer, String>();
        for (TxtColumn column : txtColumns) {
        	final boolean isImport = column.getFieldName() != null 
        							&& !"".equals(column.getFieldName())
        							&& !"serialVersionUID".equalsIgnoreCase(column.getFieldName())
        							&& column.isImport();
            if(isImport) {
            	headMap.put(column.getIndex(), column.getFieldName());
            }
        }
        return headMap;
    }
	
	
    private Map<String, Object> rowListToMap(Map<Integer, String> headMap, Map<String, Map> headConvertMap, String [] dtoProperties) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        for(int i = 0; i < dtoProperties.length; i++) {
            String fieldName =  headMap.get(i);
            // 存在所定义的列
            if(fieldName != null) {
                String value = dtoProperties[i];
                if(headConvertMap != null && headConvertMap.get(fieldName) != null) {
                   if (headConvertMap.get(fieldName).get(value)!=null) {
                	   value = headConvertMap.get(fieldName).get(value).toString();
                   }
                }
                if(value !=null){
                	rowMap.put(fieldName, value);
                }
            }
        }
        return rowMap;
    }
	
	public Object buildDataObject(Map<Integer, String> headMap, Map<String, Map> headConvertMap, String [] dtoProperties, Class cls) throws Exception{
            // 当前行的数据放入map中,生成<fieldName, value>的形式
            Map<String, Object> rowMap = rowListToMap(headMap, headConvertMap, dtoProperties);
            Object obj = null;
            try {
                obj = cls.newInstance();
            } catch (InstantiationException ex) {
                throw new InstantiationException("dto is not instantiation");
            } catch (IllegalAccessException ex) {
                throw new IllegalAccessException("param dtoName is error");
            }
           BeanUtil.populateBean(obj, rowMap);
        return obj;
    }
    
    
	@Override
	public List<?> importData(InputStream inputStream, Class clazz,
			ExcelHeader excelHeader) throws Exception {
		return null;
	}

	

}
