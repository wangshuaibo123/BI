package com.fintech.modules.platform.sysorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysUserDao
 * @description: 定义  系统用户表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysUserDao {
    
    /**
     * @author
     * @description: 分页查询系统用户表
     * @date 2014-10-15 10:25:49
     * @param searchParams
     * @return
     */
    public List<SysUserDTO> searchSysUserByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象系统用户表
     * @date 2014-10-15 10:25:49
     * @param searchParams
     * @return
     */
    public List<SysUserDTO> searchSysUser(Map<String,Object> searchParams);
    /**
     * 通过 登录用户名 获取用户信息
     * @param param
     * @return
     */
    public List<SysUserDTO> searchSysUserByLoginName(Map<String,Object> param);
    /**
     * @author
     * @description:查询对象系统用户表
     * @date 2014-10-15 10:25:49
     * @param id
     * @return
     */
    public SysUserDTO findSysUserByPrimaryKey(String id);
    
    
    /**
     * 通过登录名查询用户
     * @param id
     * @return
     */
    public SysUserDTO findSysUserByLoginName(String id);
    
    /**
     * @author
     * @description: 新增对象系统用户表
     * @date 2014-10-15 10:25:49
     * @param paramMap
     * @return
     */
    public int insertSysUser(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象系统用户表
     * @date 2014-10-15 10:25:49
     * @param paramMap
     */
    public void updateSysUser(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除系统用户表
     * @date 2014-10-15 10:25:49
     * @param ids
     * @return
     */ 
    public void deleteSysUserByPrimaryKey(Map<String, Object> paramMap);

	
	/**Description: 按机构主键删除系统用户表
	 * Create Date: 2014年10月18日下午4:00:28<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param paramMap
	 */
	public void deleteSysUserByOrgIds(Map<String, Object> paramMap);
	/**
     * @author
     * @description: 分页查询系统用户表
     * @date 2014-10-15 10:25:49
     * @param searchParams
     * @return
     */
    public List<SysUserDTO> searchUserRoleByPaging(Map<String, Object> searchParams) ;
    
    /**
     * 
     * @projectname  项目名称: com-pt-platform-orguser
     * @packageclass 包及类名: com.fintech.modules.platform.sysorg.dao.SysUserDao.java
     * @description  功能描述: 查询密码修改日期是否超过三个月
     * @author 		   作        者: zhouzhiwei
     * @param	               参        数: @param paramMap
     * @param	               参        数: @return
     * @return       返回类型: int
     * @createdate   建立日期: 2016年7月22日下午4:41:53
     */
    public Map<String, Object>  pwdExpired(Map<String, Object> paramMap);
    
    public List<Map<String,Object>> getUserRoleByTargetId(Map<String, Object> searchParams);

    
}
