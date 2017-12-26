package com.fintech.modules.platform.sysasynjob.service;
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

import com.fintech.modules.platform.sysasynjob.dao.SysAsynJobDao;
import com.fintech.modules.platform.sysasynjob.dto.SysAsynJobDTO;
import com.fintech.modules.platform.sysvariablejob.dto.SysVariableJobDTO;
import com.fintech.modules.platform.sysvariablejob.service.SysVariableJobService;
import com.fintech.platform.core.common.SerializeUtil;

/**
 * @classname: SysAsynJobService
 * @description: 定义  异步接口任务表 实现类
 * @author:  DELL
 */
@Service("com.fintech.modules.platform.sysasynjob.service.SysAsynJobService")
public class SysAsynJobService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Qualifier("com.fintech.modules.platform.sysvariablejob.service.SysVariableJobService")
    private SysVariableJobService varBiz;
	@Autowired
	private SysAsynJobDao dao;

	/**
     * @author DELL
     * @description: 分页查询 异步接口任务表列表
     * @date 2016-09-12 14:55:26
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysAsynJobDTO> searchSysAsynJobByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysAsynJobDTO> dataList =  dao.searchSysAsynJobByPaging(searchParams);
		return dataList;
	}
	/**
     * @author DELL
     * @description: 按条件查询异步接口任务表列表
     * @date 2016-09-12 14:55:26
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysAsynJobDTO> searchSysAsynJob(Map<String,Object> searchParams) throws Exception {
	    List<SysAsynJobDTO> dataList = dao.searchSysAsynJob(searchParams);
        return dataList;
    }
	/**
     * @author DELL
     * @description: 查询异步接口任务表对象
     * @date 2016-09-12 14:55:26
     * @param id
     * @return
     * @throws
     */ 
	public SysAsynJobDTO querySysAsynJobByPrimaryKey(String id) throws Exception {
		
		SysAsynJobDTO dto = dao.findSysAsynJobByPrimaryKey(id);
		
		if(dto == null) dto = new SysAsynJobDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysAsynJob
     * @author DELL
     * @description: 新增 异步接口任务表对象
     * @date 2016-09-12 14:55:26
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysAsynJob(SysAsynJobDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertSysAsynJob(paramMap);
		
		SysAsynJobDTO resultDto = (SysAsynJobDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateSysAsynJob
     * @author DELL
     * @description: 修改 异步接口任务表对象
     * @date 2016-09-12 14:55:26
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysAsynJob(SysAsynJobDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateSysAsynJob(paramMap);
	}
	/**
     * @title: deleteSysAsynJobByPrimaryKey
     * @author DELL
     * @description: 删除 异步接口任务表,按主键
     * @date 2016-09-12 14:55:26
     * @param paramMap
     * @throws
     */ 
	public void deleteSysAsynJobByPrimaryKey(String remark,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("remark", remark);
		paramMap.put("ids", ids);
		dao.deleteSysAsynJobByPrimaryKey(paramMap);
	}
	/**
     * 更改任务开发时间
     * @param paramMap
     */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
    public void updateJobStartTime(String id)throws Exception {
    	if(StringUtils.isEmpty(id)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("jobKeyId", id);
		dao.updateJobStartTime(paramMap);
    }
    /**
     * 任务失败后，更改任务错误信息描述
     * @param paramMap
     */
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
    public void updateJobErrorMsg(String id,String errorMsg)throws Exception {
    	if(StringUtils.isEmpty(id)) throw new Exception("操作失败！传入的参数主键为null");
		
    	if(StringUtils.isNotEmpty(errorMsg)  && errorMsg.length() >2000){
			errorMsg = errorMsg.substring(0,2000);
		}
    	SysAsynJobDTO dto = new SysAsynJobDTO();
    	dto.setId(Long.parseLong(id));
    	dto.setErrorRemark(errorMsg);
    	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		dao.updateJobErrorMsg(paramMap);
    }
    /**
     * 恢复、重置异步任务接口，允许任务重新执行
     * @param paramMap
     */
    public void updateRecoverySysBizJob(String id,String remark)throws Exception {
    	if(StringUtils.isEmpty(id)) throw new Exception("操作失败！传入的参数主键为null");
    	
    	SysAsynJobDTO dto = new SysAsynJobDTO();
    	dto.setId(Long.parseLong(id));
    	dto.setRemark(remark);
    	
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		dao.updateRecoverySysBizJob(paramMap);
    }
    /**
     * 任务执行完成后， 更改任务信息
     * @param jobKeyId
     * @param jobState 任务状态
     * @param errorRemark
     * @throws Exception 
     */
    @Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRES_NEW)
	public void updateSysAsynJob(String jobKeyId, String jobState,String errorRemark) throws Exception {
		if(StringUtils.isNotEmpty(errorRemark)){
			this.updateJobErrorMsg(jobKeyId, errorRemark);
		}else{
			SysAsynJobDTO dto = new SysAsynJobDTO();
			dto.setId(Long.parseLong(jobKeyId));
			dto.setJobState(jobState);
			this.updateSysAsynJob(dto);
		}
		
	}
	/**
	 * 新增 异步接口任务表对象 此 方法对外公开
	 * @param bizKeyId 业务主键ID 必填
	 * @param beanId 业务 service bean Id 必填
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long insertBizAsynJob(String bizKeyId,String beanId) throws Exception {
		SysAsynJobDTO dto = new SysAsynJobDTO();
		dto.setBizKeyId(bizKeyId);
		dto.setBeanId(beanId);
		dto.setJobState("1");//待调用状态
		Long keyId = this.insertSysAsynJob(dto);
		return keyId;
	}
	/**
	 * 新增 异步发送短信
	 * @param bizKeyId 业务主键ID 必填
	 * @param beanId 业务 service bean Id 必填
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long insertSMSAsynJob(String bizKeyId,String beanId) throws Exception {
		SysAsynJobDTO dto = new SysAsynJobDTO();
		dto.setBizKeyId(bizKeyId);
		dto.setBeanId(beanId);
		dto.setJobState("2");//短信 待调用状态
		Long keyId = this.insertSysAsynJob(dto);
		return keyId;
	}
	/**
	 * 新增异步任务参数
	 * @param var1 普通参数
	 * @param var2 序列化参数
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor=Exception.class)
	public Long insertJobVariable(String var1,Object var2) throws Exception {
		SysVariableJobDTO dto = new SysVariableJobDTO();
		dto.setVar1(var1);
		byte[] bts = SerializeUtil.serialise(var2);
		dto.setVar2(bts);
		long keyId = varBiz.insertSysVariableJob(dto );
		return keyId;
	}
	/**
	 * 通过主键ID 获取异步参数信息
	 * @param bizKeyId 异步参数表主键ID
	 * @return
	 * @throws Exception
	 */
	public SysVariableJobDTO getJobVariable(String bizKeyId) throws Exception {
		
		SysVariableJobDTO dto = varBiz.querySysVariableJobByPrimaryKey(bizKeyId);
		return dto;
	}

}

