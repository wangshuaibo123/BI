package com.jy.platform.jbpm4.jbpm4FormInfo.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.api.org.OrgInfo;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.api.sysauth.SysRoleAPI;
import com.jy.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.jy.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.jy.platform.jbpm4.repository.JbpmMapper;
import com.jy.platform.jbpm4.tool.ConstantJBPM;
/**
 * 
 * @Description: 定义工作流表单配置表接口的实现类
 * @author chen
 * @version 1.0, 
 * @date 2014-03-05 14:39:08
 */
@Service("com.jy.platform.jbpm4.jbpm4FormInfo.service.impl.Jbpm4FormInfoServiceImpl")
public class Jbpm4FormInfoServiceImpl implements IJbpm4FormInfoService, Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	private JbpmMapper mapper;
	
	@Transactional(rollbackFor=Exception.class)
	public String addJbpm4FormInfo(Map<String, Object> paramMap) throws Exception {
		String id="";
		try {
			mapper.addJbpm4FormInfo(paramMap);
			id = String.valueOf(paramMap.get("id"));
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return id;
	}

	
	public String deleteJbpm4FormInfo(String ids) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		String tempId = "";//'2','3'或 '2'
		if(ids!=null && !"".equals(ids)){
			String[] tempIds = ids.split(",");
			for(int i = 0;i < tempIds.length ;i ++){
				if(!"".equals(tempId)){
					tempId += ",";
				}
					
				tempId += "'"+tempIds[i]+"'";
			}
		}
		paramMap.put("ids", tempId);
		
		mapper.deleteJbpm4FormInfo(paramMap);
		return ConstantJBPM.DELETE_SUCCESS;
	}

	
	public List queryListOfJbpm4FormInfo(Map<String, Object> paramMap)
			throws Exception {
		
		List dataList = mapper.queryListOfJbpm4FormInfoPage(paramMap);
		return dataList;
	}

	
	public List<Jbpm4FormInfoDTO> queryListOfJbpm4FormInfoByPage(Map<String, Object> paramMap)
			throws Exception {
		
		List dataList = mapper.queryListOfJbpm4FormInfoPage(paramMap);
		return dataList;
	}

	
	public Jbpm4FormInfoDTO queryOneJbpm4FormInfo(Map<String, Object> paramMap) throws Exception {
		//paramMap.put("id",value)
		
		Jbpm4FormInfoDTO dto = mapper.queryOneJbpm4FormInfo(paramMap);
		if(dto == null) return null;
		//todo 如果是人工选择参与者 则 将已选择类型编号 转换 为名称
		String type = dto.getParticipatorType();//配置 参与者类型 0 ：角色，1：机构，2：人员
		String partType = dto.getPartType();
		String otherParams = dto.getOtherParams();
		if(StringUtils.isEmpty(otherParams)) otherParams = "";
		StringBuilder sb = new StringBuilder("");
		//调用 通过 角色编码 获取 该角色下 所有人员信息
    	List<Map<String, Object>> list = null;
    	if(ConstantJBPM.PARTNER_0.equals(partType) || partType == null || "".equals(partType)){//角色 //调用 通过角色查询人员的 接口
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    		ServletContext servletContext = webApplicationContext.getServletContext();
    		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		SysRoleAPI sysRoleAPI =(SysRoleAPI) context.getBean("sysRoleAPI");
    		
    		String[] roleCodes = otherParams.split(",");
    		for(int i = 0;i < roleCodes.length; i ++){
    			String roleCode = roleCodes[i];
    			Map<String, Object> param = new HashMap<String, Object>();
    			//param.put("roleType", ConstantJBPM.JBPM_ROLE_TYPE);//只查询 工作流角色
        		param.put("roleCode", roleCode);
        		param.put("roleGroupCode", roleCode);
        		//查询 角色组 中文描述
            	List<Map<String, Object>> roleList = sysRoleAPI.queryRoleGroupList(0, 1, param);//sysRoleAPI.queryRoleList(0, 1, param);
            	if(roleList == null || roleList.size() == 0) continue;
            	Map<String,Object> roleInfo = roleList.get(0);
    			if(StringUtils.isNotEmpty(sb.toString())) sb.append(",");
    			//sb.append(roleInfo.get("roleName"));
    			sb.append(roleInfo.get("roleGroupName"));//调整为 角色组 
    		}
    	}else if(ConstantJBPM.PARTNER_1.equals(partType)){//组织机构
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    		ServletContext servletContext = webApplicationContext.getServletContext();
    		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		OrgAPI orgApi =(OrgAPI) context.getBean("orgAPI");
    		
    		String[] orgIds = otherParams.split(",");
    		for(int i = 0;i < orgIds.length; i ++){
    			OrgInfo org = orgApi.getOrgInfo(orgIds[i]);
    			if(org == null ) continue;
				if(StringUtils.isNotEmpty(sb.toString())) sb.append(",");
    			sb.append(org.getOrgName());
    		}
    	}else if(ConstantJBPM.PARTNER_2.equals(partType)){// 通过人员 编码 或是 ID 查询出 人员 详情 接口
    		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
    		ServletContext servletContext = webApplicationContext.getServletContext();
    		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    		OrgAPI orgApi =(OrgAPI) context.getBean("orgAPI");
    		
    		String[] userIDs = otherParams.split(",");
    		for(int i = 0;i < userIDs.length; i ++){
    			UserInfo user = orgApi.getUserInfoDetail(userIDs[i]);
    			if(user == null ) continue;
				if(StringUtils.isNotEmpty(sb.toString())) sb.append(",");
    			sb.append(user.getUserName());
    		}
    	}
    	
    	dto.setOtherParamsDis(sb.toString());
		return dto;
	}

	
	public int updateJbpm4FormInfo(Map<String, Object> paramMap) throws Exception {
		int  count = mapper.updateJbpm4FormInfo(paramMap);
		return count;
	}


	public String queryJbpm4FormInfoIdBysql(String proKey, String proVersion,
			String name) throws Exception {
		//paramMap.put("id",value)
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("proDefKey", proKey);
		paramMap.put("proActivityName", name);
		paramMap.put("proLevel", proVersion);
		
		String id = mapper.queryFormInfoIdBySql( paramMap);
		return id;
	}

}

