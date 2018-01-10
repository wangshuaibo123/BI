package com.fintech.platform.jbpm4.jbpmPartner;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fintech.platform.jbpm4.dto.JYPartnerDTO;
import com.fintech.platform.jbpm4.service.JYSelectPartnerService;
import com.fintech.platform.jbpm4.service.JYTastService;
/**
 * 
 * @description:获取 流程实例某个节点 历史参与者 并返回最后一个参与者 信息
 * @author
 * @date:2015年1月11日下午12:31:41
 */
@Service("com.fintech.platform.jbpm4.jbpmPartner.JYObtainHisPartnerImpl")
public class JYObtainHisPartnerImpl implements JYSelectPartnerService ,Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger =  LoggerFactory.getLogger(JYObtainHisPartnerImpl.class);
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.service.impl.JYTastServiceImpl")
	private JYTastService jyTaskBiz;
	/**
	 * bizInfId 必传
	 * bizTabName 必传
	 * curActName 必传(传递 配置选人规则的 节点名称)
	 */
	public JYPartnerDTO selectPartnerBySysRule(String bizInfId,String bizTabName, String bizTabId,String proInsId,String curActName, Map<String, Object> variables) {
		String parUserId = "-1";//待分配 通过流程 监控手工 任务分配
		JYPartnerDTO partner = new JYPartnerDTO();
		try {
			if(bizInfId == null || "".equals(bizInfId) || "null".equals(bizInfId)) {
				logger.error("JYObtainHisPartnerImpl.selectPartnerBySysRule 方法中bizInfId参数为null或空字符串");
			} else {
				//获取 下一个待办任务 节点名称
				String proNextActName = (String) variables.get("proNextActName");
				parUserId = jyTaskBiz.getHisUserOfActive(bizInfId,bizTabName,proNextActName);
			}
		} catch (Exception e) {
			logger.error("获取业务数据 "+bizTabName+"的"+bizInfId+" 节点审核 "+curActName+"历史参与者 异常");
		}
		partner.setParUserId(parUserId);
		return partner;
	}
	public JYPartnerDTO selectPartnerByDroolsRule(String bizInfId,String bizTabName, String bizTabId,
			String proInsId,String curActName, Map<String, Object> variables) {
		return null;
	}
	public List<JYPartnerDTO> selectPartnerByOperator(String bizInfId,String bizTabName, String bizTabId,
			String proInsId,String curActName, Map<String, Object> variables) {
		return null;
	}

}
