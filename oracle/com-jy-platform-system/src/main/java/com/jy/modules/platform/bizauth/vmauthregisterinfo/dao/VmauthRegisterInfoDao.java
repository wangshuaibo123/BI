package com.jy.modules.platform.bizauth.vmauthregisterinfo.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.bizauth.vmauthregisterinfo.dto.VmauthRegisterInfoDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: VmauthRegisterInfoDao
 * @description: 定义  权限注册表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface VmauthRegisterInfoDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询权限注册表
     * @date 2015-01-16 17:14:11
     * @param searchParams
     * @return
     */
    public List<VmauthRegisterInfoDTO> searchVmauthRegisterInfoByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象权限注册表
     * @date 2015-01-16 17:14:11
     * @param searchParams
     * @return
     */
    public List<VmauthRegisterInfoDTO> searchVmauthRegisterInfo(Map<String,Object> searchParams);

    /**
     * @author chen_gang
     * @description:查询对象权限注册表
     * @date 2015-01-16 17:14:11
     * @param id
     * @return
     */
    public VmauthRegisterInfoDTO findVmauthRegisterInfoByPrimaryKey(String id);
    
    /**
     * @author chen_gang
     * @description: 新增对象权限注册表
     * @date 2015-01-16 17:14:11
     * @param paramMap
     * @return
     */
    public int insertVmauthRegisterInfo(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象权限注册表
     * @date 2015-01-16 17:14:11
     * @param paramMap
     */
    public void updateVmauthRegisterInfo(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键删除权限注册表
     * @date 2015-01-16 17:14:11
     * @param ids
     * @return
     */ 
    public void deleteVmauthRegisterInfoByPrimaryKey(Map<String, Object> paramMap);

	public void createTableBySQL(Map<String, Object> paramMap);

	public void queryVmauthRegisterInfo(Map<String, Object> paramMap);

	public void createTableBySQL2(Map<String, Object> paramMap);

	public void createTableBySQL3(Map<String, Object> paramMap);

	public void createTableBySQL4(Map<String, Object> paramMap);

	public void dropTabinfo(Map<String, Object> paramMap);

	public void dropTabinfo2(Map<String, Object> paramMap);

	public void dropTabinfo3(Map<String, Object> paramMap);

	public void dropTabinfo4(Map<String, Object> paramMap);
    
    
}
