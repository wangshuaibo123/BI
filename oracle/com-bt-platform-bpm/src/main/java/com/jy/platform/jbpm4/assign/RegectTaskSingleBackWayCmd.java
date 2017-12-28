package com.jy.platform.jbpm4.assign;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.model.Transition;
import org.jbpm.pvm.internal.cmd.AbstractCommand;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @Description: 回退任务的实现
 * @author chen
 * @version 1.0, 
 * @date 2013-8-29 下午03:18:28
 */

@Component("com.jy.platform.jbpm4.assign.RegectTaskSingleBackWayCmd")
public class RegectTaskSingleBackWayCmd extends AbstractCommand<String>{
	
	private static Logger log = LoggerFactory.getLogger(RegectTaskSingleBackWayCmd.class);
	
	private static final long serialVersionUID = 3407992369612709993L;

	private String taskId;
	
	private String destName;
	
	private String processInstanceId;
	//其他流程实例中的参数信息
	private Map<String,Object> variables;
	
	public RegectTaskSingleBackWayCmd() {
		super();
	}
	
	public String execute(Environment env) throws Exception {
		ExecutionService executionService = env.get(ExecutionService.class);
		 //获取流程实例
		ProcessInstance pi  =  executionService.findProcessInstanceById(processInstanceId);
		//获取流程定义id
		String processDefinitionId = pi.getProcessDefinitionId();
		//获取流程定义
		RepositoryService repositoryService = env.get(RepositoryService.class);
		  
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();
			
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) processDefinition;
		
		TaskService taskService = env.get(TaskService.class);
		TaskImpl currentTask = (TaskImpl)taskService.getTask(taskId);
		ExecutionImpl exec = currentTask.getProcessInstance();
		//获取当前任务的活动节点
		ActivityImpl currentActivity = exec.getActivity();
		//获取该活动节点的IncomingTransition
		List<Transition> listTemp = (List<Transition>) currentActivity.getIncomingTransitions();
		TransitionImpl incomingTransition = (TransitionImpl)currentActivity.getIncomingTransitions().get(0);
		//获取IncomingTransition的源活动节点
		ActivityImpl sourceActivity = null;
		if(StringUtils.isNotEmpty(this.destName)){
			sourceActivity = pd.findActivity(destName);
		}else{
			sourceActivity = incomingTransition.getSource();
		}
		
		TransitionImpl backTransition = currentActivity.createOutgoingTransition();
		
		//当前活动节点为新的回退Transition的源，而原来的“源”活动节点变成了目标活动节点
		backTransition.setSource(currentActivity);
		backTransition.setDestination(sourceActivity);
		String path = currentActivity.getName()+" to " + sourceActivity.getName();
		backTransition.setName("regect "+ path);
		log.info("--------代码绘制流程节点的路径----backway:"+backTransition.getName());
		
			/*String executinId = currentTask.getExecutionId();
			String acivityName = destName;
			Map param = new HashMap();
			param.put("EXECUTION_ID", executinId);
			param.put("ACITVITY_NAME", acivityName);
			//
			Map resultMap = (Map) dao.queryObjectBySql("QUERY_ACTIVITY_ASSIGNEE_OF_HIS_TASKS_BY_MY_SQL", param);
			String assginee = "";
			if(resultMap!=null){
				assginee=(String) resultMap.get("ASSIGNEE_");
				log.info("---backway-assginee:"+assginee);
			}*/
		String turnDic = backTransition.getName();
		taskService.completeTask(taskId,turnDic,variables);
		
		
		return "";
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getDestName() {
		return destName;
	}
	public void setDestName(String destName) {
		this.destName = destName;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public Map<String, Object> getVariables() {
		return variables;
	}
	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}

}
