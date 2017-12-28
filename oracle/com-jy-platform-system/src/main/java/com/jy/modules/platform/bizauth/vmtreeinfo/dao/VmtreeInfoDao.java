package com.jy.modules.platform.bizauth.vmtreeinfo.dao;

import java.util.List;
import java.util.Map;

import com.jy.modules.platform.bizauth.vmtreeinfo.dto.VmtreeInfoDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: VmtreeInfoDao
 * @description: 定义  虚拟树表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface VmtreeInfoDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询虚拟树表
     * @date 2015-01-16 17:14:31
     * @param searchParams
     * @return
     */
    public List<VmtreeInfoDTO> searchVmtreeInfoByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象虚拟树表
     * @date 2015-01-16 17:14:31
     * @param searchParams
     * @return
     */
    public List<VmtreeInfoDTO> searchVmtreeInfo(Map<String,Object> searchParams);

    /**
     * @author chen_gang
     * @description:查询对象虚拟树表
     * @date 2015-01-16 17:14:31
     * @param orgId
     * @return
     */
    public VmtreeInfoDTO findVmtreeInfoByPrimaryKey(Map<String,Object> searchParams);
    
    /**
     * @author chen_gang
     * @description: 新增对象虚拟树表
     * @date 2015-01-16 17:14:31
     * @param paramMap
     * @return
     */
    public int insertVmtreeInfo(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象虚拟树表
     * @date 2015-01-16 17:14:31
     * @param paramMap
     */
    public void updateVmtreeInfo(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键删除虚拟树表
     * @date 2015-01-16 17:14:31
     * @param ids
     * @return
     */ 
    public void deleteVmtreeInfoByPrimaryKey(Map<String, Object> paramMap);

    
    	/**
    	* @title: searchVmtreeInfoForTree
    	* @author
    	* @description: 查询虚拟树
    	* @date 2015年1月17日 上午11:07:44
    	* @param searchParams
    	* @return
    	* @throws 
    	*/ 
    public List<VmtreeInfoDTO> searchVmtreeInfoForTree(Map<String, Object> searchParams);
    
    
    
    	/**
    	* @title: insertVmtreeInfoForHR
    	* @author
    	* @description: 插入hr的虚拟节点
    	* @date 2015年1月17日 下午2:16:29
    	* @param paramMap
    	* @return
    	* @throws 
    	*/ 
    public int insertVmtreeInfoForHR(Map<String, Object> paramMap);
    
    
    /**
     * 
     * 
     * 查询多棵树
     */
    
    public List<VmtreeInfoDTO> searchVmtreeInfoForAllTree(Map<String, Object> searchParams);
    
    /**
	* @title: searchVmtreeInfoForTreeByType
	* @author
	* @description: 根据TYPE查询虚拟树
	* @date 2015年2月4日 上午11:07:44
	* @param searchParams
	* @return
	* @throws 
	*/ 
     public List<VmtreeInfoDTO> searchVmtreeInfoForTreeByType(Map<String, Object> searchParams);
     
     
    

    
}
