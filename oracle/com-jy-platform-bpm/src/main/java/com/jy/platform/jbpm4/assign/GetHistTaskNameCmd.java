package com.jy.platform.jbpm4.assign;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.HistoryService;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.pvm.internal.cmd.AbstractCommand;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
/*
 * 查询历史任务名称
 * @author xyz
 */
public class GetHistTaskNameCmd extends AbstractCommand<List<String>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String processInstanceId;
	
	public GetHistTaskNameCmd(String processInstanceId) {
		super();
		this.processInstanceId = processInstanceId;
	}
	/**
	 * 查询历史任务名称
	 */
	@Override
	public List<String> execute(Environment environment) throws Exception {
		if(StringUtils.isEmpty(processInstanceId)){
			throw new NullPointerException();
		}
		List<String> nameList=new ArrayList<String>();
		HistoryService historyService=environment.get(HistoryService.class);
		List<HistoryActivityInstance> list=historyService.createHistoryActivityInstanceQuery().processInstanceId(processInstanceId).list();
		for(HistoryActivityInstance hai:list){
			HistoryActivityInstanceImpl haii=(HistoryActivityInstanceImpl) hai;
			if("task".equals(haii.getType())){
				nameList.add(haii.getActivityName());
			}
		}
		return nameList;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

}
