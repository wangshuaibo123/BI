package com.jy.modules.platform.syslog.job;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.jy.modules.platform.syslog.service.SysLogService;
import com.jy.platform.tools.common.DateUtil;
import com.jy.platform.tools.ftp.FTPUtils;

@Component("com.jy.modules.platform.syslog.job.SysLogJob")
public class SysLogJob implements Serializable,Job{

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SysLogJob.class);
	/**
	 * 远程FTP上的路径
	 */
	private String remotePath;
	/**
	 * 本地文件路径
	 */
	private String localPath;
	
	/**
	 * ftp的ip地址
	 */
	private String ip;
	
	/**
	 * ftp端口
	 */
	private int port=0;
	
	/**
	 * ftp登录用户名
	 */
	private String userName;
	
	/**
	 * ftp登录密码
	 */
	private String password;
	
	/**
	 * 是否入库
	 */
	private boolean isImport=false;
	
	private static boolean isNext=true;
	
	private SysLogService service;
	

	/**
	 * 判断ftp下载的先决条件是否满足
	 * @return
	 */
	private boolean isRun(){
		if(isImport){
			if(StringUtils.isNotEmpty(remotePath) && StringUtils.isNotEmpty(localPath)
					&& StringUtils.isNotEmpty(ip) && StringUtils.isNotEmpty(userName)
					&& StringUtils.isNotEmpty(password) && port!=0){
				return true;
			}
		}
		return false;
	}
	
	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public boolean isImport() {
		return isImport;
	}

	public void setImport(boolean isImport) {
		this.isImport = isImport;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		if(!isNext){
			logger.info("------------SysLogJob-------isNext:"+isNext);
			return ;
		}
		isNext=false;
		long beginTime = System.currentTimeMillis();
		try {
			
			Properties prop = new Properties();   
			ClassPathResource cp = new ClassPathResource("log-ftp.properties");
            prop.load(cp.getInputStream());   
            isImport = Boolean.valueOf(StringUtils.isNotEmpty(prop.getProperty("import").trim())?prop.getProperty("import").trim():"false").booleanValue();   
            localPath = prop.getProperty("localPath").trim();   
            remotePath = prop.getProperty("remotePath").trim();  
            ip = prop.getProperty("ip").trim();  
            userName = prop.getProperty("userName").trim();  
            password = prop.getProperty("password").trim();  
            port = Integer.parseInt(prop.getProperty("port").trim());
            
            
			SchedulerContext cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			service = (SysLogService) appCtx.getBean(SysLogService.class);
			if(isRun()){
				//第一将日志文件通过FTP下载到本地
				try {
					logger.info("FTP下载文件开始===========================================");
					FTPUtils ftpUtils=new FTPUtils(getIp(), getPort(), getUserName(), getPassword(), null);
					ftpUtils.login();
					Calendar ca=Calendar.getInstance();
					ca.add(Calendar.DATE, -1);
					for(String f:ftpUtils.listFiles("*"+DateUtil.format(ca.getTime(), "yyyy-MM-dd")+"_log.log")){
						ftpUtils.download(localPath+File.separator+f, remotePath+File.separator+f);
					}
					ftpUtils.closeServer();
					logger.info("FTP下载文件结束===========================================");
				} catch (Exception e1) {
					e1.printStackTrace();
					throw new JobExecutionException("FTP下载文件失败");
				}
				//第二步将日志入库
				File file=new File(localPath);
				if(file.exists()){
					logger.info("日志入库开始=================================================");
					for(File f: file.listFiles()){
						try {
							service.insertSysLog(f);
							f.delete();
						} catch (Exception e) {
							e.printStackTrace();
							throw new JobExecutionException("日志入库出错");
						}
					}
					logger.info("日志入库结束=================================================");
				}

			}
		} catch (Exception e) {
			logger.error("日志入库操作失败"+e.getMessage());
		}finally{
			logger.info("日志入库执行时间为："+(System.currentTimeMillis()-beginTime)+"ms");
			isNext=true;
		}
	
	}

}
