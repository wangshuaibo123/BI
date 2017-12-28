package com.jy.modules.platform.schedule2;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jy.modules.platform.schedule2.ptschedlog.dto.PtSchedLogDTO;
import com.jy.modules.platform.schedule2.ptschedlog.service.PtSchedLogService;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restclient.http.RestClient;

/**
 * 
 * @author xyz
 * 
 */
public class SchedService implements Serializable {

	public static final String SUCCESS = "success";
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(SchedService.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private String logServiceUrl;
	private String appId;
	private static final String INSERT_URL = "/api/platform/ptSchedLog/create/v1";
	private static final String UPDATE_URL = "/api/platform/ptSchedLog/update/v1";
	private boolean isRemoteLog = true;

	//com.jy.modules.platform.schedule2.ptschedlog.service.PtSchedLogService
	private PtSchedLogService logService;

	/**
	 * 执行 调度处理，并记录相关处理日志
	 * 
	 * @param processSchedule
	 * @return
	 * @throws Exception
	 */
	public String processSchedule(SchedRequest schedRequest) throws Exception {
		// 设置主线程名称
		Thread.currentThread().setName(schedRequest.getJobName() + "#" + schedRequest.getFireTime());

		// 记录开始日志（开始时间、RUNNING）
		PtSchedLogDTO logBean = getInitPtSchedLogDTO(schedRequest);
		writeBeginLog(schedRequest, logBean);

		// 启动job线程
		JobThread jobThread = new JobThread(schedRequest);
		jobThread.setName(schedRequest.getJobName() + "#" + schedRequest.getFireTime() + ".job");
		jobThread.start();

		// 不断获取job线程状态，并更新日志
		String state = "";
		Map<String, Object> threadMap = new HashMap<String, Object>();
		while (!jobThread.isFinished()) {
			Thread.sleep(schedRequest.getStateInterval() * 1000);
			String jobState = jobThread.getJobState();
			if (jobState != null && !state.equals(jobState)) {
				state = jobState;
				writeStateLog(schedRequest, logBean, jobState);
			} else {
				logger.debug("进行线程检查...");
				try {
					checkThread(threadMap, jobThread.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 更新日志（结束时间，SUCCESS|ERROR）
		String result = jobThread.getJobResult();
		if (SUCCESS.equals(result)) {
			writeSuccessEndLog(schedRequest, logBean);
		} else {
			writeErrorEndLog(schedRequest, logBean, result);
		}
		return result;
	}

	private void writeBeginLog(SchedRequest request, PtSchedLogDTO logBean) {
		logger.info("调度开始执行：{}、{}、{}", 
				logBean.getJobName(), logBean.getFireTime(), logBean.getBizModule(),
				logBean.getStartTime(), logBean.getState());
		if (request.isWriteLog()) {
			insertSchedLog(logBean);
		}
	}

	private void writeStateLog(SchedRequest schedRequest, PtSchedLogDTO logBean, String jobState) {
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		logBean.setResult(time + " " + jobState);
		logger.info("调度执行状态：{}", logBean.getResult());
		if (schedRequest.isWriteLog()) {
			updateSchedLog(logBean);
		}
	}

	private void writeSuccessEndLog(SchedRequest schedRequest, PtSchedLogDTO logBean) {
		logBean.setEndTime(new Timestamp(System.currentTimeMillis()));
		logBean.setState(PtSchedLogDTO.STATE_SUCCESS);
		logBean.setResult("");
		logger.info("调度执行成功：{}、{}", logBean.getEndTime(), logBean.getState());
		if (schedRequest.isWriteLog()) {
			updateSchedLog(logBean);
		}
	}

	private void writeErrorEndLog(SchedRequest schedRequest, PtSchedLogDTO logBean, String msg) {
		logBean.setEndTime(new Timestamp(System.currentTimeMillis()));
		logBean.setState(PtSchedLogDTO.STATE_ERROR);
		logBean.setResult(msg);
		logger.info("调度执行失败：{}、{}、{}", logBean.getEndTime(), logBean.getState(), logBean.getResult());
		if (schedRequest.isWriteLog()) {
			updateSchedLog(logBean);
		}
	}

	private void updateSchedLog(PtSchedLogDTO logBean) {
		try {
			if (logBean.getId() == null || logBean.getId() == 0) {
				return;
			}

			if (isRemoteLog) {
				// 调用远程url接口，传送数据
				String url = logServiceUrl + UPDATE_URL;
				//ResponseMsg<Void> responseMsg = 
				RestClient.doPost(appId, url, logBean, new TypeReference<ResponseMsg<Void>>() {
				});
			} else {
				logService.updatePtSchedLog(logBean);
			}
		} catch (Exception e) {
			logger.error("更新调度日志出错", e);
		}
	}

	/**
	 * 保存log日志
	 * 
	 * @param logBean
	 * @throws Exception
	 */
	private void insertSchedLog(PtSchedLogDTO logBean) {
		try {
			if (isRemoteLog) {
				// 调用远程url接口，传送数据
				String url = logServiceUrl + INSERT_URL;
				ResponseMsg<PtSchedLogDTO> responseMsg = RestClient.doPost(appId, url, logBean, new TypeReference<ResponseMsg<PtSchedLogDTO>>() {
					});
				logBean.setId(responseMsg.getResponseBody().getId());
			} else {
				logService.insertPtSchedLog(logBean);
			}
		} catch (Exception e) {
			logger.error("插入调度日志出错", e);
		}
	}

	/**
	 * 获取初始数据对象
	 * 
	 * @param schedRequest
	 * @return
	 * @throws Exception
	 */
	private PtSchedLogDTO getInitPtSchedLogDTO(SchedRequest schedRequest) throws Exception {
		PtSchedLogDTO dto = new PtSchedLogDTO();
		dto.setJobName(schedRequest.getJobName());
		dto.setBizModule(schedRequest.getBizModule());
		dto.setFireTime(new Timestamp(sdf.parse(schedRequest.getFireTime()).getTime()));
		dto.setStartTime(new Timestamp(System.currentTimeMillis()));
		dto.setState(PtSchedLogDTO.STATE_RUNNING);
		dto.setThreadId(Thread.currentThread().getId() + "");
		return dto;
	}

	private void checkThread(Map<String, Object> threadMap, String threadNamePrefix) {
        for (Map.Entry<Thread,StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()){  
            Thread thread = (Thread) stackTrace.getKey();
            if (!thread.getName().startsWith(threadNamePrefix)) {
            	continue;
            }
            String stackKey = String.valueOf(thread.getId());
            String counterKey = stackKey + ".counter";
            StringBuilder sb = new StringBuilder("");
            StackTraceElement[] stack = (StackTraceElement[]) stackTrace.getValue();  
            for (StackTraceElement element : stack){  
            	sb.append("\n" + element);  
            }
            //System.out.println(sb.toString());
            if (!threadMap.containsKey(stackKey)) {
            	threadMap.put(stackKey, sb.toString());
            	threadMap.put(counterKey, 1);
            } else {
            	if (!threadMap.get(stackKey).equals(sb.toString())) {
            		threadMap.put(stackKey, sb.toString());
            		threadMap.put(counterKey, 1);
            	} else {
            		int counter = (Integer)threadMap.get(counterKey);
            		counter++;
            		if (counter < 5) {
            			threadMap.put(counterKey, counter);
            		} else {
            			logger.error("线程{}可能发生了死锁{}", thread.getName(), sb.toString());
            		}
            	}
            }
        }
	}

	public boolean isRemoteLog() {
		return isRemoteLog;
	}

	public void setIsRemoteLog(boolean isRemoteLog) {
		this.isRemoteLog = isRemoteLog;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getLogServiceUrl() {
		return logServiceUrl;
	}

	public void setLogServiceUrl(String logServiceUrl) {
		this.logServiceUrl = logServiceUrl;
	}

	public void setLogService(PtSchedLogService logService) {
		this.logService = logService;
	}
}
