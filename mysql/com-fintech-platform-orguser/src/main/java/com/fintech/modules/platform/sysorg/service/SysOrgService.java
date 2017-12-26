package com.fintech.modules.platform.sysorg.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dao.SysOrgDao;
import com.fintech.modules.platform.sysorg.dao.SysOrgUserDao;
import com.fintech.modules.platform.sysorg.dao.SysUserDao;
import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;
import com.fintech.modules.platform.sysorg.dto.VmtreeInfoDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysOrgService
 * @description: 定义 机构部门表 实现类
 * @author
 */
@SuppressWarnings("all")
@Service("com.fintech.modules.platform.sysorg.service.SysOrgService")
public class SysOrgService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private SysOrgDao sysOrgDao;

    @Autowired
    private SysOrgUserDao sysOrgUserDao;

    @Autowired
    private SysUserDao sysUserDao;
    

    

    /**
     * @author
     * @description: 分页查询 机构部门表列表
     * @date 2014-10-15 10:26:06
     * @param searchParams 条件
     * @return
     * @throws
     */
    public List<SysOrgDTO> searchSysOrgByPaging(Map<String, Object> searchParams) throws Exception {
        List<SysOrgDTO> dataList = sysOrgDao.searchSysOrgByPaging(searchParams);
        return dataList;
    }

    /**
     * @author
     * @description: 按条件查询机构部门表列表
     * @date 2014-10-15 10:26:06
     * @param searchParams 条件
     * @return
     * @throws
     */
    public List<SysOrgDTO> searchSysOrg(Map<String, Object> searchParams) throws Exception {
        List<SysOrgDTO> dataList = sysOrgDao.searchSysOrg(searchParams);
        return dataList;
    }

    /**
     * @author
     * @description:查询列表 SYS_ORG 根据人员id，岗位id查询org的parentIds author : cxt 、cyy
     * @date 2014-12-09 20:29:06
     * @param searchParams
     * @return
     */
    public List<SysOrgDTO> searchSysOrgByUserIdAndParentIds(Map<String, Object> searchParams) throws Exception {
        List<SysOrgDTO> dataList = sysOrgDao.searchSysOrgByUserIdAndParentIds(searchParams);
        return dataList;
    }

    /**
     * @author
     * @description: 查询机构部门表对象
     * @date 2014-10-15 10:26:06
     * @param id
     * @return
     * @throws
     */
    public SysOrgDTO querySysOrgByPrimaryKey(String id) throws Exception {

        SysOrgDTO dto = sysOrgDao.findSysOrgByPrimaryKey(id);

        //兼容虚拟树  如果在HR树查询不到  就查虚拟树
        if (dto == null){
        	dto = new SysOrgDTO();
        	VmtreeInfoDTO vdto=sysOrgDao.findVmtreeInfoByOrgId(id);
        	if(vdto!=null && !"".equals(vdto))
        	{
        		dto.setId(vdto.getOrgId());
        		dto.setOrgName(vdto.getOrgName());
        		dto.setParentId(vdto.getParentId().toString());	
        	}	
        	
        }

        return dto;

    }
    
    
    /**
     * @author
     * @description: 查询机构部门表对象
     * @date 2014-10-15 10:26:06
     * @param id
     * @return
     * @throws
     */
    public SysOrgDTO querySysOrgById(String id) throws Exception {

        SysOrgDTO dto = sysOrgDao.findSysOrgByPrimaryKey(id);
        return dto;

    }
    

    /**
     * @title: insertSysOrg
     * @author
     * @description: 新增 机构部门表对象
     * @date 2014-10-15 10:26:06
     * @param dto
     * @return
     * @throws
     */
    @SuppressWarnings("all")
    public Long insertSysOrg(SysOrgDTO dto) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", dto);
        dto.setIsLeef("1");//新增的一定是叶子节点
        int count = sysOrgDao.insertSysOrg(paramMap);
        //更新parent的是否叶子节点
        if(null!= dto.getParentId() && !"".equals(dto.getParentId().trim())){
        	final SysOrgDTO param = new SysOrgDTO();
        	param.setId(Long.parseLong(dto.getParentId()));
        	param.setIsLeef("0");
        	sysOrgDao.updateSysOrgLeef(new HashMap<String, Object>(){
        		{
        			put("dto", param);
        		}
        	});
        }
        SysOrgDTO resultDto = (SysOrgDTO) paramMap.get("dto");
        Long keyId = resultDto.getId();
        return keyId;
    }

    /**
     * @title: updateSysOrg
     * @author
     * @description: 修改 机构部门表对象
     * @date 2014-10-15 10:26:06
     * @param paramMap
     * @return
     * @throws
     */
    public void updateSysOrg(SysOrgDTO dto) throws Exception {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", dto);
        sysOrgDao.updateSysOrg(paramMap);
    }
    
    /**
     * 
     *  @Description    : TODO luobangpeng 方法描述
     *  @Method_Name    : updateSysOrgParentIds
     *  @param dto
     *  @throws Exception
     *  @return         : void
     *  @Creation Date  : 2015-8-20 下午3:52:29 
     *  @author
     */
    public void updateSysOrgParentIds(SysOrgDTO dto) throws Exception {
    	 Map<String, Object> paramMap = new HashMap<String, Object>();
    	 paramMap.put("dto", dto);
         sysOrgDao.updateSysOrgParentIds(paramMap);
    }

    /**
     * @title: deleteSysOrgByPrimaryKey
     * @author
     * @description: 删除 机构部门表,按主键
     * @date 2014-10-15 10:26:06
     * @param paramMap
     * @throws
     */
    public void deleteSysOrgByPrimaryKey(BaseDTO baseDto, String ids) throws Exception {
        if (StringUtils.isEmpty(ids))
            throw new Exception("删除失败！传入的参数主键为null");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", baseDto);
        paramMap.put("ids", ids);
        sysOrgDao.deleteSysOrgByPrimaryKey(paramMap);

        sysOrgUserDao.deleteSysOrgByOrgIds(paramMap);

        sysUserDao.deleteSysUserByOrgIds(paramMap);
        
        //更新叶子节点信息
        for(String id: ids.split(",")){
        	SysOrgDTO dto = sysOrgDao.findSysOrgByPrimaryKey(id);
        	final SysOrgDTO param = new SysOrgDTO();
        	param.setParentId(dto.getParentId());
        	param.setValidateState("1");
        	List<SysOrgDTO> subs = sysOrgDao.searchSysOrg(new HashMap<String, Object>(){
        		{
        			put("dto", param);
        		}
        	});
        	if(subs==null || subs.size()==0 ){
            	final SysOrgDTO parent = new SysOrgDTO();
            	parent.setId(Long.parseLong(dto.getParentId()));
            	parent.setIsLeef("1");
            	sysOrgDao.updateSysOrgLeef(new HashMap<String, Object>(){
            		{
            			put("dto", parent);
            		}
            	});
        	}
        }

    }
    
    
    public List<SysOrgDTO> searchSysOrgByIds(String ids)throws Exception
    {
    	 Map<String, Object> paramMap = new HashMap<String, Object>();
    	 paramMap.put("ids", ids);
    	 return sysOrgDao.searchSysOrgByIds(paramMap);
    }
    
   

    /**
     * @author
     * @description: 根据ID查询机构表详细信息
     * @date 2015-06-24 10:26:06
     * @param id
     * @return
     * @throws
     */
    public SysOrgDTO findSysOrgByPrimaryKey(String id) throws Exception {

        SysOrgDTO dto = sysOrgDao.findSysOrgByPrimaryKey(id);
        return dto;

    }

	public List<SysOrgDTO> queryTreeSysOrgByOrgLevel(Map<String, Object> searchParams) {
		List<SysOrgDTO> dataList = sysOrgDao.queryTreeSysOrgByOrgLevel(searchParams);
		return dataList;
	}
	/**
	 * 根据条件查询机构
	 * @param searchParams
	 * @return
	 */
	public List<SysOrgDTO>searchSysOrgByWhere(Map<String, Object> searchParams)throws Exception{
		return sysOrgDao.searchSysOrgByWhere(searchParams);
	}
	
	/**
	 * 查询机构树
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public List<SysOrgDTO> searchSysOrgTreeInfo(String orgId)throws Exception{
		List<SysOrgDTO> list=new ArrayList<SysOrgDTO>();
		if(orgId!=null){//加载初始节点
			list.add(this.querySysOrgById(orgId));
		}
		return list;
	}
	private void getChildList(String parentId,List<SysOrgDTO> list)throws Exception{
		Map<String,Object> searchParams=new HashMap<String,Object>();
		SysOrgDTO bean=new SysOrgDTO();
		bean.setParentId(parentId);
		searchParams.put("dto", bean);
		List<SysOrgDTO> subList=this.sysOrgDao.searchSysOrgByWhere(searchParams);
		if(subList==null || subList.size()==0){
			return;
		}else{
			for(SysOrgDTO dto:subList){
				list.add(dto);
				getChildList(String.valueOf(dto.getId()),list);
			}
		}
	}
}
