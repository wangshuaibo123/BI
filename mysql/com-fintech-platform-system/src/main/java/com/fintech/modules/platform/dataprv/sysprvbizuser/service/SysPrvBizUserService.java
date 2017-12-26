package com.fintech.modules.platform.dataprv.sysprvbizuser.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.dataprv.sysprvauthresult.dao.SysPrvAuthResultDao;
import com.fintech.modules.platform.dataprv.sysprvbizuser.dao.SysPrvBizUserDao;
import com.fintech.modules.platform.dataprv.sysprvbizuser.dto.SysPrvBizUserDTO;

/**
 * @classname: SysPrvBizUserService
 * @description: 定义  业务数据用户权限表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.dataprv.sysprvbizuser.service.SysPrvBizUserService")
public class SysPrvBizUserService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysPrvBizUserDao dao;
	
	@Autowired
	private SysPrvAuthResultDao rDao;

	/**
     * @author
     * @description: 分页查询 业务数据用户权限表列表
     * @date 2014-10-18 16:05:10
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysPrvBizUserDTO> searchSysPrvBizUserByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysPrvBizUserDTO> dataList =  dao.searchSysPrvBizUserByPaging(searchParams);
		return dataList;
	}
	
	/**
     * @author
     * @description: 分页查询 业务数据用户权限表列表
     * @date 2014-10-18 16:05:10
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysPrvBizUserDTO> searchSysPrvBizUserNameByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysPrvBizUserDTO> dataList =  dao.searchSysPrvBizUserNameByPaging(searchParams);
		return dataList;
	}
	
	/**
     * @author
     * @description: 按条件查询业务数据用户权限表列表
     * @date 2014-10-18 16:05:10
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysPrvBizUserDTO> searchSysPrvBizUser(Map<String,Object> searchParams) throws Exception {
	    List<SysPrvBizUserDTO> dataList = dao.searchSysPrvBizUser(searchParams);
        return dataList;
    }
	
	/**
     * @author
     * @description: 查询业务数据用户权限表对象
     * @date 2014-10-18 16:05:10
     * @param id
     * @return
     * @throws
     */ 
	public SysPrvBizUserDTO querySysPrvBizUserByPrimaryKey(String id) throws Exception {
		
		SysPrvBizUserDTO dto = dao.findSysPrvBizUserByPrimaryKey(id);
		
		if(dto == null) dto = new SysPrvBizUserDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysPrvBizUser
     * @author
     * @description: 新增 业务数据用户权限表对象
     * @date 2014-10-18 16:05:10
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public int insertSysPrvBizUser(Map<String, Object> paramMap){
		int count = dao.insertSysPrvBizUser(paramMap);
		return count;
	}
	/**
     * @title: updateSysPrvBizUser
     * @author
     * @description: 修改 业务数据用户权限表对象
     * @date 2014-10-18 16:05:10
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysPrvBizUser(Map<String,Object> param){
		dao.updateSysPrvBizUser(param);
	}
	/**
     * @title: deleteSysPrvBizUserByPrimaryKey
     * @author
     * @description: 删除 业务数据用户权限表,按主键
     * @date 2014-10-18 16:05:10
     * @param paramMap
     * @throws
     */ 
	public void deleteSysPrvBizUserByParams(Map<String,Object> param){
		dao.deleteSysPrvBizUserByParams(param);
	}

}

