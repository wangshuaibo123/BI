package com.fintech.platform.jbpm4.assign;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
/**
 * 
 * @Description: 动态设置 节点 的参与者
 * @author
 * @version 1.0, 
 * @date 2013-10-24 下午12:35:16
 */
public class SetAssignHandler implements AssignmentHandler{

	public void assign(Assignable assignable,
			OpenExecution execution) throws Exception {
			//动态设计 该节点的参与者
			String assignee = (String)execution.getVariable("participation");
			//execution.setVariable("participation", "");
			if(assignee == null){//针对 法律审批不通过。
				assignee = (String)execution.getVariable("owner");
				//execution.setVariable("owner", "");
			}
			//如果参与者不为null 责自动指定
			if(StringUtils.isNotEmpty(assignee)){
				assignable.setAssignee(assignee);
			}
		}
}