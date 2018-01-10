package com.fintech.platform.jbpm4.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.NewDeployment;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.history.HistoryProcessInstanceQuery;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.repository.RepositoryServiceImpl;
import org.jbpm.pvm.internal.stream.FileStreamInput;
import org.jbpm.pvm.internal.stream.StreamInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.modules.archive.timer.ArchiveTask;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.jbpm4.assign.MainProSingleWayCmd;
import com.fintech.platform.jbpm4.assign.RegectTaskSingleBackWayCmd;
import com.fintech.platform.jbpm4.assign.SubProSingleWayCmd;
/**
 * 此 工具类已交由 spring 进行相关对象的管理  
 * @Description: 工具类 获取 流程引擎 相关信息
 * @author
 * @version 1.0, 
 * @date 2013-8-29 下午03:23:33
 */

@Component("MyJBPMTool")
public class MyJBPMTool {
	private static final Logger logger = LoggerFactory.getLogger(MyJBPMTool.class);
	@Autowired
	@Qualifier("repositoryService")
	private RepositoryService repositoryService;
	@Autowired
	@Qualifier("processEngine")
	private ProcessEngine processEngine;
	@Autowired
	@Qualifier("historyService")
	private HistoryService historyService ;
	@Autowired
	@Qualifier("executionService")
	private ExecutionService executionService;

	
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.assign.RegectTaskSingleBackWayCmd")
	private RegectTaskSingleBackWayCmd backWayCmd;
	
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.assign.MainProSingleWayCmd")
	private MainProSingleWayCmd mainWayCmd;
	
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.assign.SubProSingleWayCmd")
	private SubProSingleWayCmd subWayCmd;
	
