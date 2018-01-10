package com.fintech.modules.platform.jbpm.decision;

import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.springframework.stereotype.Component;
/**
 * test
 * @author
 
 *
 *
 */
@Component("com.fintech.modules.platform.jbpm.decision.ManagerDecisionHandler")
public class ManagerDecisionHandler implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 业务判断 by xyz
	 */
	@Override
	public String decide(OpenExecution arg0) {
		String isManager=(String) arg0.getVariable("isManager");
		if("Y".equals(isManager)){
			return "是";
		}else{
			return "否";
		}
		
	}

}
