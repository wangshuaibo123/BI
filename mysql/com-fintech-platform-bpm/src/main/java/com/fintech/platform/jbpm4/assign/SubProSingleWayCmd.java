package com.fintech.platform.jbpm4.assign;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.cmd.AbstractCommand;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.springframework.stereotype.Component;

import com.fintech.platform.core.common.JYLoggerUtil;

/**
 * 
 * @Description: 绘制子流程之间快速进入的路径
 * @author
 * @version 1.0, 
 * @date 2013-8-29 下午03:18:28
 */

@Component("com.fintech.platform.jbpm4.assign.SubProSingleWayCmd")
public class SubProSingleWayCmd extends AbstractCommand<String>{
	
	private static final long serialVersionUID = 3407992369612709993L;

	private String taskId;
	
	private String destName;
	
	private String processInstanceId;
	//子流程结束之后 主流程执行路径
	private String turnDic;
	//其他流程实例中的参数信息
	private Map<String,Object> variables;
	
	public SubProSingleWayCmd() {
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
		
		
		ExecutionImpl exec = (ExecutionImpl) pi;
		//获取当前任务的活动节点
		ActivityImpl currentActivity = exec.getActivity();
		
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
		
		//String path = currentActivity.getName()+" to " + sourceActivity.getName();
		//backTransition.setName("regect "+ path);
		
		backTransition.setName(turnDic);
		
		
		JYLoggerUtil.info(this.getClass(),"--------代码绘制子流程之间的流程节点的路径----:"+backTransition.getName());
		
		TaskService taskService = env.get(TaskService.class);
		
		taskService.completeTask(taskId,turnDic,variables);
		
		return "";
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTurnDic() {
		return turnDic;
	}

	public void setTurnDic(String turnDic) {
		this.turnDic = turnDic;
	}

}
