package com.fintech.modules.platform.jbpm.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.platform.jbpm.dao.LeaveDemoInfoDao;
import com.fintech.modules.platform.jbpm.dto.LeaveDemoInfoDTO;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.jbpm4.dto.Jbpm4BizOptionInfoDTO;
import com.fintech.platform.jbpm4.service.JYTastService;

/**
 * @classname: LeaveDemoInfoService
 * @description: 定义  申请请假表 实现类
 * @author
 */
@Service("com.fintech.modules.platform.jbpm.service.LeaveDemoInfoService")
public class LeaveDemoInfoService implements Serializable {
    private static final long serialVersionUID = 1L;
	@Autowired
	private LeaveDemoInfoDao dao;
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JYTastServiceImpl")
	private JYTastService	jyTaskService;
	/**
     * @author
     * @description: 分页查询 申请请假表列表
     * @date 2014-10-30 17:07:12
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<LeaveDemoInfoDTO> searchLeaveDemoInfoByPaging(Map<String,Object> searchParams) throws Exception {
		List<LeaveDemoInfoDTO> dataList =  dao.searchLeaveDemoInfoByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询申请请假表列表
     * @date 2014-10-30 17:07:12
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<LeaveDemoInfoDTO> searchLeaveDemoInfo(Map<String,Object> searchParams) throws Exception {
	    List<LeaveDemoInfoDTO> dataList = dao.searchLeaveDemoInfo(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询申请请假表对象
     * @date 2014-10-30 17:07:12
     * @param id
     * @return
     * @throws
     */ 
	public LeaveDemoInfoDTO queryLeaveDemoInfoByPrimaryKey(String id) throws Exception {
		
		LeaveDemoInfoDTO dto = dao.findLeaveDemoInfoByPrimaryKey(id);
		
		if(dto == null) dto = new LeaveDemoInfoDTO();
		
		return dto;
		
	}

	/**
     * @title: insertLeaveDemoInfo
     * @author
     * @description: 新增 申请请假表对象
     * @date 2014-10-30 17:07:12
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertLeaveDemoInfo(LeaveDemoInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertLeaveDemoInfo(paramMap);
		
		LeaveDemoInfoDTO resultDto = (LeaveDemoInfoDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateLeaveDemoInfo
     * @author
     * @description: 修改 申请请假表对象
     * @date 2014-10-30 17:07:12
     * @param paramMap
     * @return
     * @throws
     */
	public void updateLeaveDemoInfo(LeaveDemoInfoDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateLeaveDemoInfo(paramMap);
	}
	
	public void updateLeaveState(LeaveDemoInfoDTO dto) throws Exception {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("dto", dto);
        
        dao.updateLeaveState(paramMap);
	}
	
	/**
     * @title: deleteLeaveDemoInfoByPrimaryKey
     * @author
     * @description: 删除 申请请假表,按主键
     * @date 2014-10-30 17:07:12
     * @param paramMap
     * @throws
     */ 
	public void deleteLeaveDemoInfoByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteLeaveDemoInfoByPrimaryKey(paramMap);
	}

	/**
	 * 结合业务中间表 发起流程
	 * @param proKey 流程定义key 必选
	 * @param variables 流程变量信息 必选
	 * @param bizTabName 业务主表信息（用于业务表 于 工作流 关联） 必选
	 * @param bizInfId 业务主表的主键id 必选
	 * @param bizType	业务类型  必选
	 * @param bizInfName 任务名称 可选
	 * @param startProUserid 流程发起人 id 必选
	 * @param orgId	流程发起者 的归属机构 必选
	 * @param remark 任务 业务或其他信息的描述  可选
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor= Exception.class)
	public String startProIns(String proKey, Map<String, Object> variables,LeaveDemoInfoDTO bizDto,Map<String,Object> bizMap)
			throws Exception {
		
		//操作业务表 数据信息
		Long keyId = this.insertLeaveDemoInfo(bizDto);
		
		proKey = "cg请假流程编码";
		String bizTabName = "leave_demo_info";
		String bizInfId = String.valueOf(keyId);
		String bizType = "000";
		String bizInfName =bizDto.getAppName()+"-申请请假";
		String orgId = String.valueOf(bizDto.getUserOrgId());
		String startProUserid = String.valueOf(bizDto.getOpUserId());
		
		String remark= bizDto.getAppName()+"，申请请假： "+bizDto.getAppDay();
		
		//启动流程 返回jbpm4_biz_tab 主键ID
		String id = jyTaskService.startProIns(proKey, variables, 
				bizTabName, bizInfId, bizType, bizInfName, 
			startProUserid, orgId, remark);
		
		return id;
	}
	/**
	 * 处理待办任务
	 * @param taskId 任务ID
	 * @param turnDir 任务流程流程方法
	 * @param variables 流程、业务变量信息
	 * @param bizMap	业务信息的参数信息
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor= Exception.class)
	public void completeTask(String taskId, String turnDir,Map<String, Object> variables,Map<String,Object> bizMap) throws Exception {
		//业务数据对象
		LeaveDemoInfoDTO bizDTO = (LeaveDemoInfoDTO) bizMap.get("bizDTO");
		bizDTO.setCreateBy(bizDTO.getOpUserId().toString());
		bizDTO.setModifyBy(bizDTO.getOpUserId().toString());
		
		//业务逻辑处理 
		this.updateLeaveDemoInfo(bizDTO);
		//调用平台 jbpm 执行待办
		jyTaskService.completeTask(taskId, turnDir, variables);
		//节点意见对象
		Jbpm4BizOptionInfoDTO bizOption = (Jbpm4BizOptionInfoDTO) bizMap.get("bizOptionDTO");
		//获取创建人
    	bizOption.setCreateBy(bizOption.getOpUserId().toString());
    	//归属机构
    	bizOption.setOrgId(bizOption.getUserOrgId().toString());
    	//业务归属人
    	bizOption.setOwnerId(bizOption.getOpUserId().toString());
    	bizOption.setTaskId(taskId);
    	
		//流程节点 意见备注信息保存
		jyTaskService.insertBizOptionInf(bizOption);
	}
}

