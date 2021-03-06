package com.fintech.modules.platform.common.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fintech.modules.platform.common.dao.CommonDao;

@Service("com.fintech.modules.platform.common.service")
public class CommonService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private CommonDao dao;
	
	
	/**Description: 校验唯一性较通用方法
	 * Create Date: 2014年11月20日下午3:14:53<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
				@param String tableName ,//		     * 		[tableName] 表名
				@param String uniqueColumn,//		     * 		[uniqueColumn]	需要校验的列名
				@param String checkValue,//		     * 		[checkValue] 需要校验的列值
				@param String nocheckId//		     * 		[nocheckId]  可选参数
	 * @return true 唯一  false不唯一
	 */
	public boolean checkUnique(String tableName,String uniqueColumn,String checkValue,String nocheckId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tableName", tableName);
		params.put("uniqueColumn", uniqueColumn);
		params.put("checkValue", checkValue);
		params.put("nocheckId", nocheckId);
		return 0 == dao.checkUnique(params);
	}
	
	public List<Map<String,Object>> executeSql(String sql) throws Exception{
		if(StringUtils.isEmpty(sql))throw  new IllegalArgumentException("argument is invalid");
		Map<String,Object>paramMap=new HashMap<String,Object>();
		paramMap.put("executeSql",sql);
		if(sql.toUpperCase().startsWith("SELECT")){
			return dao.executeQuerySql(paramMap);
		}else if(sql.toUpperCase().startsWith("UPDATE")){
			dao.executeUpdateSql(paramMap);
		}else if(sql.toUpperCase().startsWith("INSERT")){
			dao.executeInsertSql(paramMap);
		}else if(sql.toUpperCase().startsWith("DELETE")){
			dao.executeDeleteSql(paramMap);
		}
		return null;
	}
}
