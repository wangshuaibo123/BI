package com.jy.modules.quartzJob.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.modules.quartzJob.core.service.ISchedulerService;
import com.jy.modules.quartzJob.core.service.impl.JYQuartzService;
import com.jy.modules.quartzJob.tool.Constant;
import com.jy.platform.core.message.DataMsg;


@Controller
@Scope("prototype")
@RequestMapping("/quartz")
public class QuartzController {

    private static final Logger logger = LoggerFactory.getLogger(QuartzController.class);
    
    @Autowired
    @Qualifier("com.jy.modules.quartzJob.core.service.impl.JYQuartzService")
    private JYQuartzService quartzService;
    
	@Autowired
	@Qualifier("com.jy.modules.quartzJob.core.service.impl.SchedulerServiceImpl")
    private ISchedulerService schedulerService;

    @RequestMapping("/toQuartzList")
    public String toQuartzList(HttpServletRequest request, ModelMap model) {

        return "platform/quartzJob/quartzList";
    }
    
    @RequestMapping("/toAddQuartz")
    public String toAddQuartz(HttpServletRequest request, ModelMap model) {

        return "platform/quartzJob/addQuartz";
    }
    
    /**
	 * 打开更新界面
	 * @author cxt
	 * @param requ
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RequestMapping("/toUpdateQuartz")
    public ModelAndView toUpdateQuartz(HttpServletRequest request, ModelMap model) {
    	ModelAndView mav = new ModelAndView("platform/quartzJob/addQuartz"); // 返回的路径
    	String cron = "";
    	String jobName = "";
    	String jobClaz= "";
    	try{
        	String triggerName = request.getParameter("triggerName");
        	triggerName = java.net.URLDecoder.decode(triggerName,"UTF-8");
        	String triggerGroup = request.getParameter("triggerGroup");
        	triggerGroup = java.net.URLDecoder.decode(triggerGroup,"UTF-8");
        	Map<String, Object> param = new HashMap<String, Object>();
        	Map<String, Object> result = new HashMap<String, Object>();
        	param.put("triggerName", triggerName);
        	cron = quartzService.findQuartzCronByName(param);
        	Map<String, Object> map = quartzService.findQuartzJobByName(param);
        	jobName = (String) map.get("JOB_NAME");
        	jobClaz = (String) map.get("JOB_CLASS_NAME");
        	//System.out.println(triggerName+","+cron+","+jobName);
        	result.put("triggerName", triggerName);
        	result.put("triggerGroup", triggerGroup);
        	result.put("cron", cron);
        	result.put("jobName", jobName);
        	result.put("jobClaz", jobClaz);
        	mav.addObject("result", result);
        	mav.addObject("type", "edit");
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    	
        return mav;
    }
    
    /**
	 * 修改定时任务
	 * @author cxt
	 * @param requ
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updateQuartzJob")
	@ResponseBody
	public DataMsg updateQuartzJob(HttpServletRequest requ, HttpServletResponse response, @ModelAttribute DataMsg pageData) throws Exception{
		String msg = "";
		try {
			msg = Constant.OPERATE_SUCCESS;
			 if (pageData == null) {
	             pageData = new DataMsg();
	         }
			String triGroupName = requ.getParameter("triggerGroup");
			String triggerName = requ.getParameter("triggerNameId");
			String cronExp =  requ.getParameter("cronExpression");
//			String jobClaz =  requ.getParameter("jobClaz");
			String jobName = requ.getParameter("jobName");
			//String cronExp = "0/50 * * ? * * *";//0/10 * * ? * * *"，每10秒中执行调试一次
			//System.out.println("任务修改----------------jobName："+jobName+",triggerName:"+triggerName+",cronExp"+cronExp+",jobClaz:"+jobClaz);
			
			schedulerService.updateJob(triggerName, triGroupName,jobName, cronExp);	
			pageData.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			e.printStackTrace();
			msg = Constant.OPERATE_FAILED;
			pageData.setStatus(DataMsg.STATUS_FAILED);
		} finally{
			pageData.setMsg(msg);
		}
		
		return pageData;
		
	}
    
	/**
	 * 添加定时任务
	 * @author cxt
	 * @param requ
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addQuartzJob")
	@ResponseBody
	public DataMsg addQuartzJob(HttpServletRequest requ, HttpServletResponse response, @ModelAttribute DataMsg pageData) throws Exception{
		String msg = Constant.ADD_SUCESS;
		try {
            if (pageData == null) {
                pageData = new DataMsg();
            }
			
			String jobGroupName = requ.getParameter("triggerGroup");
			String triggerName = requ.getParameter("triggerNameId");
			String cronExp =  requ.getParameter("cronExpression");
			
			String claz =  requ.getParameter("jobClaz");//com.platform.quartzJob.QuartzJobTest
			//System.out.println("jobGroupName："+jobGroupName+",triggerName:"+triggerName+",cronExp"+cronExp);
			String jobName = triggerName;
			//String cronExp = "0/50 * * ? * * *";//0/10 * * ? * * *"，每10秒中执行调试一次
		
			// 添加任务调试
			//获取spring 上下文信息
//			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(requ.getSession().getServletContext());
			
			//添加复杂的任务调度
			schedulerService.addNewJob(jobName, jobGroupName, cronExp, claz);
			pageData.setStatus(DataMsg.STATUS_OK);
		} catch (Exception e) {
			e.printStackTrace();
			msg = Constant.ADD_FAILED;
			pageData.failed(e.getMessage());
			pageData.setStatus(DataMsg.STATUS_FAILED);
		}finally{
			pageData.setMsg(msg);
		}

		return pageData;
		
	}
	
	/**
	 * 暂停定时任务
 	 * @author cxt
	 * @param requ
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/pauseQuartzJob")
	@ResponseBody
	public DataMsg pauseQuartzJob(HttpServletRequest requ, HttpServletResponse response, @ModelAttribute DataMsg pageData) throws Exception{
		String msg = "暂停成功";
		try {
		    if (pageData == null) {
                pageData = new DataMsg();
            }
		    String triggerName = requ.getParameter("triggerName");
		    String triggerGroup = requ.getParameter("triggerGroup");
		    //System.out.println("暂停定时任务---------------triggerName："+triggerName+",triggerGroup："+triggerGroup);
			schedulerService.pauseTrigger(triggerName, triggerGroup);
		} catch (Exception e) {
			e.printStackTrace();
			msg ="暂停失败！！！"+e.getMessage();
		}finally{
			pageData.setMsg(msg);
		}
		return pageData;
		
	}
	
	/**
	 * 恢复定时任务
	 * @author cxt
	 * @param requ
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/resumeQuartzJob")
	@ResponseBody
	public DataMsg resumeQuartzJob(HttpServletRequest requ, HttpServletResponse response, @ModelAttribute DataMsg pageData) throws Exception{
		String msg = "恢复成功";
		try {
		    if (pageData == null) {
                pageData = new DataMsg();
            }
		    String triggerName = requ.getParameter("triggerName");
		    String triggerGroup = requ.getParameter("triggerGroup");
		    //System.out.println("恢复定时任务---------------triggerName："+triggerName+",triggerGroup："+triggerGroup);
			schedulerService.resumeTrigger(triggerName, triggerGroup);
		} catch (Exception e) {
			e.printStackTrace();
			msg ="恢复失败！！！"+e.getMessage();
		}finally{
			pageData.setMsg(msg);
		}
		return pageData;
		
	}
	
	
	/**
	 * 删除定时任务
	 * @author cxt
	 * @param requ
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/removeQuartzJob")
	@ResponseBody
	public DataMsg removeQuartzJob(HttpServletRequest requ, HttpServletResponse response, @ModelAttribute DataMsg pageData) throws Exception{
		String msg = Constant.DELETE_FAILED;
		try {
		    if (pageData == null) {
                pageData = new DataMsg();
            }
			String triggerName = requ.getParameter("triggerName");
			String triggerGroup = requ.getParameter("triggerGroup");
			//System.out.println("删除定时任务---------------triggerName："+triggerName+",triggerGroup："+triggerGroup);
			if(StringUtils.isNotEmpty(triggerName) && StringUtils.isNotEmpty(triggerGroup)){ 
				
				boolean isTrue = schedulerService.removeTrigdger(triggerName, triggerGroup);
				
				if(isTrue) msg = Constant.DELETE_SUCCESS;
				
			}else{
				msg = "删除失败，缺少参数信息！！！";
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = Constant.DELETE_FAILED +e.getMessage();
		} finally{
			pageData.setMsg(msg);
		}
		return pageData;
	}

	/**
	 * 定时任务条件查询
	 * @author cxt
	 * @param requ
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/findAllQuartz")
    @ResponseBody
    public DataMsg findAllQuartz(HttpServletRequest request, @ModelAttribute DataMsg pageData) {
        if (pageData == null) {
            pageData = new DataMsg();
        }
        Map<String, Object> param = new HashMap<String, Object>();
        // 查询条件
        String triggerName = request.getParameter("triggerName");
        String triggerGroup = request.getParameter("triggerGroup");
        String jobName = request.getParameter("jobName");
        String jobGroup = request.getParameter("jobGroup");
        String nextFireTime = request.getParameter("nextFireTime");
        String prevFireTime = request.getParameter("prevFireTime");
        String description = request.getParameter("description");
        String triggerState = request.getParameter("triggerState");//状态

        param.put("triggerName", triggerName);
        param.put("triggerGroup", triggerGroup);
        param.put("jobName", jobName);
        param.put("jobGroup", jobGroup);
        param.put("nextFireTime", nextFireTime);
        param.put("prevFireTime", prevFireTime);
        param.put("description", description);
        param.put("triggerState", triggerState);
        //param.put("status", Status.delete);

        param.put("startIndex", (pageData.getPageIndex() - 1) * pageData.getPageSize() + 1);
        param.put("endIndex", (pageData.getPageIndex() - 1) * pageData.getPageSize() + 1 + pageData.getPageSize());

        List<Map<String,Object>> quartzList = quartzService.getQuartzByPage(param);

        int count = quartzService.findAllQuartzCount(param);
        pageData.setData(quartzList);
        pageData.setTotalRows(count);
        return pageData;
    }

  
}
