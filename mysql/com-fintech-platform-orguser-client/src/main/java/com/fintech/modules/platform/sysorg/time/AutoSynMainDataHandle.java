package com.fintech.modules.platform.sysorg.time;

import java.io.Serializable;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.fintech.modules.platform.sysorg.dto.SysSynDTO;
import com.fintech.modules.platform.sysorg.service.SynClientService;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
/**
 * @description:执行定时任务 进行自动更新同步表信息
 * @date: 2016年7月26日 下午1:08:55
 */
@Service("com.fintech.modules.platform.sysorg.time.AutoSynMainDataHandle")
public class AutoSynMainDataHandle implements Serializable ,Job{
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(AutoSynMainDataHandle.class);
	private SysConfigAPI sysConfigAPI;
	
	/***用户同步服务***/
	private SynClientService synClientService;
	
	private static boolean isNext = true;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		if(!isNext){
			logger.info("--------进入方法---SynMainDataHandle- execute----------");
			return;
		}
		
		isNext = false;
		
		SchedulerContext cont;
		String msg = "orguser客户端自动更新同步数据出现错误！";
		try {
			cont = context.getScheduler().getContext();
			ApplicationContext appCtx = (ApplicationContext) cont.get("applicationContextKey");
			sysConfigAPI = (SysConfigAPI) appCtx.getBean(SysConfigAPI.class);
			synClientService = (SynClientService)appCtx.getBean(SynClientService.class);
			String value=sysConfigAPI.getValue("synAuto_User_OnOff");
			if(value!=null && !"".equals(value) && ("on".equals(value) || "ON".equals(value)))
			{
				execute();
				msg = "orguser客户端自动更新同步数据成功！";
			}else{
				msg="没设置开关，请手动更新！！！";
			}
			
		} catch (SchedulerException e) {
			logger.error("--------自动同步失败 error:",e);
			e.printStackTrace();
		}finally{
			isNext = true;
			logger.info(msg);
		}
	}
	
	public void execute(){		
		try{
			List<SysSynDTO> versions=synClientService.searchAutoSysSyn();
			for(SysSynDTO sysSynDTO : versions)
			{
				synClientService.updateSysDataSynAndCheck(sysSynDTO.getVersion().toString());
			}
		}catch(Exception e){
			
			logger.error("======AutoSynMainDataHandle synClientService.updateSysDataSynAndCheck error:",e);
		}
	}
}
