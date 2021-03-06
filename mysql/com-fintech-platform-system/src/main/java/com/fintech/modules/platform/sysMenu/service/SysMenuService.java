package com.fintech.modules.platform.sysMenu.service;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysMenu.dao.SysMenuDao;
import com.fintech.modules.platform.sysMenu.dto.SysMenuDTO;
import com.fintech.modules.platform.sysauth.service.SysResourceService;
import com.fintech.modules.platform.sysauth.service.SysRoleService;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysMenuService
 * @description: 定义  菜单管理表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.sysMenu.service.SysMenuService")
public class SysMenuService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysMenuDao dao;
	@Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysRoleService")
	private SysRoleService sysRoleService;
	
	@Autowired
    @Qualifier("com.fintech.modules.platform.sysauth.service.SysResourceService")
	private SysResourceService sysResourceService;

	/**
     * @author
     * @description: 分页查询 菜单管理表列表
     * @date 2014-10-14 20:53:03
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysMenuDTO> searchSysMenuByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysMenuDTO> dataList =  dao.searchSysMenuByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询菜单管理表列表,首页菜单显示用
     * @date 2014-10-14 20:53:03
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysMenuDTO> searchSysMenu(Map<String,Object> searchParams) throws Exception {
	    List<SysMenuDTO> dataList = dao.searchSysMenu(searchParams);
        return dataList;
    }
	
	/**
     * @author
     * @description: 按条件查询菜单管理表列表
     * @date 2014-10-14 20:53:03
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysMenuDTO> searchAllSysMenu(Map<String,Object> searchParams) throws Exception {
	    List<SysMenuDTO> dataList = dao.searchSysMenuForHome(searchParams);
        return dataList;
    }
	
	/**
	 * 首页菜单显示专用
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysMenuDTO> searchSysMenuForHome(Map<String,Object> searchParams) throws Exception {
	    //oracle
		//List<SysMenuDTO> dataList = dao.searchSysMenuForHome(searchParams);
		//mysql
		List<SysMenuDTO>dataList=new ArrayList<SysMenuDTO>();
		//菜单最多有三层
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("level", 1);
		List<SysMenuDTO> level1List=this.searchSysMenuForHomeIndex(map);
		for(SysMenuDTO level1:level1List){
			dataList.add(level1);
			//查询子菜单
			map.put("level", 2);
			map.put("parentId", level1.getId());
			List<SysMenuDTO> level2List=this.searchSysMenuForHomeIndex(map);
			for(SysMenuDTO level2:level2List){
				dataList.add(level2);
				map.put("level", 3);
				map.put("parentId", level2.getId());
				List<SysMenuDTO> level3List=this.searchSysMenuForHomeIndex(map);
				dataList.addAll(level3List);
			}
		}
        return dataList;
    }
	
	/**Description: 
	 * Create Date: 2014年10月29日下午8:34:11<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysMenuDTO> searchSysMenuNoCache(Map<String,Object> searchParams) throws Exception {
	    List<SysMenuDTO> dataList = dao.searchSysMenu(searchParams);
        return dataList;
    }
	
	/**
     * @author
     * @description: 查询菜单管理表对象
     * @date 2014-10-14 20:53:03
     * @param id
     * @return
     * @throws
     */ 
	public SysMenuDTO querySysMenuByPrimaryKey(String id) throws Exception {
		
		SysMenuDTO dto = dao.findSysMenuByPrimaryKey(id);
		
		if(dto == null) dto = new SysMenuDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysMenu
     * @author
     * @description: 新增 菜单管理表对象
     * @date 2014-10-14 20:53:03
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysMenu(SysMenuDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysMenu(paramMap);
		
		SysMenuDTO resultDto = (SysMenuDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysMenu
     * @author
     * @description: 修改 菜单管理表对象
     * @date 2014-10-14 20:53:03
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysMenu(SysMenuDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysMenu(paramMap);
	}
	/**
     * @title: deleteSysMenuByPrimaryKey
     * @author
     * @description: 删除 菜单管理表,按主键
     * @date 2014-10-14 20:53:03
     * @param paramMap
     * @throws
     */ 
	public void deleteSysMenuByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysMenuByPrimaryKey(paramMap);
	}
	
	   /**
     * 首页菜单显示专用
     * @param searchParams
     * @return
     */
    public List<SysMenuDTO> searchSysMenuForHomeIndex(Map<String,Object> searchParams)throws Exception{
    	return dao.searchSysMenuForHomeIndex(searchParams);
    }
	
}

