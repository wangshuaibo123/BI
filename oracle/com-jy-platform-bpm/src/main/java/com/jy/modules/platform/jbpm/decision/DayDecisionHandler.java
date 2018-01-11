package com.jy.modules.platform.jbpm.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.springframework.stereotype.Component;
/**
 * test
 * @author xyz
 
 *
 *
 */
@Component("com.jy.modules.platform.jbpm.decision.DayDecisionHandler")
public class DayDecisionHandler implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 业务处理  by xyz
	 */
	@Override
	public String decide(OpenExecution arg0) {
		String day=(String) arg0.getVariable("day");
		System.out.println(Integer.parseInt(day));
		if(day==null||"".equals(day)||Integer.parseInt(day)<3){
			return "同意";
		}else {
			return "总裁审批";
		}
		
	}

}
