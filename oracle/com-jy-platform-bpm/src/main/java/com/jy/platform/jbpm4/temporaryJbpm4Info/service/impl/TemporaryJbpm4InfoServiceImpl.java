package com.jy.platform.jbpm4.temporaryJbpm4Info.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.platform.core.common.JYLoggerUtil;
import com.jy.platform.jbpm4.dto.JbpmBlobDTO;
import com.jy.platform.jbpm4.dto.JbpmDTO;
import com.jy.platform.jbpm4.repository.JbpmMapper;
import com.jy.platform.jbpm4.service.IJbpmService;
import com.jy.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
import com.jy.platform.jbpm4.temporaryJbpm4Info.service.ITemporaryJbpm4InfoService;
import com.jy.platform.jbpm4.tool.ConstantJBPM;
import com.jy.platform.jbpm4.tool.MyJBPMTool;
/**
 * 
 * @Description: 定义工作流暂存表接口的实现类
 * @author chen
 * @version 1.0, 
 * @date 2013-10-16 14:58:04
 */
@Service("com.jy.platform.jbpm4.temporaryJbpm4Info.service.impl.TemporaryJbpm4InfoServiceImpl")
public class TemporaryJbpm4InfoServiceImpl implements ITemporaryJbpm4InfoService,Serializable {
	private static final long serialVersionUID = 1L;
	private static int POOL_SIZE = 2;
	private static int cpuNums = Runtime.getRuntime().availableProcessors();
	private static ExecutorService executor = Executors.newFixedThreadPool(cpuNums * POOL_SIZE );
	
	@Autowired
	private JbpmMapper mapper;
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.JbpmServiceImpl")
	private IJbpmService  jbpmServcie;
	
	//获取 访问业务层的对象
	@Autowired
	@Qualifier("MyJBPMTool")
	private MyJBPMTool myJBPMTool;
	
	/**
	 * 开启 事务
	 */
	@Transactional
	public String addTemporaryJbpm4Info(Map<String, Object> paramMap) throws Exception {
		//1.先将该流程旧信息进行假删除操作。
		//删掉流程名称相同的旧信息
		mapper.deleteOldProcessInfoBySql(paramMap);
		//2.再新增该流程信息
		TemporaryJbpm4InfoDTO dto = (TemporaryJbpm4InfoDTO) paramMap.get("dto");
		
		if(!dto.getXmlContent().contains("<?xml version='1.0' encoding='UTF-8' ?>")){
			dto.setXmlContent("<?xml version='1.0' encoding='UTF-8' ?>"+dto.getXmlContent());
		}
		
		paramMap.put("dto", dto);
		
		mapper.addTemporaryJbpm4Info(paramMap);
		
		String id = String.valueOf(paramMap.get("id"));
		
		return id;
	}

	@Transactional
	public String deleteTemporaryJbpm4Info(String ids) throws Exception {
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
		
		mapper.deleteTemporaryJbpm4Info(paramMap);
		
		return ConstantJBPM.DELETE_SUCCESS;
	}

	
	public List queryListOfTemporaryJbpm4Info(Map<String, Object> paramMap)
			throws Exception {
		
		List dataList = mapper.queryListOfTemporaryJbpm4InfoPage(paramMap);
		return dataList;
	}

	@SuppressWarnings("all")
	public List<TemporaryJbpm4InfoDTO> queryListOfTemporaryJbpm4InfoByPage(Map<String, Object> paramMap)
			throws Exception {
		
		List<TemporaryJbpm4InfoDTO> dataList = mapper.queryListOfTemporaryJbpm4InfoPage(paramMap);
		if(dataList.size() >0){
			int count = dataList.size() ;
			final CountDownLatch countDownLatch = new CountDownLatch(count);
			for(int i = 0;i < count;i ++){
				final TemporaryJbpm4InfoDTO dto = dataList.get(i);
				final JbpmDTO jbpmDto = new JbpmDTO();
				jbpmDto.setProcessState("PROCESS_DEFINE_INFO");
				jbpmDto.setProcessDefinitionKey(dto.getProcCode());
				jbpmDto.setProcessDefinitionName(dto.getProcName());
				jbpmDto.setProVersion(dto.getProcVersion());
				
				executor.execute(new Runnable(){

					public void run() {
						try {
							//判断流程 是否发布
							List<ProcessDefinition> resultList = jbpmServcie.getAllPdList(jbpmDto,null);
							//回写数据信息
							if(resultList.size() >0 ){
								dto.setPublishState("是");
							}else{
								dto.setPublishState("否");
							}
						} catch (Exception e) {
							JYLoggerUtil.error(this.getClass(), "queryListOfTemporaryJbpm4InfoByPage "+e.getMessage(),e);
						}
						
						countDownLatch.countDown();
					}
					
				});
			}
			//等待子线程都执行完了再执行主线程剩下的动作
			countDownLatch.await();
		}
		return dataList;
	}
	
