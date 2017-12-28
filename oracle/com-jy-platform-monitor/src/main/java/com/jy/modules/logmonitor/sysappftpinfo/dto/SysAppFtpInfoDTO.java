package com.jy.modules.logmonitor.sysappftpinfo.dto;

import com.jy.platform.core.common.BaseDTO;
/**
 *@Description:业务系统节点FTP配置表
 *@author lei
 *@version 1.0,
 *@date 2015-04-03 10:06:16
 */
public class SysAppFtpInfoDTO extends BaseDTO{

    private static final long serialVersionUID = 1L;

    /**主键*/
    private java.lang.Long id;

    /**节点IP*/
    private java.lang.String ip;

    /**节点FTP端口*/
    private java.lang.String port;

    /**FTP用户名*/
    private java.lang.String username;

    /**FTP密码*/
    private java.lang.String password;

    /**业务系统标识*/
    private java.lang.String appFlag;

    /**错误日志目录*/
    private java.lang.String remoteDic;
    
    /**业务应用日志目录*/
    private java.lang.String applogDic;

    
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
     *方法: 获得ip
     *@return: java.lang.String  ip
     */
    public java.lang.String getIp(){
        return this.ip;
    }

    /**
     *方法: 设置ip
     *@param: java.lang.String  ip
     */
    public void setIp(java.lang.String ip){
        this.ip = ip;
    }

    /**
     *方法: 获得port
     *@return: java.lang.String  port
     */
    public java.lang.String getPort(){
        return this.port;
    }

    /**
     *方法: 设置port
     *@param: java.lang.String  port
     */
    public void setPort(java.lang.String port){
        this.port = port;
    }

    /**
     *方法: 获得username
     *@return: java.lang.String  username
     */
    public java.lang.String getUsername(){
        return this.username;
    }

    /**
     *方法: 设置username
     *@param: java.lang.String  username
     */
    public void setUsername(java.lang.String username){
        this.username = username;
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
     *方法: 获得appFlag
     *@return: java.lang.String  appFlag
     */
    public java.lang.String getAppFlag(){
        return this.appFlag;
    }

    /**
     *方法: 设置appFlag
     *@param: java.lang.String  appFlag
     */
    public void setAppFlag(java.lang.String appFlag){
        this.appFlag = appFlag;
    }

    /**
     *方法: 获得remoteDic
     *@return: java.lang.String  remoteDic
     */
    public java.lang.String getRemoteDic(){
        return this.remoteDic;
    }

    /**
     *方法: 设置remoteDic
     *@param: java.lang.String  remoteDic
     */
    public void setRemoteDic(java.lang.String remoteDic){
        this.remoteDic = remoteDic;
    }

    /**
     *方法: 获得applogDic
     *@return: java.lang.String  applogDic
     */
    public java.lang.String getApplogDic() {
        return applogDic;
    }

    /**
     *方法: 设置applogDic
     *@param: java.lang.String  applogDic
     */
    public void setApplogDic(java.lang.String applogDic) {
        this.applogDic = applogDic;
    }
}