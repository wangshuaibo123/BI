package com.jy.platform.jbpm4.myDecsion;

import org.jbpm.api.ProcessInstance;
import org.jbpm.api.jpdl.DecisionHandler;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.pvm.internal.model.ExecutionImpl;

import com.jy.platform.core.common.JYLoggerUtil;

/**
 * 
 * @Description: 定义 是否初级进入法律部审核 的决策类处理 demo
 * @author chen
 * @version 1.0, 
 * @date 2014-7-19 上午10:29:40
 */
public class MyDecision implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("all")
	public String decide(OpenExecution execution) {
		String dec = "初次进入法律部";
		String proInsId = execution.getId();//子流程实例id
		
		try {
			JYLoggerUtil.info(this.getClass(), "----------------"+proInsId);
			
			ProcessInstance proIns = execution.getProcessInstance();
			
			ExecutionImpl excImp = (ExecutionImpl) execution;
			//获取主流程信息
			ExecutionImpl superProIns = excImp.getSuperProcessExecution();
			String mainProInsId = superProIns.getId();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//通过子流程实例id 获取主流程挂接的合同条款编号
		boolean firstIn = false;
		if (firstIn) {
	    	
			dec = "初次进入法律部";
	    }else{
	      
	    	dec = "再次进入法律部";
	    }
	    
	    return dec;
	}

}

