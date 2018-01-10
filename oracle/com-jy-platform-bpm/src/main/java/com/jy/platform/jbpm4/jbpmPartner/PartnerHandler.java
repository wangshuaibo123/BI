package com.jy.platform.jbpm4.jbpmPartner;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jy.platform.jbpm4.dto.PartnerDTO;
import com.jy.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;

@Service("com.jy.platform.jbpm4.jbpmPartner.PartnerHandler")
public abstract  class PartnerHandler {
	
	//持有下一个处理请求的对象
    protected PartnerHandler successor = null;

	public PartnerHandler getSuccessor() {
		return successor;
	}

	public void setSuccessor(PartnerHandler successor) {
		this.successor = successor;
	}
    
	/**
	 * 定义 查询相关参与者的 抽象接口
	 * @param dto
	 * @param formDTO
	 * @param page
	 * @param operate 选择参与者的处理类
	 * @param otherSqlParam 其他选择参与者规则信息 用于sql的控制
	 * @return
	 * @throws Exception 
	 */
	public abstract List<Map> getRelationPartners(PartnerDTO dto, Jbpm4FormInfoDTO formDTO,String operate,Map<String,Object> otherSqlParam) throws Exception ;

}

