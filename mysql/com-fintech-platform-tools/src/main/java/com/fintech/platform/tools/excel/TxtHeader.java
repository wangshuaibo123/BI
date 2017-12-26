/**
 * 
 */
package com.fintech.platform.tools.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p></p>	
 * @author
 * @2014年12月16日 下午4:26:10
 */
public class TxtHeader{

	/**
     * 标题列信息
     */
    private List<TxtColumn> columns = new ArrayList<TxtColumn>();
    
    /**
     * 需要转换的列
     */
    private Map<String, Map> columnsConvertMap;
    
    
     
    public List<TxtColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<TxtColumn> columns) {
		this.columns = columns;
	}

	public Map<String, Map> getColumnsConvertMap() {
		return columnsConvertMap;
	}

	public void setColumnsConvertMap(Map<String, Map> columnsConvertMap) {
		this.columnsConvertMap = columnsConvertMap;
	}

	
}
