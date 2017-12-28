package com.jy.platform.rule.ruletree.dao;

import java.util.List;
import java.util.Map;

import com.jy.platform.rule.ruletree.dto.RuleTreeDTO;

import com.jy.platform.core.mybatis.MyBatisRepository;
/**
 * @classname: RuleTreeDao
 * @description: 定义  规则树 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  zhangyu
 */
@MyBatisRepository
public interface RuleTreeDao {
    
    /**
     * @author zhangyu
     * @description:查询对象规则树，一次全部加载，主要是目前的jyTree组件不支持分步式加载
     * @date 2017-04-20 16:30:44
     * @param searchParams
     * @return
     */
    public List<RuleTreeDTO> searchRuleTree(Map<String,Object> searchParams);

    /**
     * @author dell
     * @description:查询对象规则树
     * @date 2017-04-20 16:30:44
     * @param id
     * @return
     */
    public RuleTreeDTO findRuleTreeByID(String id);
    
    /**
     * @author dell
     * @description:查询对象规则树-包含父节点名称，修改操作时用到
     * @date 2017-04-20 16:30:44
     * @param id
     * @return
     */
    public RuleTreeDTO findRuleTreeByID2(String id);
    
    /**
     * @author dell
     * @description: 新增对象规则树
     * @date 2017-04-20 16:30:44
     * @param paramMap
     * @return
     */
    public int insertRuleTree(Map<String, Object> paramMap);
    
    /**
     * @author dell
     * @description: 更新对象规则树
     * @date 2017-04-20 16:30:44
     * @param paramMap
     */
    public void updateRuleTree(Map<String, Object> paramMap);
     
    /**
     * @author dell
     * @description: 按主键删除规则树
     * @date 2017-04-20 16:30:44
     * @param ids
     * @return
     */ 
    public void deleteRuleTreeByID(Map<String, Object> paramMap);
    
    /**
     * @author dell
     * @description: 根据ID统计规则定义表中的记录数，删除前进行判断，如果有关联的规则定义，则树节点不允许删除
     * @date 2017-04-20 16:30:44
     * @param ids
     * @return
     */ 
    public Long findRuleDefCountByID(Map<String, Object> paramMap);
    
    
}
