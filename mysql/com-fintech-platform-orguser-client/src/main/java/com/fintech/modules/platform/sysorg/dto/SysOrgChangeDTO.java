package com.fintech.modules.platform.sysorg.dto;

import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;

public class SysOrgChangeDTO{
	/**机构编码*/
	private SysPropertieChange orgCode;
	/**机构名称*/
	private SysPropertieChange orgName;
	/**机构类型：org组织，dept部门*/
	private SysPropertieChange orgType;
	/**父机构ID*/
	private SysPropertieChange parentId;
	/**父机构Name*/
	private SysPropertieChange parentName;
	/**有效性状态，1有效，0无效，默认1*/
	private SysPropertieChange validateState;

	/**是否是虚拟组织，1是，0否，默认0*/
	private SysPropertieChange isVirtual;
	
	/**是否叶子节点 */
	private SysPropertieChange isLeef;
	/**区域编码*/
	private SysPropertieChange areaCodes;
	/**区域地质*/
	private SysPropertieChange areaAdress;
	/**机构层级 **/
	private SysPropertieChange orgLevel;
	/** 生效日期**/
	private SysPropertieChange effectiveDate;
	
	/** 创建日期**/
	private SysPropertieChange createTime;

	String changeType;
	
	String changeTypeCN;
	
	public SysPropertieChange getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(SysPropertieChange orgCode) {
		this.orgCode = orgCode;
	}

	public SysPropertieChange getOrgName() {
		return orgName;
	}

	public void setOrgName(SysPropertieChange orgName) {
		this.orgName = orgName;
	}

	public SysPropertieChange getOrgType() {
		return orgType;
	}

	public void setOrgType(SysPropertieChange orgType) {
		this.orgType = orgType;
	}

	public SysPropertieChange getParentId() {
		return parentId;
	}

	public void setParentId(SysPropertieChange parentId) {
		this.parentId = parentId;
	}

	public SysPropertieChange getValidateState() {
		return validateState;
	}

	public void setValidateState(SysPropertieChange validateState) {
		this.validateState = validateState;
	}

	public SysPropertieChange getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(SysPropertieChange isVirtual) {
		this.isVirtual = isVirtual;
	}

	public String getChangeType() {
		return changeType;
	}
	
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public SysPropertieChange getParentName() {
		return parentName;
	}

	public void setParentName(SysPropertieChange parentName) {
		this.parentName = parentName;
	}
	
	public SysPropertieChange getIsLeef() {
		return isLeef;
	}

	public void setIsLeef(SysPropertieChange isLeef) {
		this.isLeef = isLeef;
	}

	public SysPropertieChange getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(SysPropertieChange areaCodes) {
		this.areaCodes = areaCodes;
	}

	public SysPropertieChange getAreaAdress() {
		return areaAdress;
	}

	public void setAreaAdress(SysPropertieChange areaAdress) {
		this.areaAdress = areaAdress;
	}

	public String getChangeTypeCN() {
		if(getChangeType().equals("insert")){
			this.changeTypeCN ="新增";
		} else if (changeType.equals("update")) {
			this.changeTypeCN ="更新";
		}
		return changeTypeCN;
	}

	public SysPropertieChange getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(SysPropertieChange orgLevel) {
		this.orgLevel = orgLevel;
	}

