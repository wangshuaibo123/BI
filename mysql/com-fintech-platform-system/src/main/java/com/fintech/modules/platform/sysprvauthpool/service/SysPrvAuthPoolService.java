package com.fintech.modules.platform.sysprvauthpool.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.dataprv.sysprvroleauth.dto.SysPrvRoleAuthDTO;
import com.fintech.modules.platform.dataprv.sysprvroleauth.service.SysPrvRoleAuthService;
import com.fintech.modules.platform.sysprvauthpool.dao.SysPrvAuthPoolDao;
import com.fintech.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysPrvAuthPoolService
 * @description: 定义  SYS_PRV_AUTH_POOL 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService")
public class SysPrvAuthPoolService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysPrvAuthPoolDao dao;
	
	@Autowired
	private SysPrvRoleAuthService sService;

	/**
     * @author
     * @description: 分页查询 SYS_PRV_AUTH_POOL列表
     * @date 2015-01-12 20:13:17
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysPrvAuthPoolDTO> searchSysPrvAuthPoolByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysPrvAuthPoolDTO> dataList =  dao.searchSysPrvAuthPoolByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询SYS_PRV_AUTH_POOL列表
     * @date 2015-01-12 20:13:17
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysPrvAuthPoolDTO> searchSysPrvAuthPool(Map<String,Object> searchParams) throws Exception {
	    List<SysPrvAuthPoolDTO> dataList = dao.searchSysPrvAuthPool(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询SYS_PRV_AUTH_POOL对象
     * @date 2015-01-12 20:13:17
     * @param id
     * @return
     * @throws
     */ 
	public SysPrvAuthPoolDTO querySysPrvAuthPoolByPrimaryKey(String id) throws Exception {
		
		SysPrvAuthPoolDTO dto = dao.findSysPrvAuthPoolByPrimaryKey(id);
		
		if(dto == null) dto = new SysPrvAuthPoolDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysPrvAuthPool
     * @author
     * @description: 新增 SYS_PRV_AUTH_POOL对象
     * @date 2015-01-12 20:13:17
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysPrvAuthPool(SysPrvAuthPoolDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysPrvAuthPool(paramMap);
		
		SysPrvAuthPoolDTO resultDto = (SysPrvAuthPoolDTO) paramMap.get("dto");
		Long keyId = resultDto.getUserid();
		return keyId;
	}
	/**
     * @title: updateSysPrvAuthPool
     * @author
     * @description: 修改 SYS_PRV_AUTH_POOL对象
     * @date 2015-01-12 20:13:17
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysPrvAuthPool(SysPrvAuthPoolDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysPrvAuthPool(paramMap);
	}
	/**
     * @title: deleteSysPrvAuthPoolByPrimaryKey
     * @author
     * @description: 删除 SYS_PRV_AUTH_POOL,按主键
     * @date 2015-01-12 20:13:17
     * @param paramMap
     * @throws
     */ 
	public void deleteSysPrvAuthPoolByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysPrvAuthPoolByPrimaryKey(paramMap);
	}
	/**
	 * 
	 * 通过对象进行删除操作
	 * @author
	 * @param dto
	 */
	
	
	public void deleteSysPrvAuthPoolByMap(SysPrvAuthPoolDTO dto)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		dao.deleteSysPrvAuthPoolByMap(paramMap);
	}
	
	/**
	 * 
	 * 同步最新的数据角色权限
	 * @param ids
	 * @throws Exception
	 */
	
	
	@SuppressWarnings("all")
	@Transactional(rollbackFor=Exception.class)
	public void authRefresh(String ids)throws Exception
	{
		//首先删除权限池 里面与刷新角色 相关的数据
		if(ids!=null){
			if(ids.indexOf(",")>0){
				String[] roles = ids.split(",");
				for(String str:roles){
					SysPrvAuthPoolDTO dto  = new SysPrvAuthPoolDTO();
					dto.setDatesource("角色授权");
					dto.setDatesourceid(str);
					deleteSysPrvAuthPoolByMap(dto);
					//更新到最新的数据角色数据
					updateAuthPool(str);
				}
			}else{
				SysPrvAuthPoolDTO dto  = new SysPrvAuthPoolDTO();
				dto.setDatesource("角色授权");
				dto.setDatesourceid(ids);
				deleteSysPrvAuthPoolByMap(dto);
				//更新到最新的数据角色数据
				updateAuthPool(ids);
			}		
		}
	}
	
	public void updateAuthPool(String roleId)throws Exception
	{
		Map<String,Object> searchMap = new HashMap<String,Object>(); 
		SysPrvRoleAuthDTO dto = new SysPrvRoleAuthDTO();
		dto.setRoleId(Long.parseLong(roleId));
		searchMap.put("dto", dto);
		List<SysPrvRoleAuthDTO> roleAuthList=sService.searchSysPrvRoleAuth(searchMap);
		for(SysPrvRoleAuthDTO s : roleAuthList)
		{
			sService.insertAuthPool(roleId, s.getUserId().toString());
		}
	}
}

