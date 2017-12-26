package com.fintech.modules.logmonitor.sysapperrorinfo.job;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fintech.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService;
import com.fintech.modules.logmonitor.sysappftpinfo.dto.SysAppFtpInfoDTO;
import com.fintech.modules.logmonitor.sysappftpinfo.service.SysAppFtpInfoService;
import com.fintech.modules.logmonitor.sysapplevelsetup.dto.SysAppLevelSetupDTO;
import com.fintech.modules.logmonitor.sysapplevelsetup.service.SysAppLevelSetupService;
import com.fintech.platform.tools.common.DateUtil;
import com.fintech.platform.tools.ftp.FTPUtils;

@Component("com.fintech.modules.logmonitor.sysapperrorinfo.job.ExcuteAppErrorInfoTask")
public class ExcuteAppErrorInfoTask implements Serializable, Job {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ExcuteAppErrorInfoTask.class);
    //控制  不允许 多线程 执行 public void execute(JobExecutionContext context) throws JobExecutionException  方法
    private static boolean isNext = true;
    
    private SysAppFtpInfoService ftpInfoService;
    
    private SysAppErrorInfoService errorInfoService;
    
    private SysAppLevelSetupService setupService;
    
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("------------ExcuteAppErrorInfoTask-------isNext:"+isNext);
			return ;
		}
		isNext = false;
		SchedulerContext cont;
		try {
			Properties prop = new Properties();   
			ClassPathResource cp = new ClassPathResource("log-ftp.properties");
            prop.load(cp.getInputStream());
            String localPath=prop.getProperty("errorLog_LocalPath").trim(); 
            String backPath=prop.getProperty("errorLog_backPath").trim(); 
			cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			ftpInfoService = (SysAppFtpInfoService) appCtx.getBean(SysAppFtpInfoService.class);
			errorInfoService=(SysAppErrorInfoService)appCtx.getBean(SysAppErrorInfoService.class);
			setupService=(SysAppLevelSetupService) appCtx.getBean(SysAppLevelSetupService.class);
			Map<String, Object> searchMap=new HashMap<String, Object>();
			searchMap.put("dto", new SysAppFtpInfoDTO());
			List<SysAppFtpInfoDTO> ftplist = ftpInfoService.searchSysAppFtpInfo(searchMap);
			FTPUtils ftpUtils=null;
			File file=new File(backPath);//获取本地的日志文件路径
			for(File f: file.listFiles()){//清空本地路径中的过期日志文件
				f.delete();
			}
			for(SysAppFtpInfoDTO dto: ftplist){
				logger.info(dto.getIp()+"上错误日志FTP下载文件开始===========================================");
				try {
					ftpUtils=new FTPUtils(dto.getIp(), Integer.parseInt(dto.getPort()), dto.getUsername(), dto.getPassword(), dto.getRemoteDic());
					ftpUtils.login();
					Calendar ca=Calendar.getInstance();
					for(String f:ftpUtils.listFiles("*"+DateUtil.format(ca.getTime(), "yyyy-MM-dd")+"*.log")){
						ftpUtils.download(localPath+File.separator+f, dto.getRemoteDic()+File.separator+f);
						ftpUtils.download(backPath+File.separator+f, dto.getRemoteDic()+File.separator+f);
					}
				} catch (Exception e) {
					logger.error(dto.getIp()+"FTP下载文件失败"+e.getMessage());
					continue;
				}finally{
					if(ftpUtils!=null){
						ftpUtils.closeServer();
					}
				}
				logger.info(dto.getIp()+"上错误日志FTP下载文件结束===========================================");
				
				logger.info(dto.getIp()+"上错误日志错误日志入库开始==========================================");
				File lfile=new File(localPath);
				if(lfile.exists()){
					for(File f: lfile.listFiles()){
						try {
							errorInfoService.insertSysAppErrorInfo(f,dto.getIp(),dto.getAppFlag());
							f.delete();
						} catch (Exception e) {
							logger.error(dto.getIp()+"文件入库失败"+e.getMessage());
							continue;
						}
					}
				}
				
				logger.info(dto.getIp()+"上错误日志错误日志入库结束==========================================");
				
			}
			
			logger.info("更新错误日志的级别设置开始======================================");
			Map<String, Object> paramMap=new HashMap<String, Object>();
			paramMap.put("dto", new SysAppLevelSetupDTO());
			List<SysAppLevelSetupDTO> list = setupService.searchSysAppLevelSetup(paramMap);
			for(SysAppLevelSetupDTO dto :list){
				errorInfoService.updateSysAppErrorInfoLevel(dto.getAppFlag(), dto.getKeyWord(), dto.getLogLevel().toString(), dto.getId());
			}
			logger.info("更新错误日志的级别设置结束=====================================");
		}catch(Exception ex){
			logger.error("---------------ExcuteAppErrorInfoTask--error----------"+ex.getMessage());
		}finally{
			logger.info("------------ExcuteAppErrorInfoTask-------end-------------");
			isNext= true;
		}
	}

}