	public SysPropertieChange getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(SysPropertieChange effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public static SysOrgChangeDTO getOrgChangeDTO(SysOrgDTO sysOrgDTO ,String sysOrgParentName , SysOrgSynDTO sysOrgSynDTO , String sysOrgSynParentName) throws Exception{
		//创建一个新的sysOrgChangeDTO
		SysOrgChangeDTO sysOrgChangeDTO = new SysOrgChangeDTO();
		//sysOrgDTO不为空且 syOrgSynDTO不为空
		if(sysOrgDTO!=null && sysOrgDTO.getId()!=null && sysOrgSynDTO!=null && sysOrgSynDTO.getOrgId()!=null ){
			sysOrgChangeDTO.setChangeType("update");
			/**机构编码*/
			sysOrgChangeDTO.setOrgCode(new SysPropertieChange(sysOrgDTO.getOrgCode(), sysOrgSynDTO.getOrgCode()));;
			/**机构名称*/
			sysOrgChangeDTO.setOrgName(new SysPropertieChange(sysOrgDTO.getOrgName(), sysOrgSynDTO.getOrgName()));;
			/**机构类型：org组织，dept部门*/
			sysOrgChangeDTO.setOrgType(new SysPropertieChange(sysOrgDTO.getOrgType(), sysOrgSynDTO.getOrgType()));;
			/**父机构ID*/
			sysOrgChangeDTO.setParentId(new SysPropertieChange(sysOrgDTO.getParentId(), sysOrgSynDTO.getParentId()));;
			/**父机构Name*/
			sysOrgChangeDTO.setParentName(new SysPropertieChange(sysOrgParentName, sysOrgSynParentName));
			/**有效性状态，1有效，0无效，默认1*/
			sysOrgChangeDTO.setValidateState(new SysPropertieChange(sysOrgDTO.getValidateState(), sysOrgSynDTO.getValidateState()));;
			/**是否是虚拟组织，1是，0否，默认0*/
			sysOrgChangeDTO.setIsVirtual(new SysPropertieChange(sysOrgDTO.getIsVirtual(), sysOrgSynDTO.getIsVirtual()));;
			/**是否叶子节点 */
			sysOrgChangeDTO.setIsLeef(new SysPropertieChange(sysOrgDTO.getIsLeef(), sysOrgSynDTO.getIsLeef()));
			/**区域编码*/
			sysOrgChangeDTO.setAreaCodes(new SysPropertieChange(sysOrgDTO.getAreaCodes(), sysOrgSynDTO.getAreaCodes()));
			/**区域地质*/
			sysOrgChangeDTO.setAreaAdress(new SysPropertieChange(sysOrgDTO.getAreaAdress(), sysOrgSynDTO.getAreaAdress()));
			/**机构层级 **/
			sysOrgChangeDTO.setOrgLevel(new SysPropertieChange(sysOrgDTO.getOrgLevel(),sysOrgSynDTO.getOrgLevel()));
			/** 生效日期**/
			sysOrgChangeDTO.setEffectiveDate(new SysPropertieChange(sysOrgDTO.getEffectiveDate(),sysOrgSynDTO.getEffectiveDate()));
			/** 创建日期**/
			sysOrgChangeDTO.setCreateTime(new SysPropertieChange(sysOrgDTO.getCreateTime(),sysOrgSynDTO.getCreateTime()));
		}
		//只有syOrgSynDTO
		else if(sysOrgSynDTO!=null && sysOrgSynDTO.getOrgId()!=null ) {
			sysOrgChangeDTO.setChangeType("insert");
			/**机构编码*/
			sysOrgChangeDTO.setOrgCode(new SysPropertieChange(null, sysOrgSynDTO.getOrgCode()));;
			/**机构名称*/
			sysOrgChangeDTO.setOrgName(new SysPropertieChange(null, sysOrgSynDTO.getOrgName()));;
			/**机构类型：org组织，dept部门*/
			sysOrgChangeDTO.setOrgType(new SysPropertieChange(null, sysOrgSynDTO.getOrgType()));;
			/**父机构ID*/
			sysOrgChangeDTO.setParentId(new SysPropertieChange(null, sysOrgSynDTO.getParentId()));;
			/**父机构Name*/
			sysOrgChangeDTO.setParentName(new SysPropertieChange(null, sysOrgSynParentName ));
			/**有效性状态，1有效，0无效，默认1*/
			sysOrgChangeDTO.setValidateState(new SysPropertieChange(null, sysOrgSynDTO.getValidateState()));;
			/**是否是虚拟组织，1是，0否，默认0*/
			sysOrgChangeDTO.setIsVirtual(new SysPropertieChange(null, sysOrgSynDTO.getIsVirtual()));;
			/**是否叶子节点 */
			sysOrgChangeDTO.setIsLeef(new SysPropertieChange(null, sysOrgSynDTO.getIsLeef()));
			/**区域编码*/
			sysOrgChangeDTO.setAreaCodes(new SysPropertieChange(null, sysOrgSynDTO.getAreaCodes()));
			/**区域地质*/
			sysOrgChangeDTO.setAreaAdress(new SysPropertieChange(null, sysOrgSynDTO.getAreaAdress()));
			/**机构层级 **/
			sysOrgChangeDTO.setOrgLevel(new SysPropertieChange(null,sysOrgSynDTO.getOrgLevel()));
			/** 生效日期**/
			sysOrgChangeDTO.setEffectiveDate(new SysPropertieChange(null,sysOrgSynDTO.getEffectiveDate()));
			/** 创建日期**/
			sysOrgChangeDTO.setCreateTime(new SysPropertieChange(null,sysOrgSynDTO.getCreateTime()));
		}
		return sysOrgChangeDTO;
	}

	public SysPropertieChange getCreateTime() {
		return createTime;
	}

	public void setCreateTime(SysPropertieChange createTime) {
		this.createTime = createTime;
	}
}