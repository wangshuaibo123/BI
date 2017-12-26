package com.fintech.modules.platform.common.yghr.dao;

import java.util.List;
import java.util.Map;

import com.fintech.modules.platform.common.dto.YGhrDTO;
import com.fintech.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: YgHrDao
 * @description: 定义  yg_hr 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  Administrator
 */
@MyBatisRepository
public interface YgHrDao {
    
    /**
     * @author Administrator
     * @description: 分页查询yg_hr
     * @date 2017-01-13 17:34:05
     * @param searchParams
     * @return
     */
    public List<YGhrDTO> searchYgHrByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author Administrator
     * @description:查询对象yg_hr
     * @date 2017-01-13 17:34:05
     * @param searchParams
     * @return
     */
    public List<YGhrDTO> searchYgHr(Map<String,Object> searchParams);

    /**
     * @author Administrator
     * @description:查询对象yg_hr
     * @date 2017-01-13 17:34:05
     * @param id
     * @return
     */
    public YGhrDTO findYgHrByPrimaryKey(String id);
    
    /**
     * @author Administrator
     * @description: 新增对象yg_hr
     * @date 2017-01-13 17:34:05
     * @param paramMap
     * @return
     */
    public int insertYgHr(Map<String, Object> paramMap);
    
    /**
     * @author Administrator
     * @description: 更新对象yg_hr
     * @date 2017-01-13 17:34:05
     * @param paramMap
     */
    public void updateYgHr(Map<String, Object> paramMap);
     
    /**
     * @author Administrator
     * @description: 按主键删除yg_hr
     * @date 2017-01-13 17:34:05
     * @param ids
     * @return
     */ 
    public void deleteYgHrByPrimaryKey(Map<String, Object> paramMap);

	public void insertImportYGtoSys(Map<String, Object> paramMap);

	public void updateSysOrgRelation();

	public void truncateYgHRTable();
    
	public void truncateYgORGTable();
    
}
