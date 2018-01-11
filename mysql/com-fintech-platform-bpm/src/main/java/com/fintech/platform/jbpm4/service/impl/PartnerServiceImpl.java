package com.fintech.platform.jbpm4.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fintech.platform.jbpm4.dto.PartnerDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.fintech.platform.jbpm4.service.IPartnerService;
import com.fintech.platform.jbpm4.tool.ConstantJBPM;
import com.fintech.platform.jbpm4.tool.StringUtilTools;
/**
 * 
 * @Description: 工作流选择参与者实现类
 * @author
 * @version 1.0, 
 * @date 2014-3-6 下午01:08:32
 */
@Service("com.fintech.platform.jbpm4.service.impl.PartnerServiceImpl")
public class PartnerServiceImpl implements IPartnerService ,Serializable{
	private static final long serialVersionUID = 1L;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.jbpm4FormInfo.service.impl.Jbpm4FormInfoServiceImpl")
	private IJbpm4FormInfoService formService;
	
	/**
	 * 首先查询出 该环节配置的流程选择参与者的规则信息
	 * 
	 */
	@SuppressWarnings("all")
	public List queryPartnerListInfo(PartnerDTO partnerDTO, Jbpm4FormInfoDTO formDTO) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("dto", formDTO);//dto.id
		
		//通过 id 查询出其form info 配置信息
		Jbpm4FormInfoDTO myFormDTO = formService.queryOneJbpm4FormInfo(paramMap);
		String otherRules = myFormDTO.getOtherParams();
		
		Map<String,Object> otherRule = new HashMap<String,Object>();
		
		// 参与者 角色设置信息
		if(StringUtils.isNotEmpty(otherRules)){
			//String roleIds = otherRules.substring(1, otherRules.length() -1);
			otherRule.put("roleIds", otherRules);
		}
		//选择参与者的规则
		String ruleInfo = myFormDTO.getRuleInfo();
		//流程流转方向
		String turnDic = formDTO.getTurnDirection();
		
		Map<String,Object> temp = new HashMap<String,Object>();
		 
		Object obj = StringUtilTools.executeStringJavaCode(ruleInfo);
		temp.putAll((Map) obj);
		String tempOperate = "";
		//如果不为null 则取
		if(StringUtils.isNotEmpty(turnDic)){
			 tempOperate = (String) temp.get(turnDic);
			 
		}else{//默认
			//流程流转方向为null 则取 默认
			 tempOperate = (String) temp.get("默认");
		}
		//组装责任链
		
		String operate = ConstantJBPM.operatePartnerMap.get(tempOperate);
		//提交请求
		//List dataList = chuShi.getRelationPartners(partnerDTO, formDTO,operate,otherRule);
		
		List dataList = new ArrayList();
		return dataList;
	}
	
	/**
	 * 处理 我的待办任务选择 参与者信息
	 */
	@SuppressWarnings("all")
	public List queryPartnerListInfo(PartnerDTO dto)
			throws Exception {

		Map<String,Object> params = new HashMap<String,Object>();
		//params.put("pager", page);
		params.put("dtoPar", dto);
		/*
		//如果是 同一处室
		if("same_offices".equals(dto.getPartnerSqlId())){
			//获取session中的 登录人归属的部门list
			List deptList = (List) dto.getHttpRequest().getSession().getAttribute("deptList");
			params.put("deptList", deptList);
		}else if("same_dept".equals(dto.getPartnerSqlId())){
			
			List deptList = (List) dto.getHttpRequest().getSession().getAttribute("deptList");
			//兼职多个部门时 只考虑同一个个2级部门内的兼职
			Map deptInfo = (Map) deptList.get(0);
			
			Map<String,Object> paramsDept = new HashMap<String,Object>();
			paramsDept.put("myDeptId", deptInfo.get("ID"));
			//查询出登陆人 所属2级部门内所有的处室列表信息
			List myDeptList = dao.queryListBySql("query_under_two_level_dept_list", paramsDept);
			
			params.put("deptList", myDeptList);
		}else if("same_branch".equals(dto.getPartnerSqlId()) ){
			//同一 机构 去登录用户的session branchPK
			String branchPK = (String) dto.getHttpRequest().getSession().getAttribute("branchPK");
			params.put("branchPK", branchPK);
			
		}else if("same_company".equals(dto.getPartnerSqlId()) ){
			//不限
			
		}
		
		List dataList = dao.queryListBySql("query_by_my_sql_query_partner", params);*/
		
		List dataList = new ArrayList();
		
		return dataList;
	}
	
	
}

