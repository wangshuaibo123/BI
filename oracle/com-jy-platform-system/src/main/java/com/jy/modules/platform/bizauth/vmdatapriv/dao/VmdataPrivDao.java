package com.jy.modules.platform.bizauth.vmdatapriv.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.bizauth.vmdatapriv.dto.VmdataPrivDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: VmdataPrivDao
 * @description: 定义  映射表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface VmdataPrivDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询映射表
     * @date 2015-01-16 17:14:46
     * @param searchParams
     * @return
     */
    public List<VmdataPrivDTO> searchVmdataPrivByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象映射表
     * @date 2015-01-16 17:14:46
     * @param searchParams
     * @return
     */
    public List<VmdataPrivDTO> searchVmdataPriv(Map<String,Object> searchParams);
    
    
    /**
     * @author dlg
     * @description:查询对象映射表数据是否重复
     * @date 2015-01-16 17:14:46
     * @param searchParams
     * @return
     */
    public List<VmdataPrivDTO> searchVmdataPrivrpeate(Map<String,Object> searchParams);
    
    
    
    

    /**
     * @author chen_gang
     * @description:查询对象映射表
     * @date 2015-01-16 17:14:46
     * @param id
     * @return
     */
    public VmdataPrivDTO findVmdataPrivByPrimaryKey(String id);
    
    /**
     * @author chen_gang
     * @description: 新增对象映射表
     * @date 2015-01-16 17:14:46
     * @param paramMap
     * @return
     */
    public int insertVmdataPriv(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 添加员工时新增对象映射表
     * @date 2015-01-16 17:14:46
     * @param paramMap
     * @return
     */
    public int insertVmdataPrivforUser(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象映射表
     * @date 2015-01-16 17:14:46
     * @param paramMap
     */
    public void updateVmdataPriv(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键删除映射表
     * @date 2015-01-16 17:14:46
     * @param ids
     * @return
     */ 
    public void deleteVmdataPrivByPrimaryKey(Map<String, Object> paramMap);
    
    public void deleteVmdataPrivByMappingId(Map<String, Object> paramMap);
    
    /**
     * @author DLG
     * @description: 按userid,orgid,orgtype删除映射表
     * @date 2015-01-27 17:14:46
     * @param ids
     * @return
     */ 
    public void deleteVmdataPrivByconditions(Map<String, Object> paramMap);

    /**
     * 清理离职人员操作权限信息。
     * @param privMap
     */
	public void modifyCleanVmruleDataPriv(Map<String, Object> privMap);

	public void fastChangeOrg(Map<String, Object> param);
    
    
    
}
