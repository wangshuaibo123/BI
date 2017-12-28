package com.jy.platform.rule.ruletree.service;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jy.platform.core.common.BaseDTO;

import com.jy.platform.rule.ruletree.dto.RuleTreeDTO;
import com.jy.platform.rule.ruletree.dao.RuleTreeDao;

/**
 * @classname: RuleTreeService
 * @description: 定义  规则树 实现类
 * @author:  zhangyu
 */
@Service("com.jy.platform.rule.ruletree.service.RuleTreeService")
public class RuleTreeService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private RuleTreeDao dao;

	/**
     * @author dell
     * @description: 查询规则树列表（全部数据）
     * @date 2017-04-20 16:30:44
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<RuleTreeDTO> searchRuleTree(Map<String,Object> searchParams) throws Exception {
	    List<RuleTreeDTO> dataList = dao.searchRuleTree(searchParams);
        return dataList;
    }
	/**
     * @author dell
     * @description: 查询规则树对象
     * @date 2017-04-20 16:30:44
     * @param id
     * @return
     * @throws
     */ 
	public RuleTreeDTO queryRuleTreeByID(String id) throws Exception {
		
		RuleTreeDTO dto = dao.findRuleTreeByID(id);
		
		if(dto == null) dto = new RuleTreeDTO();
		
		return dto;
		
	}
	
	/**查询规则树对象-包含父节点名称，一般修改时使用*/
	public RuleTreeDTO queryRuleTreeByID2(String id) throws Exception {
		
		RuleTreeDTO dto = dao.findRuleTreeByID2(id);
		
		if(dto == null) dto = new RuleTreeDTO();
		
		return dto;
		
	}

	/**
     * @title: insertRuleTree
     * @author dell
     * @description: 新增 规则树对象
     * @date 2017-04-20 16:30:44
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertRuleTree(RuleTreeDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertRuleTree(paramMap);
		
		RuleTreeDTO resultDto = (RuleTreeDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateRuleTree
     * @author dell
     * @description: 修改 规则树对象
     * @date 2017-04-20 16:30:44
     * @param paramMap
     * @return
     * @throws
     */
	public void updateRuleTree(RuleTreeDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateRuleTree(paramMap);
	}
	/**
     * @title: deleteRuleTreeByPrimaryKey
     * @author dell
     * @description: 删除 规则树,按主键
     * @date 2017-04-20 16:30:44
     * @param paramMap
     * @throws
     */ 
	public void deleteRuleTreeByID(BaseDTO baseDto,String id) throws Exception {
		if(StringUtils.isEmpty(id)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("id", id);
		
		Long defCount = dao.findRuleDefCountByID(paramMap);
		if (defCount != 0) {
			throw new RuntimeException("该节点还有关联的规则定义，不能删除。");
		}
		dao.deleteRuleTreeByID(paramMap);
	}

}

