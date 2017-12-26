package com.fintech.modules.platform.common.ygorg.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.common.dto.YGorgDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: YgOrgDao
 * @description: 定义  yg_org 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  Administrator
 */
@MyBatisRepository
public interface YgOrgDao {
    
    /**
     * @author Administrator
     * @description: 分页查询yg_org
     * @date 2017-01-13 17:39:40
     * @param searchParams
     * @return
     */
    public List<YGorgDTO> searchYgOrgByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author Administrator
     * @description:查询对象yg_org
     * @date 2017-01-13 17:39:40
     * @param searchParams
     * @return
     */
    public List<YGorgDTO> searchYgOrg(Map<String,Object> searchParams);

    /**
     * @author Administrator
     * @description:查询对象yg_org
     * @date 2017-01-13 17:39:40
     * @param id
     * @return
     */
    public YGorgDTO findYgOrgByPrimaryKey(String id);
    
    /**
     * @author Administrator
     * @description: 新增对象yg_org
     * @date 2017-01-13 17:39:40
     * @param paramMap
     * @return
     */
    public int insertYgOrg(Map<String, Object> paramMap);
    
    /**
     * @author Administrator
     * @description: 更新对象yg_org
     * @date 2017-01-13 17:39:40
     * @param paramMap
     */
    public void updateYgOrg(Map<String, Object> paramMap);
     
    /**
     * @author Administrator
     * @description: 按主键删除yg_org
     * @date 2017-01-13 17:39:40
     * @param ids
     * @return
     */ 
    public void deleteYgOrgByPrimaryKey(Map<String, Object> paramMap);
    
    
}