	public List querySomeTempJbpm4InfoBySql(Map<String, Object> paramMap) throws Exception {
		List dataList =	mapper.querySomeTemporaryJbpm4InfoBySql(paramMap);
		return dataList;
	}

	public TemporaryJbpm4InfoDTO queryOneTemporaryJbpm4Info(Map<String, Object> paramMap) throws Exception {
		//paramMap.put("id",value)
		//考虑到流程信息 可能马上生效 此处可以查询到某一个流程的历史信息
		//TemporaryJbpm4InfoDTO role = (TemporaryJbpm4InfoDTO) dao.queryObjectBySql("query_one_temporary_jbpm4_info", paramMap);
		
		TemporaryJbpm4InfoDTO dto = mapper.queryOneTemporaryJbpm4_info(paramMap);
		return dto;
	}

	
	public int updateTemporaryJbpm4Info(Map<String, Object> paramMap) throws Exception {
		//int  count = dao.updateDataBySql("update_temporary_jbpm4_info", paramMap);
		
		int  count = mapper.updateTemporaryJbpm4Info(paramMap);
		
		return count;
	}

	public String getOneProcessXMLContent(String id) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		TemporaryJbpm4InfoDTO dto = new TemporaryJbpm4InfoDTO();
		try {
			dto = this.queryOneTemporaryJbpm4Info(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String xmlContent = dto.getXmlContent();
		
		return xmlContent;
	}
	
	public TemporaryJbpm4InfoDTO getOneTemporaryJbpm4InfoDTO(String id) throws Exception {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		TemporaryJbpm4InfoDTO dto = new TemporaryJbpm4InfoDTO();
		dto = this.queryOneTemporaryJbpm4Info(paramMap);
		return dto;
	}


	public String queryTempJbpm4Info(String name, String version,
			String processInstanceId) {
		String id = "";
		try {
			//如果流程实例id有数据 则以流程实例id进行查询。
			//查询出其流程定义的名称 及版本号。
			if(StringUtils.isNotEmpty(processInstanceId)){
				 //获取流程实例
				ProcessInstance pi  =  myJBPMTool.getExecutionService().findProcessInstanceById(processInstanceId);
				//获取流程定义id
				String processDefinitionId = pi.getProcessDefinitionId();
				//获取流程定义 repositoryService
				ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();
					
				ProcessDefinitionImpl pd = (ProcessDefinitionImpl) processDefinition;
				
				name = pd.getName();
				
				version =String.valueOf(pd.getVersion());
			}
			
			
			//按流程名称 流程版本号查询出其 在流程暂存表中的id
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("processName", name);
			param.put("processVersion", version);
			
			//id = String.valueOf(dao.queryObjectBySql("query_temp_workflow_id_by_sql", param));
			
			
			id = mapper.queryTempWorkflowIBySql(param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return id;
	}


	public int updateProPngOfTempJbpm4Info(JbpmBlobDTO pngDTO)
			throws Exception {
		//int  count = dao.updateDataBySql("update_pro_png_of_temporary_jbpm4_info", pngDTO);
		int  count = mapper.updateProPngOfTempJbpm4Info(pngDTO);
		return count;
	}

	@SuppressWarnings("all")
	public byte[] fetchProPng(Map paraMap) throws Exception {
		
      /*   paraMap.put("processName", processName);
         paraMap.put("processVersion", processVersion);
         paraMap.put("processInstantceId", processInstantceId);
         
         paraMap.put("processDefKey", proDefKey);*/
		if(paraMap != null ){
			String processDefKey = (String) paraMap.get("processDefKey");
			//如果流程定义Key 不为空 则按流程定义Key 查询出流程图
			if(StringUtils.isNotEmpty(processDefKey)){
				
			}
			String processInstantceId = (String) paraMap.get("processInstantceId");
			
			//如果流程实例id 不为空 则按流程实例id 查询出流程图
			if(StringUtils.isNotEmpty(processInstantceId)){
				
			}
			
			//其他情况 通过流程名称 流程版本号 联合查询
			
		}
		
         
		//如果流程实例id不为null 通过其查询出对应的流程版本号 及流程名称
		//JbpmBlobDTO resultMap = (JbpmBlobDTO) dao.queryObjectBySql("query_pro_png_by_pro_name_and_version", paraMap);
		JbpmBlobDTO resultMap = mapper.queryProPngByProNameAndVersion(paraMap);
		
		
		 /*FileOutputStream out = new FileOutputStream(new File("D:/test22.png")); 
       	 out.write(resultMap.getContents()); 
        
         out.flush();
         out.close(); */
         
		return resultMap.getContents();
	}
	/**
	 * 获取图片内容byxyz
	 */
	@SuppressWarnings("all")
	public byte[] fetchProPngByHist(Map paraMap) throws Exception {
		
     
		if(paraMap != null ){
			String processDefKey = (String) paraMap.get("processDefKey");
			String processInstantceId = (String) paraMap.get("processInstantceId");
		}
		JbpmBlobDTO resultMap = mapper.queryProPngByProNameAndVersionByHist(paraMap);
		
		return resultMap.getContents();
	}
}

