package com.fintech.platform.jbpm4.service;

import java.util.List;

import com.fintech.platform.jbpm4.dto.PartnerDTO;
import com.fintech.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;

/**
 * 
 * @Description: 定义jbpm 选择参与者 接口
 * @author
 * @version 1.0, 
 * @date 2014-3-6 下午01:07:22
 */
public interface IPartnerService {
	
	/**
	 * 查询参与者
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public List queryPartnerListInfo(PartnerDTO dto) throws Exception;
	/**
	 * 通过流程节点配置的规则查询 其设置的参与者人选
	 * @param dto
	 * @param formDTO
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List queryPartnerListInfo(PartnerDTO dto, Jbpm4FormInfoDTO formDTO) throws Exception;
}

