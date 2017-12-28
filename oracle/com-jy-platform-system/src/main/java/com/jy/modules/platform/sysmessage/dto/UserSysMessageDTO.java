package com.jy.modules.platform.sysmessage.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:SYS_MESSAGE
 *@author lin
 *@version 1.0,
 *@date 2014-11-14 11:07:13
 */
public class UserSysMessageDTO extends BaseDTO{

	private static final long serialVersionUID = 1L;

	/**唯一标识，主键*/
	private java.lang.Long msgId;

	/**消息标题*/
	private java.lang.String title;

	/**消息体*/
	private java.lang.String body;

	/**消息链接，可为空*/
	private java.lang.String url;

	/**消息创建日期*/
	private java.util.Date createDate;

	/**消息生效日期*/
	private java.util.Date startDate;

	/**消息失效日期*/
	private java.util.Date endDate;

	/**信息状态 0：初始；1：生效；2：失效；3：删除；default：0*/
	private java.lang.String status;

	/**消息发布者ID*/
	private java.lang.String publisher;

	/**信息的类型 0：全局消息；1：专有消息*/
	private java.lang.String type;

	/**信息体的编码集*/
	private java.lang.String charset;

	/**紧急标识 0:普通；1：紧急；2：特急 default:0*/
	private java.lang.String urgentFlag;

	/**标识归属于哪个系统*/
	private java.lang.String sysFlag;
	/**用户ID*/
	private String userId;
	/**用户是否已读此条消息 0:未读；1：已读 default:0*/
	private String readFlag = "0";
	/**发布人姓名显示*/
	private String publisherNameShow;
	
	/**
	 * 用户是否已读此条消息 0:未读；1：已读 default:0
	 * @return
	 */
	public String getReadFlag() {
		return readFlag;
	}
	/**
	 * 用户是否已读此条消息 0:未读；1：已读 default:0
	 * @return
	 */
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 *方法: 获得msgId
	 *@return: java.lang.Long  msgId
	 */
	public java.lang.Long getMsgId(){
		return this.msgId;
	}

	/**
	 *方法: 设置msgId
	 *@param: java.lang.Long  msgId
	 */
	public void setMsgId(java.lang.Long msgId){
		this.msgId = msgId;
	}

	/**
	 *方法: 获得title
	 *@return: java.lang.String  title
	 */
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置title
	 *@param: java.lang.String  title
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}

	/**
	 *方法: 获得body
	 *@return: java.lang.String  body
	 */
	public java.lang.String getBody(){
		return this.body;
	}

	/**
	 *方法: 设置body
	 *@param: java.lang.String  body
	 */
	public void setBody(java.lang.String body){
		this.body = body;
	}

	/**
	 *方法: 获得url
	 *@return: java.lang.String  url
	 */
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置url
	 *@param: java.lang.String  url
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}

	/**
	 *方法: 获得createDate
	 *@return: java.util.Date  createDate
	 */
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置createDate
	 *@param: java.util.Date  createDate
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	/**
	 *方法: 获得startDate
	 *@return: java.util.Date  startDate
	 */
	public java.util.Date getStartDate(){
		return this.startDate;
	}

	/**
	 *方法: 设置startDate
	 *@param: java.util.Date  startDate
	 */
	public void setStartDate(java.util.Date startDate){
		this.startDate = startDate;
	}

	/**
	 *方法: 获得endDate
	 *@return: java.util.Date  endDate
	 */
	public java.util.Date getEndDate(){
		return this.endDate;
	}

	/**
	 *方法: 设置endDate
	 *@param: java.util.Date  endDate
	 */
	public void setEndDate(java.util.Date endDate){
		this.endDate = endDate;
	}

	/**
	 *方法: 获得status
	 *@return: java.lang.String  status
	 */
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置status
	 *@param: java.lang.String  status
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}

	/**
	 *方法: 获得publisher
	 *@return: java.lang.String  publisher
	 */
	public java.lang.String getPublisher(){
		return this.publisher;
	}

	/**
	 *方法: 设置publisher
	 *@param: java.lang.String  publisher
	 */
	public void setPublisher(java.lang.String publisher){
		this.publisher = publisher;
	}

	/**
	 *方法: 获得type
	 *@return: java.lang.String  type
	 */
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置type
	 *@param: java.lang.String  type
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}

	/**
	 *方法: 获得charset
	 *@return: java.lang.String  charset
	 */
	public java.lang.String getCharset(){
		return this.charset;
	}

	/**
	 *方法: 设置charset
	 *@param: java.lang.String  charset
	 */
	public void setCharset(java.lang.String charset){
		this.charset = charset;
	}

	/**
	 *方法: 获得urgentFlag
	 *@return: java.lang.String  urgentFlag
	 */
	public java.lang.String getUrgentFlag(){
		return this.urgentFlag;
	}

	/**
	 *方法: 设置urgentFlag
	 *@param: java.lang.String  urgentFlag
	 */
	public void setUrgentFlag(java.lang.String urgentFlag){
		this.urgentFlag = urgentFlag;
	}

	/**
	 *方法: 获得sysFlag
	 *@return: java.lang.String  sysFlag
	 */
	public java.lang.String getSysFlag(){
		return this.sysFlag;
	}

	/**
	 *方法: 设置sysFlag
	 *@param: java.lang.String  sysFlag
	 */
	public void setSysFlag(java.lang.String sysFlag){
		this.sysFlag = sysFlag;
	}
	/**发布人姓名显示*/
	public String getPublisherNameShow() {
		return publisherNameShow;
	}
	/**发布人姓名显示*/
	public void setPublisherNameShow(String publisherNameShow) {
		this.publisherNameShow = publisherNameShow;
	}

}