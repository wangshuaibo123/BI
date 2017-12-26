package com.fintech.modules.platform.sysmessage.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.dto.UserSysMessageDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysMessageDao
 * @description: 定义  SYS_MESSAGE 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysMessageDao {
    
	/**
	 * @author
	 * @description: 分页查询SYS_MESSAGE
	 * @date 2014-11-14 11:07:14
	 * @param searchParams
	 * @return
	 */
	public List<SysMessageDTO> searchSysMessageByPaging(Map<String, Object> searchParams) ;
    /**
     * @author
     * @description: 分页查询SYS_MESSAGE 我的消息
     * @date 2014-11-14 11:07:14
     * @param searchParams
     * @return
     */
    public List<UserSysMessageDTO> searchMySysMessageByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象SYS_MESSAGE
     * @date 2015-12-11 11:07:13
     * @param searchParams
     * @return
     */
    public List<SysMessageDTO> searchUnRead2DSysMessage(Map<String,Object> searchParams);
    
    /**
     * @author
     * @description:查询对象SYS_MESSAGE
     * @date 2014-11-14 11:07:14
     * @param searchParams
     * @return
     */
    public List<SysMessageDTO> searchSysMessage(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象SYS_MESSAGE
     * @date 2014-11-14 11:07:14
     * @param id
     * @return
     */
    public SysMessageDTO findSysMessageByPrimaryKey(String id);
    /**
     * @author
     * @description:查询对象SYS_MESSAGE 同时关联查询出收件人对象
     * @date 2014-11-14 11:07:14
     * @param id
     * @return
     */
    public SysMessageDTO findSysMessageAndRelationByID(String id);
    
    /**
     * @author
     * @description: 新增对象SYS_MESSAGE
     * @date 2014-11-14 11:07:14
     * @param paramMap
     * @return
     */
    public int insertSysMessage(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象SYS_MESSAGE
     * @date 2014-11-14 11:07:14
     * @param paramMap
     */
    public void updateSysMessage(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除SYS_MESSAGE
     * @date 2014-11-14 11:07:14
     * @param ids
     * @return
     */ 
    public void deleteSysMessageByPrimaryKey(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description:查询对象SYS_MESSAGE
     * @date 2014-11-14 11:07:14
     * @param searchParams
     * @return
     */
    public List<SysMessageDTO> complexSearch(Map<String,Object> searchParams);
    /**
     * @author
     * @description: 按主键删除SYS_MESSAGE
     * @date 2014-11-14 11:07:14
     * @param ids
     * @return
     */ 
    public void deleteSysMessageByID(Map<String, Object> paramMap);
    /**
     * @author
     * @description: 按用户ID查询消息数量
     * @date 2014-11-14 11:07:14
     * @param publisher
     * @return
     */ 
    public int countSysMessageByUserID(Map<String, Object> paramMap);
}
