package com.jy.platform.jbpm4.assign;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.HistoryService;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.cmd.AbstractCommand;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
/*
 * 查询任务名称
 * @author xyz
 */
public class GetAllTaskNameCmd extends AbstractCommand<List<String>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processInstanceId;
	
	public GetAllTaskNameCmd(String processInstanceId) {
		super();
		this.processInstanceId = processInstanceId;
	}
	/**
	 * 查询任务名称
	 */
	@Override
	public List<String> execute(Environment environment) throws Exception {
		if(StringUtils.isEmpty(processInstanceId)){
			throw new NullPointerException("processInstanceId   is  null ");
		}
		List<String> nameList=new ArrayList<String>();
		HistoryService historyService=environment.get(HistoryService.class);
		String pdid=historyService.createHistoryProcessInstanceQuery().processInstanceId(processInstanceId).uniqueResult().getProcessDefinitionId();
		RepositoryService repositoryService=environment.get(RepositoryService.class);
		
		ProcessDefinitionImpl temp=(ProcessDefinitionImpl) repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid).uniqueResult();
		if(temp==null){
			throw new NullPointerException();
		}
		@SuppressWarnings("unchecked")
		List<ActivityImpl> lista=(List<ActivityImpl>) temp.getActivities();
		for(ActivityImpl ai:lista){
			if("task".equals(ai.getType())){
				nameList.add(ai.getName());
			}
		}
		return nameList;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

}
