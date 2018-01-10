package com.jy.platform.jbpm4.jbpmPartner;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.jy.platform.jbpm4.dto.JYPartnerDTO;
import com.jy.platform.jbpm4.service.JbpmTastService;
import com.jy.platform.jbpm4.tool.DateUtil;

/**
 * 
 * @description: 定义 系统 选人 通用 分配规则 算法实现 
 * @author chen_gang
 * @date:2014年11月6日下午1:30:32
 */
@Component("com.jy.platform.jbpm4.jbpmPartner.PartnerSysRule")
public class PartnerSysRule implements Serializable{
	private static final long serialVersionUID = -2421340553558319460L;
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.JbpmTaskServceImpl")
	private JbpmTastService jbpmTaskService;
	/**
	 * 通过 传递进来的 参与者列表 
	 * 查询 参与者列表中 每个参与者的待办任务 个数。
	 * 按平均分配原则  返回 符合条件的任意一个人的 user ID
	 * 此分配 方法 不考虑任务上限设置情况
	 * @param partners
	 * @return
	 */
	public String obtainPartnerByAVGRule(List<JYPartnerDTO> partners){
		// 按待办任务个数分配 如果是 相同待办任务 就查询上一笔分配时间 并分配给最早的
		String resultUserId = "-1";
		//计算 平均 分配任务 返回
		if(partners == null || partners.size() == 0) return resultUserId;
		//取第一个参与者对象
		JYPartnerDTO jydto = partners.get(0);
		//参与者的userId
		String userId = jydto.getParUserId();
		resultUserId = userId;
		//参与者的当前待办任务数
		//int taskCun = jbpmTaskService.getTaskCunOfPseron(userId);
		Map<String,Object> userTask = jbpmTaskService.getTaskCunAndLastTimeOfPerson(userId);
		//假设  第一个人为 最小参与者处理任务数
		int taskMinCun = Integer.parseInt(userTask.get("TASK_CUNT").toString());
		String lastTime = (String) userTask.get("CREATE_TIME");
		//设置最初参与者的分配任务时间
		String firstParDistributeTime = "2012-01-01 00:00:01"; 
		if(StringUtils.isNotEmpty(lastTime)){
		    firstParDistributeTime = lastTime;
		}
		for(int i = 1;i < partners.size();i++) {
			jydto = partners.get(i);
			userId = jydto.getParUserId();
			//userId 参与者的当前待办任务数
			userTask = jbpmTaskService.getTaskCunAndLastTimeOfPerson(userId);
			int taskCurCun = Integer.parseInt(userTask.get("TASK_CUNT").toString());
			lastTime = (String) userTask.get("CREATE_TIME");
			//当前参与者的上一次分配任务时间
			String curParDistributeTime = "2014-11-27 15:55:45";
			if(StringUtils.isNotEmpty(lastTime)){
				curParDistributeTime = lastTime;
			}else{
				curParDistributeTime = "2012-01-01 00:00:01"; 
			}
			
			if(taskMinCun > taskCurCun) {
				resultUserId = userId;
				taskMinCun = taskCurCun;
				firstParDistributeTime = curParDistributeTime;
			} else if(taskMinCun == taskCurCun) {
				//如果 相同 任务个数 则取 最后一次分配时间 最早的 人员ID
				if(DateUtil.compareDate(firstParDistributeTime, curParDistributeTime) >= 0) {
					resultUserId = userId;
					firstParDistributeTime = curParDistributeTime;
				}
			}
		}
		
		return resultUserId;
	}
	
	
	/**
	 * 通过 传递进来的 参与者列表 
	 * 查询 参与者列表中 每个参与者当天的任务个数(包括待办、已办、已结)。
	 * 按平均分配原则  返回 符合条件的任意一个人的 user ID
	 * 此分配 方法 不考虑任务上限设置情况
	 * @param partners
	 * @return
	 */
	public String obtainPartnerByAVGRuleToday(List<JYPartnerDTO> partners){
		// 按待办任务个数分配 如果是 相同待办任务 就查询上一笔分配时间 并分配给最早的
		String resultUserId = "-1";
		//计算 平均 分配任务 返回
		if(partners == null || partners.size() == 0) return resultUserId;
		//取第一个参与者对象
		JYPartnerDTO jydto = partners.get(0);
		//参与者的userId
		String userId = jydto.getParUserId();
		resultUserId = userId;
		//参与者的当前待办任务数
		//int taskCun = jbpmTaskService.getTaskCunOfPseron(userId);
		Map<String,Object> userTask = jbpmTaskService.getTaskCunAndLastTimeOfPersonToday(userId);
		//假设  第一个人为 最小参与者处理任务数
		int taskMinCun = Integer.parseInt(userTask.get("TASK_CUNT").toString());
		String lastTime = (String) userTask.get("CREATE_TIME");
		//设置最初参与者的分配任务时间
		String firstParDistributeTime = "2012-01-01 00:00:01"; 
		if(StringUtils.isNotEmpty(lastTime)){
		    firstParDistributeTime = lastTime;
		}
		for(int i = 1;i < partners.size();i++) {
			jydto = partners.get(i);
			userId = jydto.getParUserId();
			//userId 参与者的当前待办任务数
			userTask = jbpmTaskService.getTaskCunAndLastTimeOfPersonToday(userId);
			int taskCurCun = Integer.parseInt(userTask.get("TASK_CUNT").toString());
			lastTime = (String) userTask.get("CREATE_TIME");
			//当前参与者的上一次分配任务时间
			String curParDistributeTime = "2014-11-27 15:55:45";
			if(StringUtils.isNotEmpty(lastTime)){
				curParDistributeTime = lastTime;
			}else{
				curParDistributeTime = "2012-01-01 00:00:01"; 
			}
			
			if(taskMinCun > taskCurCun) {
				resultUserId = userId;
				taskMinCun = taskCurCun;
				firstParDistributeTime = curParDistributeTime;
			} else if(taskMinCun == taskCurCun) {
				//如果 相同 任务个数 则取 最后一次分配时间 最早的 人员ID
				if(DateUtil.compareDate(firstParDistributeTime, curParDistributeTime) >= 0) {
					resultUserId = userId;
					firstParDistributeTime = curParDistributeTime;
				}
			}
		}
		
		return resultUserId;
	}

}
