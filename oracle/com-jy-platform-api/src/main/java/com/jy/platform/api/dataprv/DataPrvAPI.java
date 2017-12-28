package com.jy.platform.api.dataprv;

import java.util.List;
import java.util.Map;

public interface DataPrvAPI {

	/**
	 * 插入用户业务数据资源表，需要传入的内容是
	 * 1、业务数据所属用户ID      userId
	 * 2、用户所属组织机构ID		orgId   ???是否可以在实现里面通过OrgApi取？？
	 * 3、业务数据的资源表名称		tableName
	 * 4、业务数据的资源ID		bizId
	 * @return
	 */
	public int insertDataPrv(Map<String, Object> paramMap);
	
	/**
	 * 删除用户业务数据资源表，要传入的内容是
	 * 1、业务数据资源表的名称 			tableName
	 * 2、要删除的业务IDS			bizIds  多条的格式为  id1,id2,id3
	 * @param paramMap
	 */
	public void deleteDataPrv(Map<String,Object> paramMap);
	

	/**
	 * 修改用户业务数据资源表中的user，要传入的内容是
	 * 1、业务数据资源表的名称 			tableName
	 * 2、要删除的业务IDS			bizIds  多条的格式为  id1,id2,id3
	 * 3、要修改成哪个用户			toUserId
	 * @param paramMap
	 */
	public void updateDataUser(Map<String,Object> paramMap);
	
	
	
     /**
     * 
     * 查询某个用户下 所有的虚线关系
     * 
     * vmMappingtableName
     * userId
     * orgType
     */
    public Map findVmruleMappingByUserId(Map<String, Object> paramMap);
}
