package com.fintech.modules.platform.common.dao;

import java.util.List;
import java.util.Map;

import com.fintech.platform.core.mybatis.MyBatisRepository;
/**<pre>
 * 类名中文描述:公用方法dao数据操作类
 *
 * 基本操作功能:可灵活的使用一些数据查询，可不与dto挂任何关系
 *
 * Module ID  : com-pt-platform-system 
 *
 * 
 * @since 0.1
 * @version: 0.1
 * @author 
 *
 * Change History Log
 * --------------------------------------------------------------------------------------------------------------
 * Date	      | Version | Author	   | Description			              
 * --------------------------------------------------------------------------------------------------------------
 * 2014年11月20日 | 0.1     | cyy| CREATE THE JAVA FILE: CommonDao.java.
 * --------------------------------------------------------------------------------------------------------------
 *
 * --------------------------------------------------------------------------------------------------------------
 *
 * </pre>
 */
@MyBatisRepository
public interface CommonDao {
    
    /**Description: 校验唯一性较通用方法(返回值大于零则表示已经存在相对应的记录)
     * Create Date: 2014年11月20日下午2:03:14<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param params
     * 		[tableName] 表名
     * 		[uniqueColumn]	需要校验的列名
     * 		[checkValue] 需要校验的列值
     * 		[nocheckId]  可选参数
     * @return
     */
    public int checkUnique(Map<String, Object> params) ;
    
    /**
     * @param paramMap
     * @return
     */
    public List<Map<String,Object>>executeQuerySql(Map<String,Object>paramMap);

    /**
     * @param paramMap
     */
    public void executeInsertSql(Map<String,Object>paramMap);

    /**
     * @param paramMap
     */
    public void executeUpdateSql(Map<String,Object>paramMap);
    /**
     * @param paramMap
     */
    public void executeDeleteSql(Map<String,Object>paramMap);
    
}
