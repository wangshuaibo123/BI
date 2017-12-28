package com.jy.platform.rule.ruledefinition.dao;

import java.util.List;
import java.util.Map;

import com.jy.platform.rule.ruledefinition.dto.RuleDefinitionDTO;
import com.jy.platform.core.mybatis.MyBatisRepository;

/**
 * @classname: RuleDefinitionDao
 * @description: 定义  RULE_DEFINITION 持久层 接口
 * 通过@MapperScannerConfigurer扫描目录中的所有接口, 动态在Spring Context中生成实现.
 * 方法名称必须与Mapper.xml中保持一致.
 * @author:  zhangyu
 */
@MyBatisRepository
public interface RuleDefinitionDao {

    /**
     * @author dell
     * @description: 分页查询RULE_DEFINITION
     * @date 2017-03-28 15:17:21
     * @param searchParams
     * @return
     */
    public List<RuleDefinitionDTO> searchRuleDefinitionByPaging(Map<String, Object> searchParams) ;

    /**
     * @author dell
     * @description:根据规则编码查询列表 RULE_DEFINITION-执行规则的时候用
     * @date 2017-03-28 15:17:21
     * @param searchParams
     * @return
     */
    public List<RuleDefinitionDTO> searchRuleDefinition(Map<String,Object> searchParams);

    /**
     * @author dell
     * @description:查询对象RULE_DEFINITION
     * @date 2017-03-28 15:17:21
     * @param id
     * @return
     */
    public RuleDefinitionDTO findRuleDefinitionByID(String id);
    
    /**
     * @author dell
     * @description: 新增对象RULE_DEFINITION
     * @date 2017-03-28 15:17:21
     * @param paramMap
     * @return
     */
    public int insertRuleDefinition(Map<String, Object> paramMap);

    /**
     * @author dell
     * @description: 更新对象RULE_DEFINITION
     * @date 2017-03-28 15:17:21
     * @param paramMap
     */
    public void updateRuleDefinition(Map<String, Object> paramMap);

    /**
     * @author dell
     * @description: 按主键删除RULE_DEFINITION
     * @date 2017-03-28 15:17:21
     * @param ids
     * @return
     */ 
    public void deleteRuleDefinitionByID(Map<String, Object> paramMap);

}
