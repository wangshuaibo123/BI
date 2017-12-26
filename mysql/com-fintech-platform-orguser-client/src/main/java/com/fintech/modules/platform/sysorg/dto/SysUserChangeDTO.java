package com.fintech.modules.platform.sysorg.dto;

import com.fintech.modules.platform.sysorg.dto.SysUserDTO;

/**
 *@Description:系统用户表
 *@author
 *@version 1.0,
 *@date 2015-01-17 17:09:03
 */
public class SysUserChangeDTO{

	/**姓名*/
	private SysPropertieChange userName ;

	/**编码*/
	private SysPropertieChange userNo ;

	/**登录名*/
	private SysPropertieChange loginName ;

	/**登录密码*/
	private SysPropertieChange password ;

	/**手机*/
	private SysPropertieChange mobile ;

	/**邮件*/
	private SysPropertieChange email ;

	/**用户头像*/
	private SysPropertieChange userImage ;

	/**性别*/
	private SysPropertieChange sex ;

	/**出生日期*/
	private SysPropertieChange birthday ;

	/**民族*/
	private SysPropertieChange nationality ;

	/**学历*/
	private SysPropertieChange education ;

	/**职务*/
	private SysPropertieChange job ;

	/**家庭住址*/
	private SysPropertieChange homeAddress ;

	/**家庭邮编*/
	private SysPropertieChange homeZipcode ;

	/**家庭电话*/
	private SysPropertieChange homeTel ;

	/**办公电话*/
	private SysPropertieChange officeTel ;

	/**办公地址*/
	private SysPropertieChange officeAddress ;

	/**有效性状态，1有效，0无效，默认1*/
	private SysPropertieChange validateState ;

	/**是否锁定，1锁定，0未锁，默认0*/
	private SysPropertieChange isLocked ;
	
	/**兼职机构*/
	//private List<SysOrgUserDTO> sysOrgUserDTOs = new ArrayList<SysOrgUserDTO>();
	
	/**证件号码**/
	private SysPropertieChange cardNo;
	
	/**试用期**/
	private SysPropertieChange probationPeriod;
	
	/**入职日期**/
	private SysPropertieChange entryDate;
	
	/**离职日期**/
	private SysPropertieChange quitDate;
	
	/**参加工作日期**/
	private SysPropertieChange workDate;
	
	/**政治面貌**/
	private SysPropertieChange politicalStatus;

	/**员工关系**/
	private SysPropertieChange userRelation;
	
	/**标准年假**/
	private SysPropertieChange annualLeave;
	
	/**基薪职级**/
	private SysPropertieChange jxzj;
	
	/**年假起始日期**/
	private SysPropertieChange njqsrq;
	
	String changeType;
	
	String changeTypeCN;
	
	public SysPropertieChange getJxzj() {
		return jxzj;
	}

	public void setJxzj(SysPropertieChange jxzj) {
		this.jxzj = jxzj;
	}

	public SysPropertieChange getNjqsrq() {
		return njqsrq;
	}

	public void setNjqsrq(SysPropertieChange njqsrq) {
		this.njqsrq = njqsrq;
	}
	
	public SysPropertieChange getUserName() {
		return userName;
	}

	public void setUserName(SysPropertieChange userName) {
		this.userName = userName;
	}

	public SysPropertieChange getUserNo() {
		return userNo;
	}

	public void setUserNo(SysPropertieChange userNo) {
		this.userNo = userNo;
	}

	public SysPropertieChange getLoginName() {
		return loginName;
	}

	public void setLoginName(SysPropertieChange loginName) {
		this.loginName = loginName;
	}

	public SysPropertieChange getPassword() {
		return password;
	}

	public void setPassword(SysPropertieChange password) {
		this.password = password;
	}

	public SysPropertieChange getMobile() {
		return mobile;
	}

	public void setMobile(SysPropertieChange mobile) {
		this.mobile = mobile;
	}

	public SysPropertieChange getEmail() {
		return email;
	}

	public void setEmail(SysPropertieChange email) {
		this.email = email;
	}

	public SysPropertieChange getUserImage() {
		return userImage;
	}

	public void setUserImage(SysPropertieChange userImage) {
		this.userImage = userImage;
	}

	public SysPropertieChange getSex() {
		return sex;
	}

	public void setSex(SysPropertieChange sex) {
		this.sex = sex;
	}

	public SysPropertieChange getBirthday() {
		return birthday;
	}

	public void setBirthday(SysPropertieChange birthday) {
		this.birthday = birthday;
	}

