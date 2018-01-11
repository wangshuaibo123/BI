package com.fintech.modules.archive.timer;


import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletContext;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fintech.modules.archive.service.HelpDataArchive;


/**
 * 工作流数据归档任务入口
 * @author
 *
 */
@Component("com.fintech.modules.archive.timer.ArchiveTask")
public class ArchiveTask implements Serializable,Job{
	private static final Logger logger = LoggerFactory.getLogger(ArchiveTask.class);
	private static final long serialVersionUID = 1L;
	private static final String NAME="helpDataArchive";
	private static final String CODE="jbpm.archive.month";
	private static final String VALUE="-36";
	private HelpDataArchive helpDataArchive;
	
	public  ArchiveTask(){
		helpDataArchive=getHelpDataArchive();
	}
	
	/**
	 * 执行数据归档
	 */
	private static boolean isNext = true;
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		if(!isNext){
			logger.info("----------进入方法AutoExeTaskJob execute---------------");
			return ;
		}
		isNext = false;
		try {
			logger.info("开始工作流历史数据归档");
			helpDataArchive.doArchiving(getDate( ));
		}catch (Exception e) {
			logger.error(e.getMessage());
			throw new JobExecutionException(e.getMessage());
		}finally{
			isNext= true;
		}
		
	}
	/**
	 * 获取归档时间
	 * @return
	 * @throws JobExecutionException
	 * @throws IOException
	 */
	public String getDate( ) throws JobExecutionException, IOException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		System.out.println(sdf.format(calendar.getTime()));
		calendar.add(Calendar.MONTH, Integer.parseInt(getMonth()));
		String timeStr=sdf.format(calendar.getTime());
		logger.info("归档时间："+timeStr);
		return timeStr;
	}
	public String getMonth(){
		String value=helpDataArchive.getValue(CODE);
        String month=null;
        if(value==null||"".equals(value.trim())){
        	month=VALUE;
        }else{
        	month=value.trim();
        }
        return month;
	}
	
	protected HelpDataArchive getHelpDataArchive(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();    
		ServletContext servletContext = webApplicationContext.getServletContext();
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return  (HelpDataArchive)context.getBean(NAME);
	}
}
