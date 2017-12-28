package com.jy.platform.jbpm4.assign;

import java.util.Map;

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
import org.jbpm.pvm.internal.task.TaskImpl;
/**
 * 任务跳转
 * @author xyz
 *
 */
public class JumpTaskCommand extends AbstractCommand<Void> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 protected String taskId;
	 protected String outcome;
	 protected String targetName;
	 protected Map<String, ?> variables;
	 /**
	  * 任务跳转
	  * @author xyz
	  *
	  */
	@Override
	public Void execute(Environment environment) throws Exception {
		ExecutionService executionService = environment.get(ExecutionService.class);
		TaskService taskService = environment.get(TaskService.class);
		TaskImpl currentTask = (TaskImpl)taskService.getTask(taskId);
		ProcessInstance pi  =  executionService.findProcessInstanceById(currentTask.getProcessInstance().getId());
		String processDefinitionId = pi.getProcessDefinitionId();
		RepositoryService repositoryService = environment.get(RepositoryService.class);
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) processDefinition;
		ExecutionImpl exec = currentTask.getProcessInstance();
		ActivityImpl currentActivity = exec.getActivity();
		ActivityImpl sourceActivity = pd.findActivity(targetName);
		if(sourceActivity==null){
			throw new Exception("查询不到目标节点。。。");
		}     
		
		TransitionImpl backTransition = currentActivity.createOutgoingTransition();
		
		backTransition.setSource(currentActivity);
		backTransition.setDestination(sourceActivity);
		String path = currentActivity.getName()+" to " + sourceActivity.getName();
		backTransition.setName("jump  from"+ path);
	
		String outname = backTransition.getName();
		taskService.completeTask(taskId,outname,variables);
		currentActivity.removeOutgoingTransition(backTransition);
		return null;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public void setVariables(Map<String, ?> variables) {
		this.variables = variables;
	}

}
