package com.fintech.modules.platform.sysmessage.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysUserMsgRelationDao
 * @description: 定义  SYS_USER_MSG_RELATION 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysUserMsgRelationDao {
    
    /**
     * @author
     * @description: 分页查询SYS_USER_MSG_RELATION
     * @date 2014-11-14 11:08:58
     * @param searchParams
     * @return
     */
    public List<SysUserMsgRelationDTO> searchSysUserMsgRelationByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象SYS_USER_MSG_RELATION
     * @date 2014-11-14 11:08:58
     * @param searchParams
     * @return
     */
    public List<SysUserMsgRelationDTO> searchSysUserMsgRelation(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象SYS_USER_MSG_RELATION
     * @date 2014-11-14 11:08:58
     * @param id
     * @return
     */
    public SysUserMsgRelationDTO findSysUserMsgRelationByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象SYS_USER_MSG_RELATION
     * @date 2014-11-14 11:08:58
     * @param paramMap
     * @return
     */
    public int insertSysUserMsgRelation(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象SYS_USER_MSG_RELATION
     * @date 2014-11-14 11:08:58
     * @param paramMap
     */
    public void updateSysUserMsgRelation(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除SYS_USER_MSG_RELATION
     * @date 2014-11-14 11:08:58
     * @param ids
     * @return
     */ 
    public void deleteSysUserMsgRelationByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 获取所有可用的（且已分配角色）用户编号列表 
     * @date 2015-12-03 09:37:58
     * @return
     */
    public List<SysUserDTO> searchAllUsefulSysUser();
}
