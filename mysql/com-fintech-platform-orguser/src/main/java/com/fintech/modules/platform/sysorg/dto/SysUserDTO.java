package com.fintech.modules.platform.sysorg.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fintech.platform.core.common.BaseDTO;
/**
 *@Description:系统用户表
 *@author
 *@version 1.0,
 *@date 2014-10-15 10:25:49
 */
public class SysUserDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**主键*/
	private java.lang.Long id;

	/**姓名*/
	private java.lang.String userName;

	/**编码*/
	private java.lang.String userNo;

	/**登录名*/
	private java.lang.String loginName;

	/**登录密码*/
	private java.lang.String password;

	/**盐值*/
	private java.lang.String salt;

	/**手机*/
	private java.lang.String mobile;

	/**邮件*/
	private java.lang.String email;

	/**用户头像*/
	private java.lang.String userImage;

	/**性别*/
	private java.lang.String sex;
	private java.lang.String sexCN;

	/**出生日期*/
	private java.lang.String birthday;

	/**民族*/
	private java.lang.String nationality;

	/**学历*/
	private java.lang.String education;

	/**职务*/
	private java.lang.String job;

	/**家庭住址*/
	private java.lang.String homeAddress;

	/**家庭邮编*/
	private java.lang.String homeZipcode;

	/**家庭电话*/
	private java.lang.String homeTel;

	/**办公电话*/
	private java.lang.String officeTel;

	/**办公地址*/
	private java.lang.String officeAddress;

	/**排序*/
	private java.lang.String orderBy;

	/**有效性状态，1有效，0无效，默认1*/
	private java.lang.String validateState;
	private java.lang.String validateStateCN;

	/**是否锁定，1锁定，0未锁，默认0*/
	private java.lang.String isLocked;
	private java.lang.String isLockedCN;

	/**乐观锁*/
	private java.lang.Long version;
	
	/**岗位名称中文显示**/
	private java.lang.String positionCN;
	
	/**机构名称中文显示**/
	private java.lang.String orgCN;
	
	/**是否变更密码[1:是;0:否]**/
	private java.lang.String isChange;
	
	/**兼职机构*/
	private List<SysOrgUserDTO> sysOrgUserDTOs = new ArrayList<SysOrgUserDTO>();
	
	/**证件号码**/
	private java.lang.String cardNo;
	
	/**试用期**/
	private java.lang.Long probationPeriod;
	
	/**入职日期**/
	private java.util.Date entryDate;
	
	/**离职日期**/
	private java.util.Date quitDate;
	
	/**参加工作日期**/
	private java.util.Date workDate;
	
	/**政治面貌**/
	private java.lang.String politicalStatus;

	/**员工关系**/
	private java.lang.String userRelation;
	
	/**创建时间**/
	private java.util.Date createDate;
	
	/**标准年假**/
	private java.lang.Float annualLeave;
	
	/**
	 * 	基薪职级
	 */
	private java.lang.String jxzj;
	

	/**
	 * 年假起始日期 
	 */
	private java.util.Date njqsrq;
	
	
	public java.lang.String getJxzj() {
		return jxzj;
	}

	public void setJxzj(java.lang.String jxzj) {
		this.jxzj = jxzj;
	}


	
	

	public java.util.Date getNjqsrq() {
		return njqsrq;
	}

	public void setNjqsrq(java.util.Date njqsrq) {
		this.njqsrq = njqsrq;
	}

	/**
	 *方法: 获得id
	 *@return: java.lang.Long  id
	 */
	public java.lang.Long getId(){
		return this.id;
	}

	/**
	 *方法: 设置id
	 *@param: java.lang.Long  id
	 */
	public void setId(java.lang.Long id){
		this.id = id;
	}

	/**
	 *方法: 获得userName
	 *@return: java.lang.String  userName
	 */
	public java.lang.String getUserName(){
		return this.userName;
	}

	/**
	 *方法: 设置userName
	 *@param: java.lang.String  userName
	 */
	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}

	/**
	 *方法: 获得userNo
	 *@return: java.lang.String  userNo
	 */
	public java.lang.String getUserNo(){
		return this.userNo;
	}

	/**
	 *方法: 设置userNo
	 *@param: java.lang.String  userNo
	 */
	public void setUserNo(java.lang.String userNo){
		this.userNo = userNo;
	}

	/**
	 *方法: 获得loginName
	 *@return: java.lang.String  loginName
	 */
	public java.lang.String getLoginName(){
		return this.loginName;
	}

	/**
	 *方法: 设置loginName
	 *@param: java.lang.String  loginName
	 */
	public void setLoginName(java.lang.String loginName){
		this.loginName = loginName;
	}

	/**
	 *方法: 获得password
	 *@return: java.lang.String  password
	 */
	public java.lang.String getPassword(){
		return this.password;
	}

	/**
	 *方法: 设置password
	 *@param: java.lang.String  password
	 */
	public void setPassword(java.lang.String password){
		this.password = password;
	}

	/**
	 *方法: 获得salt
	 *@return: java.lang.String  salt
	 */
	public java.lang.String getSalt(){
		return this.salt;
	}

	/**
	 *方法: 设置salt
	 *@param: java.lang.String  salt
	 */
	public void setSalt(java.lang.String salt){
		this.salt = salt;
	}

	/**
	 *方法: 获得mobile
	 *@return: java.lang.String  mobile
	 */
	public java.lang.String getMobile(){
		return this.mobile;
	}

	/**
	 *方法: 设置mobile
	 *@param: java.lang.String  mobile
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}

	/**
	 *方法: 获得email
	 *@return: java.lang.String  email
	 */
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置email
	 *@param: java.lang.String  email
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}

	/**
	 *方法: 获得userImage
	 *@return: java.lang.String  userImage
	 */
	public java.lang.String getUserImage(){
		return this.userImage;
	}

	/**
	 *方法: 设置userImage
	 *@param: java.lang.String  userImage
	 */
	public void setUserImage(java.lang.String userImage){
		this.userImage = userImage;
	}

	/**
	 *方法: 获得sex
	 *@return: java.lang.String  sex
	 */
	public java.lang.String getSex(){
		return this.sex;
	}
	
	/**方法: 获得sexCN
	 * @return
	 */
	public java.lang.String getSexCN(){
		if("1".equals(getSex())){
			this.sexCN = "男";
		}else if ("0".equals(getSex())) {
			this.sexCN = "女";
		}
		return this.sexCN;
	}

	/**
	 *方法: 设置sex
	 *@param: java.lang.String  sex
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}

	/**
	 *方法: 获得birthday
	 *@return: java.lang.String  birthday
	 */
	public java.lang.String getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置birthday
	 *@param: java.lang.String  birthday
	 */
	public void setBirthday(java.lang.String birthday){
		this.birthday = birthday;
	}

	/**
	 *方法: 获得nationality
	 *@return: java.lang.String  nationality
	 */
	public java.lang.String getNationality(){
		return this.nationality;
	}

	/**
	 *方法: 设置nationality
	 *@param: java.lang.String  nationality
	 */
	public void setNationality(java.lang.String nationality){
		this.nationality = nationality;
	}

	/**
	 *方法: 获得education
	 *@return: java.lang.String  education
	 */
	public java.lang.String getEducation(){
		return this.education;
	}

	/**
	 *方法: 设置education
	 *@param: java.lang.String  education
	 */
	public void setEducation(java.lang.String education){
		this.education = education;
	}

	/**
	 *方法: 获得job
	 *@return: java.lang.String  job
	 */
	public java.lang.String getJob(){
		return this.job;
	}

	/**
	 *方法: 设置job
	 *@param: java.lang.String  job
	 */
	public void setJob(java.lang.String job){
		this.job = job;
	}

	/**
	 *方法: 获得homeAddress
	 *@return: java.lang.String  homeAddress
	 */
	public java.lang.String getHomeAddress(){
		return this.homeAddress;
	}

	/**
	 *方法: 设置homeAddress
	 *@param: java.lang.String  homeAddress
	 */
	public void setHomeAddress(java.lang.String homeAddress){
		this.homeAddress = homeAddress;
	}

	/**
	 *方法: 获得homeZipcode
	 *@return: java.lang.String  homeZipcode
	 */
	public java.lang.String getHomeZipcode(){
		return this.homeZipcode;
	}

	/**
	 *方法: 设置homeZipcode
	 *@param: java.lang.String  homeZipcode
	 */
	public void setHomeZipcode(java.lang.String homeZipcode){
		this.homeZipcode = homeZipcode;
	}

	/**
	 *方法: 获得homeTel
	 *@return: java.lang.String  homeTel
	 */
	public java.lang.String getHomeTel(){
		return this.homeTel;
	}

	/**
	 *方法: 设置homeTel
	 *@param: java.lang.String  homeTel
	 */
	public void setHomeTel(java.lang.String homeTel){
		this.homeTel = homeTel;
	}

	/**
	 *方法: 获得officeTel
	 *@return: java.lang.String  officeTel
	 */
	public java.lang.String getOfficeTel(){
		return this.officeTel;
	}

	/**
	 *方法: 设置officeTel
	 *@param: java.lang.String  officeTel
	 */
	public void setOfficeTel(java.lang.String officeTel){
		this.officeTel = officeTel;
	}

	/**
	 *方法: 获得officeAddress
	 *@return: java.lang.String  officeAddress
	 */
	public java.lang.String getOfficeAddress(){
		return this.officeAddress;
	}

	/**
	 *方法: 设置officeAddress
	 *@param: java.lang.String  officeAddress
	 */
	public void setOfficeAddress(java.lang.String officeAddress){
		this.officeAddress = officeAddress;
	}

	/**
	 *方法: 获得orderBy
	 *@return: java.lang.String  orderBy
	 */
	public java.lang.String getOrderBy(){
		return this.orderBy;
	}

	/**
	 *方法: 设置orderBy
	 *@param: java.lang.String  orderBy
	 */
	public void setOrderBy(java.lang.String orderBy){
		this.orderBy = orderBy;
	}

	/**
	 *方法: 获得validateState
	 *@return: java.lang.String  validateState
	 */
	public java.lang.String getValidateState(){
		return this.validateState;
	}

	/**方法: 获得validateStateCN
	 * @return
	 */
	public java.lang.String getValidateStateCN(){
		if("1".equals(getValidateState())){
			this.validateStateCN = "有效";
		}else if ("0".equals(getValidateState())) {
			this.validateStateCN = "无效";
		}
		return this.validateStateCN;
	}
	
	/**
	 *方法: 设置validateState
	 *@param: java.lang.String  validateState
	 */
	public void setValidateState(java.lang.String validateState){
		this.validateState = validateState;
	}

	/**
	 *方法: 获得isLocked
	 *@return: java.lang.String  isLocked
	 */
	public java.lang.String getIsLocked(){
		return this.isLocked;
	}
	
	/**方法: 获得IsLockedCN
	 * @return
	 */
	public java.lang.String getIsLockedCN(){
		if("1".equals(getIsLocked())){
			this.isLockedCN = "锁定";
		}else if ("0".equals(getIsLocked())) {
			this.isLockedCN = "未锁定";
		}
		return this.isLockedCN;
	}

	/**
	 *方法: 设置isLocked
	 *@param: java.lang.String  isLocked
	 */
	public void setIsLocked(java.lang.String isLocked){
		this.isLocked = isLocked;
	}

	/**
	 *方法: 获得version
	 *@return: java.lang.Long  version
	 */
	public java.lang.Long getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置version
	 *@param: java.lang.Long  version
	 */
	public void setVersion(java.lang.Long version){
		this.version = version;
	}
	
	public static long generatorVersion(){
		return new Date().getTime();
	}

	public List<SysOrgUserDTO> getSysOrgUserDTOs() {
		return sysOrgUserDTOs;
	}

	public void setSysOrgUserDTOs(List<SysOrgUserDTO> sysOrgUserDTOs) {
		this.sysOrgUserDTOs = sysOrgUserDTOs;
	}

	public java.lang.String getPositionCN() {
		return positionCN;
	}

	public void setPositionCN(java.lang.String positionCN) {
		this.positionCN = positionCN;
	}

	public java.lang.String getOrgCN() {
		return orgCN;
	}

	public void setOrgCN(java.lang.String orgCN) {
		this.orgCN = orgCN;
	}

	public java.lang.String getIsChange() {
		return isChange;
	}

	public void setIsChange(java.lang.String isChange) {
		this.isChange = isChange;
	}

	public java.lang.String getCardNo() {
		return cardNo;
	}

	public void setCardNo(java.lang.String cardNo) {
		this.cardNo = cardNo;
	}

	public java.util.Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(java.util.Date entryDate) {
		this.entryDate = entryDate;
	}

	public java.util.Date getQuitDate() {
		return quitDate;
	}

	public void setQuitDate(java.util.Date quitDate) {
		this.quitDate = quitDate;
	}

	public java.util.Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(java.util.Date workDate) {
		this.workDate = workDate;
	}

	public java.lang.String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(java.lang.String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public java.lang.String getUserRelation() {
		return userRelation;
	}

	public void setUserRelation(java.lang.String userRelation) {
		this.userRelation = userRelation;
	}

	public void setProbationPeriod(java.lang.Long probationPeriod) {
		this.probationPeriod = probationPeriod;
	}

	public java.lang.Long getProbationPeriod() {
		return probationPeriod;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.lang.Float getAnnualLeave() {
		return annualLeave;
	}

	public void setAnnualLeave(java.lang.Float annualLeave) {
		this.annualLeave = annualLeave;
	}

	

	
}