	public SysPropertieChange getNationality() {
		return nationality;
	}

	public void setNationality(SysPropertieChange nationality) {
		this.nationality = nationality;
	}

	public SysPropertieChange getEducation() {
		return education;
	}

	public void setEducation(SysPropertieChange education) {
		this.education = education;
	}

	public SysPropertieChange getJob() {
		return job;
	}

	public void setJob(SysPropertieChange job) {
		this.job = job;
	}

	public SysPropertieChange getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(SysPropertieChange homeAddress) {
		this.homeAddress = homeAddress;
	}

	public SysPropertieChange getHomeZipcode() {
		return homeZipcode;
	}

	public void setHomeZipcode(SysPropertieChange homeZipcode) {
		this.homeZipcode = homeZipcode;
	}

	public SysPropertieChange getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(SysPropertieChange homeTel) {
		this.homeTel = homeTel;
	}

	public SysPropertieChange getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(SysPropertieChange officeTel) {
		this.officeTel = officeTel;
	}

	public SysPropertieChange getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(SysPropertieChange officeAddress) {
		this.officeAddress = officeAddress;
	}

	public SysPropertieChange getValidateState() {
		return validateState;
	}

	public void setValidateState(SysPropertieChange validateState) {
		this.validateState = validateState;
	}

	public SysPropertieChange getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(SysPropertieChange isLocked) {
		this.isLocked = isLocked;
	}

	public String getChangeType() {
		return changeType;
	}
	
