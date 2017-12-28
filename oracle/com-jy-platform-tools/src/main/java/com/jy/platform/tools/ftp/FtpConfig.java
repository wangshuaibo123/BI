package com.jy.platform.tools.ftp;


public class FtpConfig  {
	private String localBasePath="";  //本地文件基础路径
	private String ftpBasePath="";  //FTP上传文件基础路径
	private String ftpHost="";  //FTP地址
	private String ftpUserName="";  //FTP用户名
	private String ftpPassword="";  //FTP用户密码
	private boolean passive = true;
	private int ftpPort=21;//FTP 端口(默认为21端口)
    private FtpConfig() {
        
    }
    public static  FtpConfig getConfig(){
        FtpConfig ftpConfig=new FtpConfig();
        return ftpConfig;
    }
    public String getLocalBasePath() {
        return localBasePath;
    }
    public void setLocalBasePath(String localBasePath) {
        this.localBasePath = localBasePath;
    }
    public String getFtpBasePath() {
        return ftpBasePath;
    }
    public void setFtpBasePath(String ftpBasePath) {
        this.ftpBasePath = ftpBasePath;
    }
    public String getFtpHost() {
        return ftpHost;
    }
    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }
    public String getFtpUserName() {
        return ftpUserName;
    }
    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }
    public String getFtpPassword() {
        return ftpPassword;
    }
    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }
    public boolean isPassive() {
        return passive;
    }
    public int getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
	public void setPassive(boolean passive) {
        this.passive = passive;
    }

}
