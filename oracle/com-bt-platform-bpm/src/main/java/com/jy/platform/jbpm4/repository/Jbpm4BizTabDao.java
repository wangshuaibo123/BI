package com.jy.platform.jbpm4.repository;

import java.util.List;
import java.util.Map;

import com.jy.platform.core.mybatis.MyBatisRepository;
import com.jy.platform.jbpm4.dto.Jbpm4BizTabDTO;
/**
 * @classname: Jbpm4BizTabDao
 * @description: 定义  工作流与业务表关联表 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  chen_gang
 */
@MyBatisRepository
public interface Jbpm4BizTabDao {
    
    /**
     * @author chen_gang
     * @description: 分页查询工作流与业务表关联表
     * @date 2014-10-24 15:07:31
     * @param searchParams
     * @return
     */
    public List<Jbpm4BizTabDTO> searchJbpm4BizTabByPaging(Map<String, Object> searchParams) ;
    
    /**
     * @author chen_gang
     * @description:查询对象工作流与业务表关联表
     * @date 2014-10-24 15:07:31
     * @param searchParams
     * @return
     */
    public List<Jbpm4BizTabDTO> searchJbpm4BizTab(Map<String,Object> searchParams);

    /**
     * @author chen_gang
     * @description:查询对象工作流与业务表关联表
     * @date 2014-10-24 15:07:31
     * @param id
     * @return
     */
    public Jbpm4BizTabDTO findJbpm4BizTabByPrimaryKey(String id);
   /**
    * @author chen_gang
    * @description: 通过流程实例ID 查询获取jbpm4_biz_tab信息 
    * @date 2015年12月28日 下午1:26:24
    * @param paramMap
    * @return
    */
    public Jbpm4BizTabDTO searchJbpm4BizTabByProInsId(Map<String, Object> paramMap);
    /**
     * @author chen_gang
     * @description: 新增对象工作流与业务表关联表
     * @date 2014-10-24 15:07:31
     * @param paramMap
     * @return
     */
    public int insertJbpm4BizTab(Map<String, Object> paramMap);
    
    /**
     * @author chen_gang
     * @description: 更新对象工作流与业务表关联表
     * @date 2014-10-24 15:07:31
     * @param paramMap
     */
    public void updateJbpm4BizTab(Map<String, Object> paramMap);
    
    /**
     * 跟新超时时间和提前提醒时间
    	* @title: updateOvertimeAndRemindTime
    	* @author
    	* @description:
    	* @date 2015年1月30日 上午11:50:14
    	* @param paramMap
    	* @throws
     */
    public void updateOvertimeAndRemindTime(Map<String, Object> paramMap);
     
    /**
     * @author chen_gang
     * @description: 按主键删除工作流与业务表关联表
     * @date 2014-10-24 15:07:31
     * @param ids
     * @return
     */ 
    public void deleteJbpm4BizTabByPrimaryKey(Map<String, Object> paramMap);
    /**
     * 通过jbpm4_biz_tab 主键ID 更新biz_task_type 状态
     * @param paramMap
     */
    public void updateBizTaskTypeById(Map<String, Object> paramMap);
}
