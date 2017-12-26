package com.fintech.modules.platform.dataprv.sysprvtabledef.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.dataprv.sysprvtabledef.dto.SysPrvTableDefDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: SysPrvTableDefDao
 * @description: 定义  数据权限表定义 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author
 */
@MyBatisRepository
public interface SysPrvTableDefDao {
    
    /**
     * @author
     * @description: 分页查询数据权限表定义
     * @date 2014-10-18 16:07:40
     * @param searchParams
     * @return
     */
    public List<SysPrvTableDefDTO> searchSysPrvTableDefByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author
     * @description:查询对象数据权限表定义
     * @date 2014-10-18 16:07:40
     * @param searchParams
     * @return
     */
    public List<SysPrvTableDefDTO> searchSysPrvTableDef(Map<String,Object> searchParams);

    /**
     * @author
     * @description:查询对象数据权限表定义
     * @date 2014-10-18 16:07:40
     * @param id
     * @return
     */
    public SysPrvTableDefDTO findSysPrvTableDefByPrimaryKey(String id);
    
    /**
     * @author
     * @description: 新增对象数据权限表定义
     * @date 2014-10-18 16:07:40
     * @param paramMap
     * @return
     */
    public int insertSysPrvTableDef(Map<String, Object> paramMap);
    
    /**
     * @author
     * @description: 更新对象数据权限表定义
     * @date 2014-10-18 16:07:40
     * @param paramMap
     */
    public void updateSysPrvTableDef(Map<String, Object> paramMap);
     
    /**
     * @author
     * @description: 按主键删除数据权限表定义
     * @date 2014-10-18 16:07:40
     * @param ids
     * @return
     */ 
    public void deleteSysPrvTableDefByID(Map<String, Object> paramMap);

	/**
	 * 获取所有的业务表
	 * @return
	 */
	public List<String> queryTableList();

	
	/**
	 * 获取需要数据权限的业务表
	 * @return
	 */
	public List<String> queryDataPrvTableList();
    
    
}
