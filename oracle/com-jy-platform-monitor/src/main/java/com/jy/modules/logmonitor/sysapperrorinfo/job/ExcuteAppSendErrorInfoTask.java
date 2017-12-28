package com.jy.modules.logmonitor.sysapperrorinfo.job;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorInfoDTO;
import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorLevelDetailDTO;
import com.jy.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService;
import com.jy.modules.logmonitor.sysapplevelsetup.dto.SysAppLevelSetupDTO;
import com.jy.modules.logmonitor.sysapplevelsetup.service.SysAppLevelSetupService;
import com.jy.modules.logmonitor.sysappmanagecontactway.dto.SysAppManageContactWayDTO;
import com.jy.modules.logmonitor.sysappmanagecontactway.service.SysAppManageContactWayService;
import com.jy.modules.platform.sysconfig.service.SysConfigService;
import com.jy.modules.platform.sysdict.service.SysDictDetailService;
import com.jy.platform.tools.mail.MessageUtil;

@Component("com.jy.modules.logmonitor.sysapperrorinfo.job.ExcuteAppSendErrorInfoTask")
public class ExcuteAppSendErrorInfoTask implements Serializable, Job{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ExcuteAppSendErrorInfoTask.class);
    //控制  不允许 多线程 执行 public void execute(JobExecutionContext context) throws JobExecutionException  方法
    private static boolean isNext = true;
    private SysAppErrorInfoService errorInfoService;
    private SysAppManageContactWayService manageContactWayService;
    private SysDictDetailService diService;
    private SysAppLevelSetupService setupService;
    private SysConfigService sysConfigService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("------------ExcuteAppSendErrorInfoTask-------isNext:"+isNext);
			return ;
		}
		isNext = false;
		SchedulerContext cont;
		try {
			cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			errorInfoService=(SysAppErrorInfoService)appCtx.getBean(SysAppErrorInfoService.class);
			manageContactWayService=(SysAppManageContactWayService) appCtx.getBean(SysAppManageContactWayService.class);
			diService= (SysDictDetailService)appCtx.getBean(SysDictDetailService.class);
			setupService = (SysAppLevelSetupService) appCtx.getBean(SysAppLevelSetupService.class);
			sysConfigService = (SysConfigService) appCtx.getBean("com.jy.modules.platform.sysconfig.service.SysConfigService");
			
			/**
			 * 首先，匹配错误级别
			 */
			logger.info("更新错误日志的级别设置开始======================================");
			//查询所有错误级别
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("dto", new SysAppLevelSetupDTO());
			List<SysAppLevelSetupDTO> list = setupService.searchSysAppLevelSetup(paramMap);
			//循环更新日志级别
			for(SysAppLevelSetupDTO dto :list){
				errorInfoService.updateSysAppErrorInfoLevel(dto.getAppFlag(), dto.getKeyWord(), dto.getLogLevel().toString(), dto.getId());
			}
			//将未匹配的更新为Y
			//统一将此时所有匹配字段为N的更新为Y，上面循环日志级别时不更新，为了使得后面的匹配可以覆盖前面的
			errorInfoService.updateSysAppErrorMatched();
			logger.info("更新错误日志的级别设置结束=====================================");
			
			
			/**
			 * 统计错误信息，并发送邮件/短信
			 */
            String esbEmailAddr = sysConfigService.queryValueByCode("ESB_EMAIL_ADDR");//ESB发送邮件服务地址 
            String esbSmsAddr = sysConfigService.queryValueByCode("ESB_SMS_ADDR");//ESB发送短信服务地址 
			
			Calendar ca = Calendar.getInstance();
			int hour = ca.get(Calendar.HOUR_OF_DAY);
			int minute = ca.get(Calendar.MINUTE);
			
			/*统计同一个系统下，同一种错误日志级别的数量*/
			//这个list存储的是按照时间为小时的整点错误统计
			List<SysAppErrorLevelDetailDTO> hList = new ArrayList<SysAppErrorLevelDetailDTO>();
			//这个list存储的是按照时间为分钟的错误统计
			List<SysAppErrorLevelDetailDTO> mList = new ArrayList<SysAppErrorLevelDetailDTO>();
			
			//查询错误级别中定义的最大时间频率（用于优化下面的SQL查询）
			int hourMaxRate = 1;//单位为小时的最大频率
			int minuteMaxRate = 1;//单位为分组的最大频率
			int maxRate = 1;
			hourMaxRate = setupService.searchMaxRateOfUnit("2");
			if(hourMaxRate <= 0){
				minuteMaxRate = setupService.searchMaxRateOfUnit("1");
				maxRate = minuteMaxRate;
			}
			else{
				maxRate = hourMaxRate * 60;
			}
			
			//如果当前是整点，查询是否有频率为小时的error
			if(minute == 0){
				hList = errorInfoService.getErrorForLevelCount("2", hour, maxRate);
			}
			mList = errorInfoService.getErrorForLevelCount("1", minute==0?60:minute, maxRate);
			hList.addAll(mList);
			logger.info("本次查询到的可发送错误日志总数==="+hList.size());
			
			if(hList.size()>0){
				//查询所有的管理员联系方式
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("dto", new SysAppManageContactWayDTO());
				List<SysAppManageContactWayDTO> manageList = manageContactWayService.searchSysAppManageContactWay(param);
				
				List<Map> logLevelList = diService.queryDetailByDictCode("LOG_LEVEL");//日志级别数据字典
				List<Map> appFlagList = diService.queryDetailByDictCode("SYSTEMFLAG");//系统标示数据字典
				
				//根据系统管理者所负责的系统，哪种级别的错误分组
				Map<String,List<SysAppErrorLevelDetailDTO>> manageError = getManageError(hList, manageList);
				
				//发送邮件
				sendMail(manageError, logLevelList, appFlagList, esbEmailAddr);
				
				//发送短信
				sendSms(manageError, logLevelList, appFlagList, esbSmsAddr);
			}
		} 
		catch(Exception ex){
			logger.error("---------------ExcuteAppSendErrorInfoTask--error----------"+ex.getMessage());
		}
		finally{
			logger.info("------------ExcuteAppSendErrorInfoTask-------end-------------");
			isNext= true;
		}
	}
	
	/**
	 * 发送邮件
	 * @param manageError   管理者：错误集合
	 * @param logLevelList  错误级别数据字典
	 * @param appFlagList   系统标示数据字典
	 * @param esbEmailAddr  ESB发送邮件地址
	 */
	private void sendMail(
			Map<String, List<SysAppErrorLevelDetailDTO>> manageError,
			List<Map> logLevelList, List<Map> appFlagList, String esbEmailAddr) throws Exception{
		//迭代“管理者：错误集合”
		for(String key: manageError.keySet()){
			StringBuffer emailContent = new StringBuffer();
			emailContent.append("错误如下：<br/><br/>");
			boolean flag = false;
			
			//迭代当前管理者的错误集合
			for(SysAppErrorLevelDetailDTO errorLevelDetail : manageError.get(key)){
				//EmailFlag==1，发送邮件
				if(errorLevelDetail.getEmailFlag()!=null && "1".equals(errorLevelDetail.getEmailFlag())){
					//拼接如：贷款系统：【级别：严重】【关键字：关键字2】【统计个数：3】
					emailContent.append("<font style='color: red'>"+getAppFlagName(errorLevelDetail.getAppFlag(), appFlagList))
					            .append("：<br/>")
					            .append("【错误级别："+getLogLevelName(errorLevelDetail.getLogLevel(), logLevelList)+"】")
					            .append("【关键字："+errorLevelDetail.getKeyWord()+"】")
					            .append("【统计个数 ：").append(errorLevelDetail.getCount()+"】"+"</font>")
					            .append("<br/>");
					
					//ShowDetailFlag==1，展示详细信息
					if(errorLevelDetail.getShowDetailFlag()!=null && "1".equals(errorLevelDetail.getShowDetailFlag())){
						String errorIdStrs = errorLevelDetail.getErrorIds();//SYS_APP_ERROR_INFO表主键，以,分割
						
						if(errorIdStrs!=null && !"".equals(errorIdStrs)){
							String[] errorIds = errorIdStrs.split(",");
							
							emailContent.append("【详细错误信息：】"+"<br/>");
							SysAppErrorInfoDTO errorInfo = null;
							String errorContent = "";
							String[] errorContentLines = null;
						    for(int j=1; j<=errorIds.length; j++ ){
								errorInfo = errorInfoService.querySysAppErrorInfoByPrimaryKey(errorIds[j-1]);
								
								errorContent = errorInfo.getConcent();
								errorContentLines = errorContent.split("\n");
								
								//增加服务器IP
								emailContent.append("<font style='color: red'>"+errorInfo.getNodeName()+"</font>  ");
								
								//错误详细信息是否多于5行，截取前5行
								if(errorContentLines!=null && errorContentLines.length>5){
									emailContent.append(errorContentLines[0]+"<br/>")
									            .append(errorContentLines[1]+"<br/>")
									            .append(errorContentLines[2]+"<br/>")
									            .append(errorContentLines[3]+"<br/>")
									            .append(errorContentLines[4]+"<br/>");
								}
								else{
									for(int k=0;k<errorContentLines.length;k++){
										emailContent.append(errorContentLines[k]+"<br/>");
									}
								}
								
								if(j != errorIds.length){
									emailContent.append("---------------------------------------------------------------------------------------"+"<br/>");
								}
							}
						}
					}
					
					emailContent.append("<br/><br/>");
					flag = true;
				}
			}
			
			if(flag){
				emailContent.append(" 请登录<a href='http://log.jieyue.com/monitor'>日志系统</a>查看详细信息。 ");
				String[] email_name = key.split("\t\t");
				logger.debug("singleEmail=="+email_name[0]);
				logger.debug("emailContent=="+emailContent.toString());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				//发送邮件
				MessageUtil.sendMail(esbEmailAddr,"错误统计（"+df.format(new java.util.Date())+"）",emailContent.toString(),1,email_name[2],email_name[0],"","0","","","");
			}
			
			flag = false;
		}
	}
	
	
	/**
	 * 发送短信
	 * @param manageError
	 * @param logLevelList
	 * @param appFlagList
	 * @param esbSmsAddr
	 * @throws Exception
	 */
	private void sendSms(
			Map<String, List<SysAppErrorLevelDetailDTO>> manageError,
			List<Map> logLevelList, List<Map> appFlagList, String esbSmsAddr) throws Exception{
		//迭代“管理者：错误集合”
		for(String key: manageError.keySet()){
			StringBuffer content = new StringBuffer();
			content.append("错误如下：\n\n");
			boolean flag = false;
			
			//迭代当前管理者的错误集合
			for(SysAppErrorLevelDetailDTO errorLevelDetail : manageError.get(key)){
				//SmsFlag==1，发送短信
				if(errorLevelDetail.getSmsFlag()!=null && "1".equals(errorLevelDetail.getSmsFlag())){
					//拼接如：贷款系统：【级别：严重】【关键字：关键字2】【统计个数：3】
					content.append(getAppFlagName(errorLevelDetail.getAppFlag(), appFlagList))
					            .append("：\n")
					            .append("【错误级别："+getLogLevelName(errorLevelDetail.getLogLevel(), logLevelList)+"】")
					            .append("【关键字："+errorLevelDetail.getKeyWord()+"】")
					            .append("【统计个数 ：").append(errorLevelDetail.getCount()+"】")
					            .append("\n\n");
					flag = true;
				}
			}
			
			if(flag){
				content.append(" 请登录 日志系统 查看详细信息。 ").append("【捷越】");//短信中必须包含“【捷越】”
				String tel = key.split("\t\t")[1];
				logger.debug("tel=="+tel);
				logger.debug("smsContent=="+content.toString());
				
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				
				//发送短信
				MessageUtil.sendSms(esbSmsAddr, tel, content.toString(), df.format(new java.util.Date()));
			}
			
			flag = false;
		}
	}
	
	
	/**
	 * 获取系统标示描述
	 * @param appFlag
	 * @param appFlagList
	 * @return
	 */
	private String getAppFlagName(String appFlag, List<Map> appFlagList){
		for(Map app: appFlagList){
			if(app.get("DICVALUE").equals(appFlag)){
				return app.get("DICNAME").toString();
			}
		}
		return null;
	}
	
	/**
	 * 获取日志级别描述
	 * @param logLevel
	 * @param logLevelList
	 * @return
	 */
	private String getLogLevelName(String logLevel, List<Map> logLevelList){
		for(Map app: logLevelList){
			if(app.get("DICVALUE").equals(logLevel)){
				return app.get("DICNAME").toString();
			}
		}
		return null;
	}

	/**
	 * 根据系统管理者所负责的系统，哪种级别的错误分组，分组后的结果为：
	 * 系统管理者（mail,管理者名称） -- 管理者所负责的系统的错误统计信息的集合
	 * @param errorlist  系统错误统计集合
	 * @param managerList  系统管理者联系方式集合
	 * @return
	 */
	private Map<String,List<SysAppErrorLevelDetailDTO>> getManageError(List<SysAppErrorLevelDetailDTO> errorlist, List<SysAppManageContactWayDTO> managerList){
		//key：管理者
		//value：错误集合
		Map<String, List<SysAppErrorLevelDetailDTO>> restltMap = new HashMap<String, List<SysAppErrorLevelDetailDTO>>();
		
		//遍历所有系统管理者
		for(SysAppManageContactWayDTO manager : managerList){
			List<SysAppErrorLevelDetailDTO> inList = null;
			
			if(restltMap.get(manager.getEmail()+"\t\t"+manager.getTel()+"\t\t"+manager.getManageName()) != null){
				inList = restltMap.get(manager.getEmail()+"\t\t"+manager.getTel()+"\t\t"+manager.getManageName());
			}
			else{
				inList = new ArrayList<SysAppErrorLevelDetailDTO>();
				restltMap.put(manager.getEmail()+"\t\t"+manager.getTel()+"\t\t"+manager.getManageName(), inList);
			}
			
			//遍历系统错误统计集合
			for(SysAppErrorLevelDetailDTO errorLevelDetailDto : errorlist){
				//如果当前系统由当前管理者负责，按照关键字过滤  或者当期管理者负责所有系统
				if(errorLevelDetailDto.getAppFlag().equals(manager.getAppFlag()) || "ALL".equalsIgnoreCase(manager.getAppFlag())){
					//如果当前管理者指定了关键字
					if(manager.getKeyWord()!=null && !"".equals(manager.getKeyWord())){
						//当前关键字包含当前管理者负责的关键字
						if(errorLevelDetailDto.getKeyWord()!=null && !"".equals(errorLevelDetailDto.getKeyWord())
							&& errorLevelDetailDto.getKeyWord().trim().indexOf(manager.getKeyWord().trim())!=-1 ){
							inList.add(errorLevelDetailDto);
						}
					}
					//如果当前管理者没有指定关键字，按照级别过滤，当前错误级别  > 当前管理者负责的错误级别
					else if(Long.parseLong(errorLevelDetailDto.getLogLevel()) >= manager.getLogLevel()){
						inList.add(errorLevelDetailDto);
					}
				}
			}
		}
		
		return restltMap;
	}
	
	
}
