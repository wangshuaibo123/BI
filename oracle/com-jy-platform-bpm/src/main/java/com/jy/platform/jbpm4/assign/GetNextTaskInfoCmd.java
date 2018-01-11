package com.jy.platform.jbpm4.assign;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.history.HistoryProcessInstanceQuery;
import org.jbpm.pvm.internal.cmd.AbstractCommand;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;

/*
 * 查询任务名称
 * @author xyz
 */
public class GetNextTaskInfoCmd extends AbstractCommand<List<String>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processInstanceId;
	
	public GetNextTaskInfoCmd(String processInstanceId) {
		super();
		this.processInstanceId = processInstanceId;
	}
	/*
	 * 查询任务名称
	 * @author xyz
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> execute(Environment environment) throws Exception {
		if(StringUtils.isEmpty(processInstanceId)){
			throw new NullPointerException("processInstanceId   is  null ");
		}
		
		List<String> nameList=new ArrayList<String>();
		HistoryProcessInstanceQuery historyProcessInstanceQuery=environment.get(HistoryProcessInstanceQuery.class);
		String pdid=historyProcessInstanceQuery.processInstanceId(processInstanceId).uniqueResult().getProcessDefinitionId();
		ProcessDefinitionQuery processDefinitionQuery=environment.get(ProcessDefinitionQuery.class);
		List<ProcessDefinition> listPD=processDefinitionQuery.list();
		ProcessDefinitionImpl temp=null;
		for(ProcessDefinition pd:listPD){
			
			if(pd.getId().equals(pdid)){
				temp=(ProcessDefinitionImpl) pd; break;
			}
		}
		
		if(temp==null){
			throw new NullPointerException();
		}
		
		
		List<ActivityImpl> lista= (List<ActivityImpl>) temp.getActivities();
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
