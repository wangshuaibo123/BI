package com.fintech.modules.platform.sysorg.dto;

import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;

public class SysPositionChangeDTO{
	
	/**岗位名称*/
	private SysPropertieChange positionName;

	/**岗位编码*/
	private SysPropertieChange positionCode;

	/**有效性状态，1有效，0无效，默认1*/
	private SysPropertieChange validateState;
	
	/**所属机构ID**/
	private SysPropertieChange orgCode;
	
	/**是否负责**/
	private SysPropertieChange isResponsible;
	
	/**岗位序列**/
	private SysPropertieChange positionSequence;
	
	/**岗位**/
	private SysPropertieChange post;
	
	/**上级岗位**/
	private SysPropertieChange parentId;
	
	/**生效日期**/
	private SysPropertieChange effectiveDate;
	
	/** 上级岗位名称**/
	private SysPropertieChange parentName;
	
	/** 上级岗位编码**/
	private SysPropertieChange parentCode;

	String changeType;
	
	String changeTypeCN;
	
	public SysPropertieChange getPositionName() {
		return positionName;
	}

	public void setPositionName(SysPropertieChange positionName) {
		this.positionName = positionName;
	}

	public SysPropertieChange getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(SysPropertieChange positionCode) {
		this.positionCode = positionCode;
	}

	public SysPropertieChange getValidateState() {
		return validateState;
	}

	public void setValidateState(SysPropertieChange validateState) {
		this.validateState = validateState;
	}

	public void setChangeTypeCN(String changeTypeCN) {
		this.changeTypeCN = changeTypeCN;
	}

	public String getChangeType() {
		return changeType;
	}
	
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public String getChangeTypeCN() {
		if(getChangeType().equals("insert")){
			this.changeTypeCN ="新增";
		} else if (changeType.equals("update")) {
			this.changeTypeCN ="更新";
		}
		return changeTypeCN;
	}

	public SysPropertieChange getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(SysPropertieChange orgCode) {
		this.orgCode = orgCode;
	}

	public SysPropertieChange getIsResponsible() {
		return isResponsible;
	}

	public void setIsResponsible(SysPropertieChange isResponsible) {
		this.isResponsible = isResponsible;
	}

	public SysPropertieChange getPositionSequence() {
		return positionSequence;
	}

	public void setPositionSequence(SysPropertieChange positionSequence) {
		this.positionSequence = positionSequence;
	}

	public SysPropertieChange getPost() {
		return post;
	}

	public void setPost(SysPropertieChange post) {
		this.post = post;
	}

	public SysPropertieChange getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(SysPropertieChange effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public static SysPositionChangeDTO getPositionChangeDTO(SysPositionDTO sysPositionDTO ,SysPositionSynDTO sysPositionSynDTO ) throws Exception{
		//创建一个新的sysPositionChangeDTO
		SysPositionChangeDTO sysPositionChangeDTO = new SysPositionChangeDTO();
		//sysPositionDTO不为空且 syPositionSynDTO不为空
		if(sysPositionDTO!=null && sysPositionDTO.getId()!=null && sysPositionSynDTO!=null && sysPositionSynDTO.getPositionId()!=null ){
			sysPositionChangeDTO.setChangeType("update");
			/**岗位名称*/
			sysPositionChangeDTO.setPositionName(new SysPropertieChange(sysPositionDTO.getPositionName(), sysPositionSynDTO.getPositionName()));
			/**岗位编码*/
			sysPositionChangeDTO.setPositionCode(new SysPropertieChange(sysPositionDTO.getPositionCode(), sysPositionSynDTO.getPositionCode()));
			/**有效性状态，1有效，0无效，默认1*/
			sysPositionChangeDTO.setValidateState(new SysPropertieChange(sysPositionDTO.getValidateState(), sysPositionSynDTO.getValidateState()));
			/**生效日期**/
			sysPositionChangeDTO.setEffectiveDate(new SysPropertieChange(sysPositionDTO.getEffectiveDate(),sysPositionSynDTO.getEffectiveDate()));
			/**是否负责**/
			sysPositionChangeDTO.setIsResponsible(new SysPropertieChange(sysPositionDTO.getIsResponsible(),sysPositionSynDTO.getIsResponsible()));
			/**岗位序列**/
			sysPositionChangeDTO.setPositionSequence(new SysPropertieChange(sysPositionDTO.getPositionSequence(),sysPositionSynDTO.getPositionSequence()));
			/**岗位**/
			sysPositionChangeDTO.setPost(new SysPropertieChange(sysPositionDTO.getPost(),sysPositionSynDTO.getPost()));
			/**所属机构ID**/
			sysPositionChangeDTO.setOrgCode(new SysPropertieChange(sysPositionDTO.getOrgCode(),sysPositionSynDTO.getOrgCode()));
			/** 上级岗位名称**/
			sysPositionChangeDTO.setParentName(new SysPropertieChange(sysPositionDTO.getParentName(),sysPositionSynDTO.getParentName()));
			/** 上级岗位编码**/
			sysPositionChangeDTO.setParentCode(new SysPropertieChange(sysPositionDTO.getParentCode(),sysPositionSynDTO.getParentCode()));
		}
		//只有sysPositionDTO
		else if(sysPositionSynDTO!=null && sysPositionSynDTO.getPositionId()!=null ) {
			sysPositionChangeDTO.setChangeType("insert");
			/**岗位名称*/
			sysPositionChangeDTO.setPositionName(new SysPropertieChange(null, sysPositionSynDTO.getPositionName()));
			/**岗位编码*/
			sysPositionChangeDTO.setPositionCode(new SysPropertieChange(null, sysPositionSynDTO.getPositionCode()));
			/**有效性状态，1有效，0无效，默认1*/
			sysPositionChangeDTO.setValidateState(new SysPropertieChange(null, sysPositionSynDTO.getValidateState()));
			/**生效日期**/
			sysPositionChangeDTO.setEffectiveDate(new SysPropertieChange(null,sysPositionSynDTO.getEffectiveDate()));
			/**是否负责**/
			sysPositionChangeDTO.setIsResponsible(new SysPropertieChange(null,sysPositionSynDTO.getIsResponsible()));
			/**岗位序列**/
			sysPositionChangeDTO.setPositionSequence(new SysPropertieChange(null,sysPositionSynDTO.getPositionSequence()));
			/**岗位**/
			sysPositionChangeDTO.setPost(new SysPropertieChange(null,sysPositionSynDTO.getPost()));
			/**所属机构ID**/
			sysPositionChangeDTO.setOrgCode(new SysPropertieChange(null,sysPositionSynDTO.getOrgCode()));
			/** 上级岗位名称**/
			sysPositionChangeDTO.setParentName(new SysPropertieChange(null,sysPositionSynDTO.getParentName()));
			/** 上级岗位编码**/
			sysPositionChangeDTO.setParentCode(new SysPropertieChange(null,sysPositionSynDTO.getParentCode()));
		}
		return sysPositionChangeDTO;
	}

	public SysPropertieChange getParentId() {
		return parentId;
	}

	public void setParentId(SysPropertieChange parentId) {
		this.parentId = parentId;
	}

	public SysPropertieChange getParentName() {
		return parentName;
	}

	public void setParentName(SysPropertieChange parentName) {
		this.parentName = parentName;
	}

	public SysPropertieChange getParentCode() {
		return parentCode;
	}

	public void setParentCode(SysPropertieChange parentCode) {
		this.parentCode = parentCode;
	}
}