	/**
	 * 	将zip流程文件发布到pvm（流程虚拟机中，会将zip中的xml核png图片存储到数据库中）
	 * @param file
	 * @return
	 */
	public String publishProcessByZip(File processFile){
		//InputStream is;
		try {
			//is = new FileInputStream(processFile);
			String fileName = processFile.getName();
			logger.info("fileName:"+fileName);
			JYLoggerUtil.info(this.getClass(), "----fileName:"+fileName);
			NewDeployment deployment = repositoryService.createDeployment();
		    deployment.setName(fileName);
		    deployment.setTimestamp(System.currentTimeMillis());
		    
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(processFile));
	        deployment.addResourcesFromZipInputStream(zipInputStream);
	        
			String deploymentId = deployment.deploy();
			JYLoggerUtil.info(this.getClass(), deployment+"----id:"+deploymentId);
			return ConstantJBPM.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			
			return ConstantJBPM.FAILD;
		}
		
	}
	/**
	 * xml 文件 root 根节点为process-update
	 * 此方法暂时不对外开发
	 * @param deploymentId
	 * @param oldResourceName
	 * @param lastXmlFile
	 * @return
	 */
	@Transactional(rollbackFor=Exception.class)
	private String updateXmlOfProcess(String deploymentId,String oldResourceName,String lastXmlFile){
		logger.info("参数，deploymentId:"+deploymentId+"  oldResourceName:"+oldResourceName+"  lastXmlFile:"+lastXmlFile);
		File file = new File(lastXmlFile);
		StreamInput streamInput = new FileStreamInput(file);
		InputStream inputStream = streamInput.openStream();
		//((RepositoryServiceImpl)this.repositoryService).updateDeploymentResource("670001", "org/jbpm/examples/oo/process.jpdl.xml", is) ;
		((RepositoryServiceImpl)this.repositoryService).updateDeploymentResource(deploymentId, oldResourceName,inputStream) ;
		//方法进行xml更新。
		System.out.println("更新流程成功。。。。。。。。。。。。");
		return null;
	}
	/**
	 * 发布流程 xml
	 * @param file
	 * @return
	 */
	public String publishProcessByXML(String file){
		//发布流程 3
		//file "resource/swing.jpdl.xml"
		String deploymentId = repositoryService.createDeployment().addResourceFromClasspath(file).deploy();
		/*InputStream is = null;
		String deploymentId = "";
		try {
			is = new FileInputStream(new File(file));
			FileInputStream in = new FileInputStream(new File(fileName));  
			Reader read = new InputStreamReader(in,"gbk");
			deploymentId = repositoryService.createDeployment().addResourceFromInputStream("process.jdpl.xml",is).deploy();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
		
		
		//String deploymentId = getRepositoryService().createDeployment().addResourceFromClasspath("resource/swing.jpdl.xml").deploy();
		System.out.println("-----------------流程发布ID:"+deploymentId);
		
		return deploymentId;
	}
	
	/**
	 * 启动一个流程实例
	 * @return
	 */
	public ProcessInstance startOneProcessInstance(String key){
		logger.info("参数，key:"+key);
		
		ProcessInstance processInstance = executionService.startProcessInstanceByKey(key);
		
		return processInstance ;
	}
	/**
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String key,Map<String,Object> map){
		logger.info("参数，key:"+key+"  map:"+map);
		ProcessInstance processInstance = executionService.startProcessInstanceByKey(key, map);
		
		return processInstance ;
	}
	
	/**
	 * 
	 * @param key
	 * @param map
	 * @return
	 */
	public ProcessInstance startProcessInstanceByKey(String key,Map<String,Object> map,String descKey){
		logger.info("参数，key:"+key+"  map:"+map+"  descKey:"+descKey);
		ProcessInstance processInstance = executionService.startProcessInstanceByKey(key, map,descKey);
		
		return processInstance ;
	}
	
	
	/**
	 *  获取代办任务  TaskService
	 * @return
	 */
	public  TaskService getTaskService(){
		TaskService taskService = processEngine.getTaskService();
		
		return taskService ;
	}
	/**
	 * ͨ�� 通过 name 查询 其名下的代办任务
	 * @param name
	 * @return
	 */
	public List<Task> getTasksByPerson(String name){
		List<Task> taskList_A = getTaskService().findPersonalTasks(name);
		
		return taskList_A;
	}
	/**
	 *处理代办 任务
	 * @param task
	 * @return
	 */
	public String dealWithTask(String taskId){
		logger.info("参数，taskId:"+taskId);
		String msg = "";
		if(taskId != null){
			getTaskService().completeTask(taskId);
			msg = ConstantJBPM.DO_SUCCESS;
		}else{
			msg = ConstantJBPM.DO_FAILD;
		}
		return msg;
	}
	/**
	 * 
	 * @param taskId
	 * @param param
	 * @return
	 */
	public String dealWithTask2(String taskId,Map<String,Object> param){
		logger.info("参数，taskId:"+taskId+"  param:"+param);
		String msg = "";
		if(taskId != null){
			getTaskService().completeTask(taskId,param);
			msg = ConstantJBPM.DO_SUCCESS;
		}else{
			msg = ConstantJBPM.DO_FAILD;
		}
		return msg;
	}
	
	
	/**
	 * 处理代办 任务3
	 * @param taskId 待办任务id
	 * @param turnDir 流程流转方向
	 * @param param  map 参数信息
	 * @return
	 */
	public String dealWithTask3(String taskId,String turnDir,Map<String,Object> param){
		logger.info("参数，taskId:"+taskId+"  turnDir:"+turnDir+"  param:"+param);
		String msg = "";
		if(taskId != null){
			getTaskService().completeTask(taskId,turnDir,param);
			msg = ConstantJBPM.DO_SUCCESS;
		}else{
			msg = ConstantJBPM.DO_FAILD;
		}
		return msg;
	}
	
	
	
	/**
	 * 取消发布流程
	 * @param deploymentId
	 */
	public void cancelPublishProcess(String deploymentId) {
		logger.info("参数，deploymentId:"+deploymentId);
		repositoryService.deleteDeploymentCascade(deploymentId);
	}
	/**
	 * 终止 该流程实例
	 * @param processInstanceId
	 * @param msg
	 */
	public void endProcessInstance(String processInstanceId,String msg) {
		logger.info("参数，processInstanceId:"+processInstanceId+"  msg:"+msg);
		//获取流程实例
		ProcessInstance pi = executionService.findProcessInstanceById(processInstanceId);
		//检验流程实例pi，是否结束了
		System.out.println(pi.isEnded()+":===:"+pi.getId());

		executionService.endProcessInstance(processInstanceId, msg);
		
	}
	/**
	 * 回退功能实现
	 * @param taskId
	 * @return 节点名称
	 */
	public String singleBackWay(String taskId,String destName,String processInstanceId,Map<String,Object> variables) {
		logger.info("参数，taskId:"+taskId+"  destName:"+destName+"  processInstanceId:"+processInstanceId+" variables:"+variables);
		backWayCmd.setDestName(destName);
		backWayCmd.setTaskId(taskId);
		backWayCmd.setProcessInstanceId(processInstanceId);
		//流程实例中及页面传递的其他参数信息 
		backWayCmd.setVariables(variables);
		//String assginee = processEngine.execute(new RegectTaskSingleBackWayCmd(taskId,destName,processInstanceId));
		
		String assginee = processEngine.execute(backWayCmd);
		
		return assginee;
		
	}
	
	/**
	 * 子流程之间快速 处理功能实现 跨流程
	 * @param taskId
	 * @return 节点名称
	 */
	public String singleSubProWay(String toOtherSubProPath,String mainDestName,String mainProInsId,String taskId,String subDestName,String subProInsId,Map<String,Object> variables) {
		logger.info("参数，toOtherSubProPath:"+toOtherSubProPath+"  mainDestName:"+mainDestName+"  mainProInsId:"+mainProInsId+"  taskId:"+taskId+"  subDestName:"+subDestName+"  subProInsId:"+subProInsId+"  variables:"+variables);
		String msg = "";
		//定义 子流程之间的跨流程处理 outcome="#{result}"
		variables.put("result", toOtherSubProPath);
		
		mainWayCmd.setDestName(mainDestName);
		mainWayCmd.setProcessInstanceId(mainProInsId);
		mainWayCmd.setTurnDic(toOtherSubProPath);
		//流程实例中及页面传递的其他参数信息 
		mainWayCmd.setVariables(variables);
		
		
		subWayCmd.setDestName(subDestName);
		subWayCmd.setTaskId(taskId);
		subWayCmd.setProcessInstanceId(subProInsId);
		
		//流程实例中及页面传递的其他参数信息 
		subWayCmd.setVariables(variables);
		
		//先绘制 主流程中的子流程之间的快速 路径
		processEngine.execute(mainWayCmd);
		
		subWayCmd.setTurnDic(toOtherSubProPath);
		
		//再绘制 子流程内的快速处理 当前活动的task --> end
		processEngine.execute(subWayCmd);
		
		return msg;
		
	}
	
	/**
	 * 获得所有发布了的流程
	 * @return
	 */
	public List<ProcessDefinition> getAllPdList(){
		return repositoryService.createProcessDefinitionQuery().list();
	}
	/**
	 * 获得所有 已经 结束的流程
	 * @return
	 */
	
	public List<HistoryProcessInstance> getAllCompletedProInsList() {
		List<HistoryProcessInstance> hpiList  = new ArrayList<HistoryProcessInstance>();
		// 获取已经发布的流程定义
		List<ProcessDefinition> proDefs = getAllPdList();
		
		for(ProcessDefinition proDef: proDefs){
			String processDefinitionId = proDef.getDeploymentId();
			//获取已经 结束的流程实例
			List<HistoryProcessInstance> temp = historyService.createHistoryProcessInstanceQuery()
			.processDefinitionId(processDefinitionId)
			.state(HistoryProcessInstance.STATE_ENDED)
			.orderAsc(HistoryProcessInstanceQuery.PROPERTY_STARTTIME).list();
			
			hpiList.addAll(temp);
		}
		
		return hpiList;
	}
	
	public ActivityImpl getCurrentActivityInfo(String activityName,String processInstanceId){
		logger.info("参数，activityName:"+activityName+"  processInstanceId:"+processInstanceId);
		 //获取流程实例
		ProcessInstance pi  =  executionService.findProcessInstanceById(processInstanceId);
		//获取流程定义id
		String processDefinitionId = pi.getProcessDefinitionId();
		//获取流程定义 repositoryService
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();
			
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) processDefinition;
		
		ActivityImpl activity = pd.findActivity(activityName);
		
		return activity;
	}
	/**
	 * 通过 用户 领取其 待办任务
	 * @param ds
	 */
	public void takeMyTask(String[] ds) {
		logger.info("参数，ds:"+ds);
		for(int i = 0;i < ds.length;i ++){
			String participator = ds[i];
			List<Task>  tasks = getTaskService().findGroupTasks(participator);
			
			for(Task task:tasks){
				getTaskService().takeTask(task.getId(), participator);
			}
			
		}
		
	}
	
	public RepositoryService getRepositoryService() {
		
		return this.repositoryService;
	}
	/**
	 * ExecutionService接口，用来管理流程实例
	 * @return
	 */
	public ExecutionService  getExecutionService() {
		
		return this.executionService;
	}
	public HistoryService getHistoryService() {

		return this.historyService;
	}
	
	
	public ProcessEngine getProcessEngine(){
		return this.processEngine;
	}
}
