package com.jy.platform.jbpm4.jbpmPartner;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jy.platform.jbpm4.dto.JYPartnerDTO;
import com.jy.platform.jbpm4.dto.Jbpm4BizTabDTO;
import com.jy.platform.jbpm4.service.JYSelectPartnerService;
import com.jy.platform.jbpm4.service.impl.Jbpm4BizTabService;
/**
 * 
 * @description:获取 流程实例  发起者
 * task 节点 任务归属  流程实例发起者 时 配置该实现类即可
 * @author chen_gang
 * @date:2014年11月11日下午5:16:54
 */
@Service("com.jy.platform.jbpm4.jbpmPartner.JYObatinStartProInsPartnerImpl")
public class JYObatinStartProInsPartnerImpl implements JYSelectPartnerService ,Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger =  LoggerFactory.getLogger(JYObatinStartProInsPartnerImpl.class);
	//注入工作流与业务表关联表 实现类
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.Jbpm4BizTabService")
	private Jbpm4BizTabService jbpm4BizTabService;
	
	public JYPartnerDTO selectPartnerBySysRule(String bizInfId,String bizTabName, String bizTabId,
			String proInsId,String curActName, Map<String, Object> variables) {
		//todo 通过 bizTabId  获取流程发起者 ID
		String parUserId = "-1";//待分配 通过流程 监控手工 任务分配
		JYPartnerDTO partner = new JYPartnerDTO();
		try {
			if(bizTabId == null || "".equals(bizTabId)) {
				logger.error("JYObatinStartProInsPartnerImpl.selectPartnerBySysRule方法中bizTabId参数为null或空字符串");
			} else {
				Jbpm4BizTabDTO jbpm4BizTabDTO = jbpm4BizTabService.queryJbpm4BizTabByPrimaryKey(bizTabId);
				if(jbpm4BizTabDTO != null) {
					parUserId = jbpm4BizTabDTO.getStartProUserid();
				} else {
					logger.error("JYObatinStartProInsPartnerImpl.selectPartnerBySysRule方法中获取工作流与业务表关联表Jbpm4BizTabDTO对象为null");
				}
			}
		} catch (Exception e) {
			logger.error("JYObatinStartProInsPartnerImpl.selectPartnerBySysRule获取流程发起者出现异常");
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
