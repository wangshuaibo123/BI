package com.jy.platform.rule.ruledefinition.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.ehcache.ObtainPropertiesInfo;
import com.jy.platform.core.redis.JedisSentinelPool;
import com.jy.platform.rule.RuleDefService;
import com.jy.platform.rule.ruledefinition.dao.RuleDefinitionDao;
import com.jy.platform.rule.ruledefinition.dto.RuleDefinitionDTO;

/**
 * @classname: RuleDefinitionService
 * @description: 定义  RULE_DEFINITION 实现类
 * @author:  zhangyu
 */
@Service("com.jy.platform.rule.ruledefinition.service.RuleDefinitionService")
public class RuleDefinitionService implements Serializable, RuleDefService {
	
    private static final long serialVersionUID = 1L;
    
	@Autowired
	private RuleDefinitionDao dao;
	
	@Autowired
	private JedisSentinelPool jedisSentinelPool;

	/** redis中缓存的规则定义的key */
	private static final String versionKey = ObtainPropertiesInfo.getValByKey("app.code") + ":" + "ruleDefVersion";
	
	/**
     * @author dell
     * @description: 分页查询 RULE_DEFINITION列表
     * @date 2017-03-28 15:17:21
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<RuleDefinitionDTO> searchRuleDefinitionByPaging(Map<String,Object> searchParams) throws Exception {
		List<RuleDefinitionDTO> dataList = dao.searchRuleDefinitionByPaging(searchParams);
		return dataList;
	}
	/**
     * @author dell
     * @description: 根据规则编码查询RULE_DEFINITION列表
     * @date 2017-03-28 15:17:21
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<RuleDefinitionDTO> searchRuleDefinition(String rules) throws Exception {
		Map<String,Object> searchParams = new HashMap<String,Object>();
		searchParams.put("ruleCode", rules);
	    List<RuleDefinitionDTO> dataList = dao.searchRuleDefinition(searchParams);
        return dataList;
    }
	/**
     * @author dell
     * @description: 查询RULE_DEFINITION对象
     * @date 2017-03-28 15:17:21
     * @param id
     * @return
     * @throws
     */ 
	public RuleDefinitionDTO queryRuleDefinitionByID(String id) throws Exception {
		
		RuleDefinitionDTO dto = dao.findRuleDefinitionByID(id);
		
		if(dto == null) dto = new RuleDefinitionDTO();
		
		return dto;
		
	}

	/**
     * @title: insertRuleDefinition
     * @author dell
     * @description: 新增 RULE_DEFINITION对象
     * @date 2017-03-28 15:17:21
     * @param dto
     * @return
     * @throws
     */
	public Long insertRuleDefinition(RuleDefinitionDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.insertRuleDefinition(paramMap);
		
		RuleDefinitionDTO resultDto = (RuleDefinitionDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		
		
		// 新增成功后，更新对应的缓存
		cleanCache(dto);
		
		return keyId;
	}
	/**
     * @title: updateRuleDefinition
     * @author dell
     * @description: 修改 RULE_DEFINITION对象
     * @date 2017-03-28 15:17:21
     * @param paramMap
     * @return
     * @throws
     */
	public void updateRuleDefinition(RuleDefinitionDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateRuleDefinition(paramMap);
		
		// 更新缓存
		cleanCache(dto);
	}
	/**
     * @title: deleteRuleDefinitionByID
     * @author dell
     * @description: 删除 RULE_DEFINITION,按主键
     * @date 2017-03-28 15:17:21
     * @param paramMap
     * @throws
     */ 
	public void deleteRuleDefinitionByID(BaseDTO baseDto, String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		
		
		// 删除数据前，先获取要删除的DTO
		String[] idA = ids.split(",");
		RuleDefinitionDTO[] dtoA = new RuleDefinitionDTO[idA.length];
		for (int i=0; i<idA.length; i++) {
			dtoA[i] = queryRuleDefinitionByID(idA[i]);
		}
		
		dao.deleteRuleDefinitionByID(paramMap);
		
		// 清除缓存
		for (RuleDefinitionDTO dto: dtoA) {
			cleanCache(dto);
		}
	}

	private void cleanCache(RuleDefinitionDTO dto) throws Exception {
		// 如果规则编码为空，则查取一遍
		if (dto.getRuleCode() == null || dto.getRuleCode().isEmpty()) {
			dto = queryRuleDefinitionByID(dto.getId() + "");
		}
		ShardedJedis jedis = jedisSentinelPool.getResource();
		jedis.hdel(versionKey, dto.getRuleCode());
		jedisSentinelPool.returnResource(jedis);
	}
}