	public String getChangeTypeCN() {
		if(getChangeType().equals("insert")){
			this.changeTypeCN ="新增";
		} else if (changeType.equals("update")) {
			this.changeTypeCN ="更新";
		}
		return changeTypeCN;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	
	public SysPropertieChange getCardNo() {
		return cardNo;
	}

	public void setCardNo(SysPropertieChange cardNo) {
		this.cardNo = cardNo;
	}

	public SysPropertieChange getProbationPeriod() {
		return probationPeriod;
	}

	public void setProbationPeriod(SysPropertieChange probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	public SysPropertieChange getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(SysPropertieChange entryDate) {
		this.entryDate = entryDate;
	}

	public SysPropertieChange getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(SysPropertieChange quitDate) {
		this.quitDate = quitDate;
	}

	public SysPropertieChange getWorkDate() {
		return workDate;
	}

	public void setWorkDate(SysPropertieChange workDate) {
		this.workDate = workDate;
	}

	public SysPropertieChange getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(SysPropertieChange politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public SysPropertieChange getUserRelation() {
		return userRelation;
	}

	public void setUserRelation(SysPropertieChange userRelation) {
		this.userRelation = userRelation;
	}

	public static SysUserChangeDTO getUserChangeDTO(SysUserDTO sysUserDTO , SysUserSynDTO sysUserSynDTO) throws Exception{
		
		//创建一个新的sysUserChangeDTO
		SysUserChangeDTO sysUserChangeDTO = new SysUserChangeDTO();
		
		//sysUserDTO不为空且 syUserSynDTO不为空
		if(sysUserDTO!=null && sysUserDTO.getId()!=null && sysUserSynDTO!=null && sysUserSynDTO.getUserId()!=null ){
			sysUserChangeDTO.setChangeType("update");
			/**姓名*/
			sysUserChangeDTO.setUserName(new SysPropertieChange(sysUserDTO.getUserName(), sysUserSynDTO.getUserName()));;
			/**编码*/
			sysUserChangeDTO.setUserNo(new SysPropertieChange(sysUserDTO.getUserNo(), sysUserSynDTO.getUserNo())) ;
			/**登录名*/
			sysUserChangeDTO.setLoginName(new SysPropertieChange(sysUserDTO.getLoginName(), sysUserSynDTO.getLoginName())) ;
			/**登录密码*/
			sysUserChangeDTO.setPassword(new SysPropertieChange(sysUserDTO.getPassword(), sysUserSynDTO.getPassword())) ;
			/**手机*/
			sysUserChangeDTO.setMobile (new SysPropertieChange(sysUserDTO.getMobile(), sysUserSynDTO.getMobile()));
			/**邮件*/
			sysUserChangeDTO.setEmail (new SysPropertieChange(sysUserDTO.getEmail(), sysUserSynDTO.getEmail()));
			/**用户头像*/
			sysUserChangeDTO.setUserImage(new SysPropertieChange(sysUserDTO.getUserImage(), sysUserSynDTO.getUserImage())) ;
			/**性别*/
			sysUserChangeDTO.setSex(new SysPropertieChange(sysUserDTO.getSex(), sysUserSynDTO.getSex()))  ;
			/**出生日期*/
			sysUserChangeDTO.setBirthday (new SysPropertieChange(sysUserDTO.getBirthday(), sysUserSynDTO.getBirthday()));
			/**民族*/
			sysUserChangeDTO.setNationality(new SysPropertieChange(sysUserDTO.getNationality(), sysUserSynDTO.getNationality())) ;
			/**学历*/
			sysUserChangeDTO.setEducation (new SysPropertieChange(sysUserDTO.getEducation(), sysUserSynDTO.getEducation()));
			/**职务*/
			sysUserChangeDTO.setJob(new SysPropertieChange(sysUserDTO.getJob(), sysUserSynDTO.getJob())) ;
			/**家庭住址*/
			sysUserChangeDTO.setHomeAddress(new SysPropertieChange(sysUserDTO.getHomeAddress(), sysUserSynDTO.getHomeAddress())) ;
			/**家庭邮编*/
			sysUserChangeDTO.setHomeZipcode (new SysPropertieChange(sysUserDTO.getHomeZipcode(), sysUserSynDTO.getHomeZipcode()));
			/**家庭电话*/
			sysUserChangeDTO.setHomeTel (new SysPropertieChange(sysUserDTO.getHomeTel(), sysUserSynDTO.getHomeTel()));
			/**办公电话*/
			sysUserChangeDTO.setOfficeTel (new SysPropertieChange(sysUserDTO.getOfficeTel(), sysUserSynDTO.getOfficeTel()));
			/**办公地址*/
			sysUserChangeDTO.setOfficeAddress(new SysPropertieChange(sysUserDTO.getOfficeAddress(), sysUserSynDTO.getOfficeAddress())) ;
			/**有效性状态，1有效，0无效，默认1*/
			sysUserChangeDTO.setValidateState (new SysPropertieChange(sysUserDTO.getValidateState(), sysUserSynDTO.getValidateState()));
			/**是否锁定，1锁定，0未锁，默认0*/
			sysUserChangeDTO.setIsLocked (new SysPropertieChange(sysUserDTO.getIsLocked(), sysUserSynDTO.getIsLocked()));
			/**证件号码**/
			sysUserChangeDTO.setCardNo(new SysPropertieChange(sysUserDTO.getCardNo(),sysUserSynDTO.getCardNo()));
			/**政治面貌**/
			sysUserChangeDTO.setPoliticalStatus(new SysPropertieChange(sysUserDTO.getPoliticalStatus(),sysUserSynDTO.getPoliticalStatus()));
			/**试用期**/
			sysUserChangeDTO.setProbationPeriod(new SysPropertieChange(sysUserDTO.getProbationPeriod(),sysUserSynDTO.getProbationPeriod()));
			/**员工关系**/
			sysUserChangeDTO.setUserRelation(new SysPropertieChange(sysUserDTO.getUserRelation(),sysUserSynDTO.getUserRelation()));
			/**入职日期**/
			sysUserChangeDTO.setEntryDate(new SysPropertieChange(sysUserDTO.getEntryDate(),sysUserSynDTO.getEntryDate()));
			/**离职日期**/
			sysUserChangeDTO.setQuitDate(new SysPropertieChange(sysUserDTO.getQuitDate(),sysUserSynDTO.getQuitDate()));
			/**参加工作日期**/
			sysUserChangeDTO.setWorkDate(new SysPropertieChange(sysUserDTO.getWorkDate(),sysUserSynDTO.getWorkDate()));
			/**标准年假**/
			sysUserChangeDTO.setAnnualLeave(new SysPropertieChange(sysUserDTO.getAnnualLeave(),sysUserSynDTO.getAnnualLeave()));
			/**基薪职级**/
			sysUserChangeDTO.setJxzj(new SysPropertieChange(sysUserDTO.getJxzj(),sysUserSynDTO.getJxzj()));
			/**起始年假**/
			sysUserChangeDTO.setNjqsrq(new SysPropertieChange(sysUserDTO.getNjqsrq(),sysUserSynDTO.getNjqsrq()));
		}
		//只有syUserSynDTO
		else if(sysUserSynDTO!=null && sysUserSynDTO.getUserId()!=null ) {
			sysUserChangeDTO.setChangeType("insert");
			/**姓名*/
			sysUserChangeDTO.setUserName(new SysPropertieChange(null, sysUserSynDTO.getUserName()));;
			/**编码*/
			sysUserChangeDTO.setUserNo(new SysPropertieChange(null, sysUserSynDTO.getUserNo())) ;
			/**登录名*/
			sysUserChangeDTO.setLoginName(new SysPropertieChange(null, sysUserSynDTO.getLoginName())) ;
			/**登录密码*/
			sysUserChangeDTO.setPassword(new SysPropertieChange(null, sysUserSynDTO.getPassword())) ;
			/**手机*/
			sysUserChangeDTO.setMobile (new SysPropertieChange(null, sysUserSynDTO.getMobile()));
			/**邮件*/
			sysUserChangeDTO.setEmail (new SysPropertieChange(null, sysUserSynDTO.getEmail()));
			/**用户头像*/
			sysUserChangeDTO.setUserImage(new SysPropertieChange(null, sysUserSynDTO.getUserImage())) ;
			/**性别*/
			sysUserChangeDTO.setSex(new SysPropertieChange(null, sysUserSynDTO.getSex()))  ;
			/**出生日期*/
			sysUserChangeDTO.setBirthday (new SysPropertieChange(null, sysUserSynDTO.getBirthday()));
			/**民族*/
			sysUserChangeDTO.setNationality(new SysPropertieChange(null, sysUserSynDTO.getNationality())) ;
			/**学历*/
			sysUserChangeDTO.setEducation (new SysPropertieChange(null, sysUserSynDTO.getEducation()));
			/**职务*/
			sysUserChangeDTO.setJob(new SysPropertieChange(null, sysUserSynDTO.getJob())) ;
			/**家庭住址*/
			sysUserChangeDTO.setHomeAddress(new SysPropertieChange(null, sysUserSynDTO.getHomeAddress())) ;
			/**家庭邮编*/
			sysUserChangeDTO.setHomeZipcode (new SysPropertieChange(null, sysUserSynDTO.getHomeZipcode()));
			/**家庭电话*/
			sysUserChangeDTO.setHomeTel (new SysPropertieChange(null, sysUserSynDTO.getHomeTel()));
			/**办公电话*/
			sysUserChangeDTO.setOfficeTel (new SysPropertieChange(null, sysUserSynDTO.getOfficeTel()));
			/**办公地址*/
			sysUserChangeDTO.setOfficeAddress(new SysPropertieChange(null, sysUserSynDTO.getOfficeAddress())) ;
			/**有效性状态，1有效，0无效，默认1*/
			sysUserChangeDTO.setValidateState (new SysPropertieChange(null, sysUserSynDTO.getValidateState()));
			/**是否锁定，1锁定，0未锁，默认0*/
			sysUserChangeDTO.setIsLocked (new SysPropertieChange(null, sysUserSynDTO.getIsLocked()));
			/**证件号码**/
			sysUserChangeDTO.setCardNo(new SysPropertieChange(null,sysUserSynDTO.getCardNo()));
			/**政治面貌**/
			sysUserChangeDTO.setPoliticalStatus(new SysPropertieChange(null,sysUserSynDTO.getPoliticalStatus()));
			/**试用期**/
			sysUserChangeDTO.setProbationPeriod(new SysPropertieChange(null,sysUserSynDTO.getProbationPeriod()));
			/**员工关系**/
			sysUserChangeDTO.setUserRelation(new SysPropertieChange(null,sysUserSynDTO.getUserRelation()));
			/**入职日期**/
			sysUserChangeDTO.setEntryDate(new SysPropertieChange(null,sysUserSynDTO.getEntryDate()));
			/**离职日期**/
			sysUserChangeDTO.setQuitDate(new SysPropertieChange(null,sysUserSynDTO.getQuitDate()));
			/**参加工作日期**/
			sysUserChangeDTO.setWorkDate(new SysPropertieChange(null,sysUserSynDTO.getWorkDate()));
			/**标准年假**/
			sysUserChangeDTO.setAnnualLeave(new SysPropertieChange(null,sysUserSynDTO.getAnnualLeave()));
			/**基薪职级**/
			sysUserChangeDTO.setJxzj(new SysPropertieChange(null,sysUserSynDTO.getJxzj()));
			/**起始年假**/
			sysUserChangeDTO.setNjqsrq(new SysPropertieChange(null,sysUserSynDTO.getNjqsrq()));
		}
		return sysUserChangeDTO;
	}

	public SysPropertieChange getAnnualLeave() {
		return annualLeave;
	}

	public void setAnnualLeave(SysPropertieChange annualLeave) {
		this.annualLeave = annualLeave;
	}
}