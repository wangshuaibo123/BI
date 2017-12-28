package com.jy.modules.platform.schedule2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author xyz
 * 
 */
@Controller
@RequestMapping("/api/schedRest")
public class SchedRest {

	private static final Logger logger = LoggerFactory.getLogger(SchedRest.class);

	@Autowired
	@Qualifier("com.jy.modules.platform.schedule2.SchedService")
	private SchedService service;

	/**
	 * 请求示例数据格式：{jobName:'bean:com.jy.modules.platform.schedule2.test.TestJobBean',
	 * bizModule:'s001',fireTime:'20161115175710',hasLog:false,methodName:null}
	 * @param schedRequest
	 * @return
	 */
	@RequestMapping(value="/schedJob", method={RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String schedJob(SchedRequest schedRequest) {
		logger.info("接收调度：{}", schedRequest.toString());

		try {
			checkData(schedRequest);
			return service.processSchedule(schedRequest);
		} catch (Exception e) {
			logger.error("执行调度异常：", e);
			return e.getClass().getSimpleName() + ":" + e.getMessage();
		}
	}

	private void checkData(SchedRequest schedRequest) throws Exception {
		if (schedRequest == null) {
			throw new Exception("调度请求内容不能为空");
		}
		if (schedRequest.getJobName() == null
				|| "".equals(schedRequest.getJobName())) {
			throw new Exception("字段jobName不能为空");
		}
		if (schedRequest.getBizModule() == null
				|| "".equals(schedRequest.getBizModule())) {
			throw new Exception("字段bizModule不能为空");
		}
		if (schedRequest.getFireTime() == null
				|| "".equals(schedRequest.getFireTime())) {
			throw new Exception("字段fireTime不能为空");
		}
	}
}
