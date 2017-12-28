package com.jy.platform.jbpm4.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.history.HistoryActivityInstance;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.model.ActivityCoordinates;
import org.jbpm.api.model.Transition;
import org.jbpm.pvm.internal.history.model.HistoryActivityInstanceImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.jbpm.pvm.internal.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.api.sysauth.SysRoleAPI;
import com.jy.platform.core.common.JYLoggerUtil;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.jbpm4.dto.Jbpm4BizTabDTO;
import com.jy.platform.jbpm4.dto.Jbpm4RoleMappingDTO;
import com.jy.platform.jbpm4.dto.JbpmDTO;
import com.jy.platform.jbpm4.dto.PartnerDTO;
import com.jy.platform.jbpm4.dto.TaskInfo;
import com.jy.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.jy.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.jy.platform.jbpm4.repository.JbpmMapper;
import com.jy.platform.jbpm4.service.IJbpmService;
import com.jy.platform.jbpm4.service.IModifyProInfoService;
import com.jy.platform.jbpm4.service.Jbpm4RoleMappingService;
import com.jy.platform.jbpm4.service.JbpmTastService;
import com.jy.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
import com.jy.platform.jbpm4.temporaryJbpm4Info.service.ITemporaryJbpm4InfoService;
import com.jy.platform.jbpm4.tool.ConstantJBPM;
import com.jy.platform.jbpm4.tool.MyJBPMTool;
import com.jy.platform.jbpm4.tool.StringUtilTools;
import com.jy.platform.jbpm4.tool.ThreadPool;
/**
 * 
 * @Description: jbpm4.4 工作流的service 实现类 
 * @author chen
 * @version 1.0, 
 * @date 2013-8-29 下午01:38:38
 */
@Service("com.jy.platform.jbpm4.service.impl.JbpmServiceImpl")
@SuppressWarnings("all")
public class JbpmServiceImpl implements IJbpmService,Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JbpmServiceImpl.class);
	//线程池初始化
	private static ThreadPoolExecutor executor = ThreadPool.getThreadPool(15,30, 3000, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2000));
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private JbpmMapper mapper;
	//获取 访问业务层的对象
	@Autowired
	@Qualifier("MyJBPMTool")
	private MyJBPMTool myJBPMTool;
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.jbpm4FormInfo.service.impl.Jbpm4FormInfoServiceImpl")
	private IJbpm4FormInfoService	formService;
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.temporaryJbpm4Info.service.impl.TemporaryJbpm4InfoServiceImpl")
	private ITemporaryJbpm4InfoService tempService;
	
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.JbpmTaskServceImpl")
	private JbpmTastService taskService;
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.ModifyProInfoServiceImpl")
	private IModifyProInfoService modifyProService;
	
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.Jbpm4RoleMappingService")
	private Jbpm4RoleMappingService	roleMappingService;
	
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.Jbpm4BizTabService")
	private Jbpm4BizTabService	bizTabService;
	//通过 userId 获取 用户 详细 信息
	@Autowired
	private OrgAPI orgApi;
	@Autowired
    private SessionAPI sessionAPI;
	@Autowired
	private SysRoleAPI sysRoleAPI;
	
	public List queryAllTask(Map<String, Object> param) throws Exception {
		if(param != null){
			JbpmDTO dto  = (JbpmDTO) param.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			String processParticipationName = dto.getProcessParticipationName();
			//如果不为空
			if(StringUtils.isNotEmpty(processParticipationName)){
				StringBuilder sb = new StringBuilder();
				String[] parts = processParticipationName.split(",");
				for(String par: parts){
					if(sb.length() >0)
					sb.append(",");
					sb.append("'").append(par).append("'");
				}
				dto.setProcessParticipationName(sb.toString());
			}
			param.put("dto", dto);
		}
        
		List<Map<String,Object>> dataList = null;
		//查询出待办任务的基本信息列表
		JYLoggerUtil.logCurrentTime("查询待办1", true, this.getClass());
		dataList = mapper.queryTasksByMySql(param);
		JYLoggerUtil.logCurrentTime("查询待办1", false, this.getClass());
		
		JYLoggerUtil.logCurrentTime("查询待办2", true, this.getClass());
		//todo 查询上一环节处理人、业务描述
		dataList = makeupOtherTaskInfo(dataList);
		JYLoggerUtil.logCurrentTime("查询待办2", false, this.getClass());
		JYLoggerUtil.logCurrentTime("查询待办3", true, this.getClass());
		//通过人员ID 获取 人员 姓名 信息 
		dataList = makeupUserInfo(dataList);
		JYLoggerUtil.logCurrentTime("查询待办3", false, this.getClass());
		
		return dataList;
	}
	
	public List queryAllTaskByPaging(Map<String, Object> param) throws Exception {
		if(param != null){
			JbpmDTO dto  = (JbpmDTO) param.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			String processParticipationName = dto.getProcessParticipationName();
			//如果不为空
			if(StringUtils.isNotEmpty(processParticipationName)){
				StringBuilder sb = new StringBuilder();
				String[] parts = processParticipationName.split(",");
				for(String par: parts){
					if(sb.length() >0)
					sb.append(",");
					sb.append("'").append(par).append("'");
				}
				dto.setProcessParticipationName(sb.toString());
			}
			param.put("dto", dto);
		}
        
		List<Map<String,Object>> dataList = null;
		//查询出待办任务的基本信息列表
		dataList = mapper.queryTasksByMySqlByPaging(param);
		//todo 查询上一环节处理人、业务描述
		dataList = makeupOtherTaskInfo(dataList);
		//通过人员ID 获取 人员 姓名 信息 
		dataList = makeupUserInfo(dataList);
		
		return dataList;
	}
	/**
	 *  通过 userID 补充 用户的其他信息
	 * @param dataList
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String, Object>> makeupUserInfo(List<Map<String, Object>> dataList) throws Exception {
		if(dataList != null && dataList.size() >0){
				int count = dataList.size() ;
				final CountDownLatch countDownLatch = new CountDownLatch(count*4);
				
				this.makeupUserInfoUpUserId(dataList,count,countDownLatch);
				this.makeupUserInfoCurOwnerId(dataList,count,countDownLatch);
				this.makeupUserInfoExeUserId(dataList,count,countDownLatch);
				this.makeupUserInfoStartProUserid(dataList,count,countDownLatch);
				//等待子线程都执行完了再执行主线程剩下的动作
				countDownLatch.await();
		}
		return dataList;
	}
	
	/**
	 * 组装 转换UP_USER_ID
	 * @param dataList
	 * @param count 
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> makeupUserInfoUpUserId(List<Map<String, Object>> dataList,int count, final CountDownLatch countDownLatch) throws Exception {
		for(int i = 0;i < count;i ++){
			final Map temp = dataList.get(i);
			String upUserId = (String) temp.get("UP_USER_ID");
			final Map<String,Object> tempParam = new HashMap<String,Object>();
			tempParam.put("upUserId", upUserId);
			executor.execute(new Runnable(){
				public void run() {
					try {
						String rUpUserId  = (String) tempParam.get("upUserId");
						Map<String,Object> resultMap = new HashMap<String,Object>();
						if(StringUtils.isNotEmpty(rUpUserId)&& !"null".equals(rUpUserId)){//通过 用户ID接口 获取 用户的其他信息
							UserInfo upUser = orgApi.getUserInfoDetail(rUpUserId);
							if(upUser != null ) resultMap.put("UP_USER_NAME", upUser.getUserName());
						}
						//回写数据信息
						if(resultMap != null) temp.putAll(resultMap);
					} catch (Exception e) {
						JYLoggerUtil.error(this.getClass(), "======makeupUserInfoUpUserId=======");
					}
					
					countDownLatch.countDown();
				}
			});
		}
		return dataList;
	}
	/**
	 * 组装 转换CUR_OWNER
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> makeupUserInfoCurOwnerId(List<Map<String, Object>> dataList,int count, final CountDownLatch countDownLatch) throws Exception {
			for(int i = 0;i < count;i ++){
				final Map temp = dataList.get(i);
				String curOwnerId = (String) temp.get("CUR_OWNER");
				
				final Map<String,Object> tempParam = new HashMap<String,Object>();
				tempParam.put("curOwnerId", curOwnerId);
				executor.execute(new Runnable(){
					public void run() {
						try {
							String rCurOwnerId = (String) tempParam.get("curOwnerId");
							Map<String,Object> resultMap = new HashMap<String,Object>();
							if(StringUtils.isNotEmpty(rCurOwnerId)&& !"null".equals(rCurOwnerId)){
								//判断执行人是不是用户ID，如果为用户ID则是数字，如果为角色组编码则不是数字
								boolean isUserId = true;
								try{
									Long.parseLong(rCurOwnerId);
								}catch(NumberFormatException e){
									isUserId = false;
								}
								String userName = "";
								if(isUserId){
									UserInfo curUser = orgApi.getUserInfoDetail(rCurOwnerId);
									if(curUser != null ){
										userName = curUser.getUserName();
									}
								}else{
									//查询角色组名称  针对推送到角色而非个人的情况
									Map<String, Object> param = new HashMap<String, Object>();
					        		param.put("roleGroupCode", rCurOwnerId);
					        		List<Map<String, Object>> roleGroupList = sysRoleAPI.queryRoleGroupList(0, 1, param);
									if(null!=roleGroupList && roleGroupList.size()>0){
										Map<String,Object> roleInfo = roleGroupList.get(0);
										userName = (String)roleInfo.get("roleGroupName");
									}
									
								}
								if(userName != null && !"".equals(userName)) resultMap.put("CUR_OWNER_NAME", userName);
							}
							//回写数据信息
							if(resultMap != null) temp.putAll(resultMap);
						} catch (Exception e) {
							JYLoggerUtil.error(this.getClass(), "======makeupUserInfoCurOwnerId=======");
						}
						
						countDownLatch.countDown();
					}
				});
			}
		return dataList;
	}
	/**
	 * 组装 转换START_PRO_USERID
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> makeupUserInfoStartProUserid(List<Map<String, Object>> dataList,int count, final CountDownLatch countDownLatch) throws Exception {
			for(int i = 0;i < count;i ++){
				final Map temp = dataList.get(i);
				
				String startProUserid = (String) temp.get("START_PRO_USERID");
				final Map<String,Object> tempParam = new HashMap<String,Object>();
				tempParam.put("startProUserid", startProUserid);
				executor.execute(new Runnable(){
					public void run() {
						try {
							String rStartProUserid = (String)tempParam.get("startProUserid");
							Map<String,Object> resultMap = new HashMap<String,Object>();
							if(StringUtils.isNotEmpty(rStartProUserid)&& !"null".equals(rStartProUserid)){
								UserInfo startUser = orgApi.getUserInfoDetail(rStartProUserid);
								if(startUser != null ) resultMap.put("START_PRO_USERNAME", startUser.getUserName());
							}
							//回写数据信息
							if(resultMap != null) temp.putAll(resultMap);
						} catch (Exception e) {
							JYLoggerUtil.error(this.getClass(), "======makeupUserInfoStartProUserid=======");
						}
						
						countDownLatch.countDown();
					}
				});
			}
		return dataList;
	}
	/**
	 * 组装 转换EXE_USER_ID
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> makeupUserInfoExeUserId(List<Map<String, Object>> dataList,int count, final CountDownLatch countDownLatch) throws Exception {
			for(int i = 0;i < count;i ++){
				final Map temp = dataList.get(i);
				String exeUserId = (String) temp.get("EXE_USER_ID");
				
				final Map<String,Object> tempParam = new HashMap<String,Object>();
				tempParam.put("exeUserId", exeUserId);
				executor.execute(new Runnable(){
					public void run() {
						try {
							String rExeUserId  = (String) tempParam.get("exeUserId");
							Map<String,Object> resultMap = new HashMap<String,Object>();
							
							if(StringUtils.isNotEmpty(rExeUserId)&& !"null".equals(rExeUserId)){
								UserInfo exeUser = orgApi.getUserInfoDetail(rExeUserId);
								if(exeUser != null ) resultMap.put("EXE_USER_NAME", exeUser.getUserName());
							}
							//回写数据信息
							if(resultMap != null) temp.putAll(resultMap);
						} catch (Exception e) {
							JYLoggerUtil.error(this.getClass(), "======makeupUserInfoExeUserId=======");
						}
						
						countDownLatch.countDown();
					}
				});
			}
		return dataList;
	}
	
	/**
	 * 组装完善 待办任务的其他 有效 信息
	 * CUR_EXE_ID 流程实例ID
	 * MAIN_ID 主流程实例ID
	 * CUR_ACT_NAME 当前活动的任务节点 名称
	 * @param dataList
	 * @return
	 * @throws InterruptedException 
	 */
	private List<Map<String, Object>> makeupOtherTaskInfo(List<Map<String, Object>> dataList) throws Exception {
		
		if(dataList != null && dataList.size() >0){
				//线程池初始化
				
				int count = dataList.size() ;
				final CountDownLatch countDownLatch = new CountDownLatch(count);
				
				for(int i = 0;i < count;i ++){
					final Map temp = dataList.get(i);
					
					String executionId = (String) temp.get("CUR_EXE_ID");
					String mainProInsId = (String) temp.get("MAIN_ID");
					String actName = (String) temp.get("CUR_ACT_NAME");
					final Map<String,Object> tempParam = new HashMap<String,Object>();
					tempParam.put("mainProInsId", mainProInsId);
					tempParam.put("executionId", executionId);
					tempParam.put("actName", actName);
					
					executor.execute(new Runnable(){
						private String transProInsId(String proInsId){
							if(StringUtils.isNotEmpty(proInsId)	&& StringUtilTools.countStr(proInsId, ".") >1){
								//如果含有的.大于1说明其 是会签流程 格式如：会签.90011.2.90025, 其实际流程实例为 会签.90011
								String headStr = proInsId.substring(0, proInsId.indexOf("."));
								String temStr = proInsId.substring(proInsId.indexOf(".") +".".length());
								proInsId = headStr +"."+temStr.substring(0,temStr.indexOf("."));
							}
							return proInsId;
						}
						public void run() {
							try {
								String mainProInsId  = (String) tempParam.get("mainProInsId");
								String executionId = (String) tempParam.get("executionId");
								String actName = (String)tempParam.get("actName");
								//获取 上一节点的处理人信息
								Map<String,Object> resultMap = taskService.getUpTaskDealPersonInfo(tempParam);
								Map<String,Object> bizTabMap = null;
								
								executionId = this.transProInsId(executionId);
								mainProInsId = this.transProInsId(mainProInsId);
								
								if(StringUtils.isNotEmpty(mainProInsId)){//如果使用了 主子流程 
									//获取jbpm4_biz_tab 的相关信息
									bizTabMap = taskService.obtainBizTabInfo(mainProInsId);
								}else{
									//获取jbpm4_biz_tab 的相关信息
									bizTabMap = taskService.obtainBizTabInfo(executionId);
								}
								
								Map<String,Object> formMap = null;
								//通过流程实例获取流程定义 信息
								Map<String, Object> paMap = new HashMap<String,Object>();
								paMap.put("proInsId", executionId);
								Map<String,Object> defMap = mapper.queryProDefindInfo(paMap);
								if(defMap != null){
									String proDefKey = (String)defMap.get("PRO_KEY");
									String proName = (String) defMap.get("PRO_NAME");
									String proDefVersion = String.valueOf((BigDecimal) defMap.get("VERSION"));
									//获取表单 配置 及选人规则的配置信息
									formMap = taskService.obtainJbpm4FormInfo(proDefKey,proDefVersion,actName);
								}else{
									//说明该流程是子流程或是会签 结束
									formMap = null;
								}
								//回写数据信息
								if(resultMap != null) temp.putAll(resultMap);
								if(bizTabMap != null) temp.putAll(bizTabMap);
								if(formMap != null ) temp.putAll(formMap);
							} catch (Exception e) {
								JYLoggerUtil.error(this.getClass(), "=====makeupOtherTaskInfo======");
							}
							
							countDownLatch.countDown();
						}
					});
				}
				//等待子线程都执行完了再执行主线程剩下的动作
				countDownLatch.await();
		}
		return dataList;
	}

	public List queryCompletedTask(Map<String, Object> params) throws Exception {
		if(params != null){
			JbpmDTO dto  = (JbpmDTO) params.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			String processParticipationName = dto.getProcessParticipationName();
			//如果不为空
			if(StringUtils.isNotEmpty(processParticipationName)){
				StringBuilder sb = new StringBuilder();
				String[] parts = processParticipationName.split(",");
				for(String par: parts){
					if(sb.length() >0)
					sb.append(",");
					sb.append("'").append(par).append("'");
				}
				dto.setProcessParticipationName(sb.toString());
			}
			params.put("dto", dto);
		}
		
		List<Map<String,Object>> dataList = null;
		//list = mapper.queryCompletedTasksByMySql2Temp(params);
		//todo 完善当前 任务归属人CUR_OWNER
		dataList = mapper.queryCompletedTasksByMySql(params);
		//组装其他参数信息
		dataList = makeupOtherTaskInfo(dataList);
		//通过人员ID 获取 人员 姓名 信息 
		dataList = makeupUserInfo(dataList);
		return dataList;
	}
	public List queryCompletedTaskByPaging(Map<String, Object> params) throws Exception {
		if(params != null){
			JbpmDTO dto  = (JbpmDTO) params.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			String processParticipationName = dto.getProcessParticipationName();
			//如果不为空
			if(StringUtils.isNotEmpty(processParticipationName)){
				StringBuilder sb = new StringBuilder();
				String[] parts = processParticipationName.split(",");
				for(String par: parts){
					if(sb.length() >0)
					sb.append(",");
					sb.append("'").append(par).append("'");
				}
				dto.setProcessParticipationName(sb.toString());
			}
			params.put("dto", dto);
		}
		
		//list = mapper.queryCompletedTasksByMySql2Temp(params);
		//todo 完善当前 任务归属人CUR_OWNER
		List<Map<String,Object>> dataList = mapper.queryCompletedTasksByMySqlByPaging(params);
		//组装其他参数信息
		dataList = makeupOtherTaskInfo(dataList);
		//通过人员ID 获取 人员 姓名 信息 
		dataList = makeupUserInfo(dataList);
		return dataList;
	}
	public List<Map<String,Object>> queryEndTaskList(Map<String, Object> params) throws Exception{
		List<Map<String,Object>> dataList = null;
		try {
			if(params != null){
				JbpmDTO dto  = (JbpmDTO) params.get("dto");
				 //是否管理监控模块
				if(dto != null  && "1".equals(dto.getIsMonitor())){
		     	//处理角色映射权限控制 需要通过角色映射模块配置
					dealRoleMapping(dto);
				}
				String processParticipationName = dto.getProcessParticipationName();
				//如果不为空
				if(StringUtils.isNotEmpty(processParticipationName)){
					StringBuilder sb = new StringBuilder();
					String[] parts = processParticipationName.split(",");
					for(String par: parts){
						if(sb.length() >0)
						sb.append(",");
						sb.append("'").append(par).append("'");
					}
					dto.setProcessParticipationName(sb.toString());
				}
				params.put("dto", dto);
			}
			//list = mapper.queryEndTaskInfo(params);
			
			dataList = mapper.queryPersonalEndTaskInfo(params);
			
			dataList = makeupPersonalEndTaskInfo(dataList);
			//通过人员ID 获取 人员 姓名 信息 
			dataList = makeupUserInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	public List<Map<String,Object>> queryEndTaskListByPaging(Map<String, Object> params) throws Exception {
		if(params != null){
			JbpmDTO dto  = (JbpmDTO) params.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			String processParticipationName = dto.getProcessParticipationName();
			//如果不为空
			if(StringUtils.isNotEmpty(processParticipationName)){
				StringBuilder sb = new StringBuilder();
				String[] parts = processParticipationName.split(",");
				for(String par: parts){
					if(sb.length() >0)
					sb.append(",");
					sb.append("'").append(par).append("'");
				}
				dto.setProcessParticipationName(sb.toString());
			}
			params.put("dto", dto);
		}
		List<Map<String,Object>> dataList = mapper.queryPersonalEndTaskInfoByPaging(params);
		
		dataList = makeupPersonalEndTaskInfo(dataList);
		//通过人员ID 获取 人员 姓名 信息 
		dataList = makeupUserInfo(dataList);
		return dataList;
	}
	/**
	 * 通过流程实例id 补充 其流程定义信息
	 * @param dataList
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String, Object>> makeupPersonalEndTaskInfo(List<Map<String, Object>> dataList) throws Exception {
		if(dataList != null && dataList.size() >0){
				int count = dataList.size() ;
				//定义个数
				final CountDownLatch countDownLatch = new CountDownLatch(count);
				for(int i = 0;i < count;i ++){
					final Map temp = dataList.get(i);
					
					String proInsId = (String) temp.get("PRO_INSTANCE_ID");
					final Map<String,Object> tempParam = new HashMap<String,Object>();
					tempParam.put("proInsId", proInsId);
					
					executor.execute(new Runnable(){
						private String transProInsId(String proInsId){
							if(StringUtils.isNotEmpty(proInsId)	&& StringUtilTools.countStr(proInsId, ".") >1){
								//如果含有的.大于1说明其 是会签流程 格式如：会签.90011.2.90025, 其实际流程实例为 会签.90011
								String headStr = proInsId.substring(0, proInsId.indexOf("."));
								String temStr = proInsId.substring(proInsId.indexOf(".") +".".length());
								proInsId = headStr +"."+temStr.substring(0,temStr.indexOf("."));
							}
							return proInsId;
						}
						public void run() {
							try {
								String proInsId  = (String) tempParam.get("proInsId");
								//通过流程实例获取流程定义 信息
								proInsId = this.transProInsId(proInsId);
								Map<String, Object> paMap = new HashMap<String,Object>();
								paMap.put("proInsId", proInsId);
								Map<String,Object> defMap = mapper.queryProDefindInfo(paMap);
								
								if(defMap != null){
									String proName = (String) defMap.get("PRO_NAME");
									String proDefVersion = String.valueOf((BigDecimal) defMap.get("VERSION"));
									temp.put("VERSION", proDefVersion);
									temp.put("NAME", proName);
								}
							} catch (Exception e) {
								JYLoggerUtil.error(this.getClass(), "=====makeupPersonalEndTaskInfo======");
							}
							
							countDownLatch.countDown();
						}
						
					});
				}
				//等待子线程都执行完了再执行主线程剩下的动作
				countDownLatch.await();
			
		}
		
		return dataList;
	}
	@SuppressWarnings("all")
	public List queryActiveProcessInfoList(Map<String, Object> params) throws Exception {
		if(params != null){
			JbpmDTO dto  = (JbpmDTO) params.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			params.put("dto", dto);
		}
		List list = null;
		try {
			//list = dao.queryListBySql("QUERY_ACTIVE_PRO_INFO_BY_MY_SQL", params);
			
			list = mapper.queryActiveProInfoByMySql(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/*
	 * (non-Javadoc)
	 * @see com.jy.platform.jbpm4.service.IJbpmService#queryEndProInfoList(java.util.Map)
	 */
	public List<Map<String,Object>> queryEndProInfoList(Map<String, Object> params) throws Exception {
		if(params != null){
			JbpmDTO dto  = (JbpmDTO) params.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			params.put("dto", dto);
		}
		List<Map<String,Object>> list = null;
		try {
			//list = dao.queryListBySql("QUERY_END_PRO_INFO_BY_MY_SQL", params);
			
			list = mapper.queryEndPrInfoByMySql(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Map<String,Object>> queryEndProInfoListByPaging(Map<String, Object> params) throws Exception {
		if(params != null){
			JbpmDTO dto  = (JbpmDTO) params.get("dto");
			 //是否管理监控模块
			if(dto != null  && "1".equals(dto.getIsMonitor())){
	     	//处理角色映射权限控制 需要通过角色映射模块配置
				dealRoleMapping(dto);
			}
			params.put("dto", dto);
		}
		List<Map<String,Object>> list = mapper.queryEndPrInfoByMySqlByPaging(params);
		//补充jbpm4_biz_tab 信息
		list = makeupJbpm4BizTabInfo(list);
		return list;
	}
	private List<Map<String, Object>> makeupJbpm4BizTabInfo(List<Map<String, Object>> dataList) throws Exception {
		if(dataList == null || dataList.size() == 0) return dataList;
		
		int count = dataList.size() ;
		final CountDownLatch countDownLatch = new CountDownLatch(count);
		for(int i = 0;i < count;i ++){
			final Map temp = dataList.get(i);
			final String proInsId = (String) temp.get("CUR_EXE_ID");
			
			executor.execute(new Runnable(){
				public void run() {
					try {
						if(StringUtils.isNotEmpty(proInsId)){
							Jbpm4BizTabDTO dto = bizTabService.searchJbpm4BizTabByProInsId(proInsId);
							if(dto != null){
								temp.put("BIZ_INF_NAME", dto.getBizInfName());
								temp.put("BIZ_INF_NO", dto.getBizInfNo());
								temp.put("BIZ_TYPE", dto.getBizType());
							}
						}
					} catch (Exception e) {
						JYLoggerUtil.error(this.getClass(), "=====makeupJbpm4BizTabInfo======");
					}
					
					countDownLatch.countDown();
				}
			});
		}
		//等待子线程都执行完了再执行主线程剩下的动作
		countDownLatch.await();
	
		return dataList;
	}

	public String productXmlByXmlContent(String xmlContent) {
        
		ClassLoader cl= ModifyProInfoServiceImpl.class.getClassLoader();
		URL url=cl.getResource("proxml");
		String urlPath = url.getFile();
		String templatePath = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/proxml/";
        String path = url.getFile();
        
		String xmlPath = templatePath;//path +"/";
		
		//String xmlPath = ConstantJBPM.JbpmXmlfilePath;//BaseConfiguration.getString("jbpm.xmlfile.path");
		//用它可以产生一个号称全球唯一的ID
		UUID uuid = UUID.randomUUID();
		String realPath = "";
		String tempPath =  xmlPath + uuid+".xml";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddmmss");
		
		realPath = xmlPath + dateFormat.format(new Date())  + ".jpdl.xml";
		
		//向文件写xml内容信息
		Writer writer = null;
		try {
            File file = new File(realPath);
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            writer.write(xmlContent);
            
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	if(writer != null ){
        		try {
        			writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
				
        }
        
        
		return realPath;
	}

	public List<ProcessDefinition> getAllPdList(JbpmDTO dto,DataMsg pageData) {
	    ProcessDefinitionQuery query = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().orderDesc(ProcessDefinitionQuery.PROPERTY_DEPLOYMENT_TIMESTAMP);
		
		if(StringUtils.isNotEmpty(dto.getProcessDefinitionId()))
		query.processDefinitionId(dto.getProcessDefinitionId());
		
		if(StringUtils.isNotEmpty(dto.getProcessDefinitionKey()))
		query.processDefinitionKey(dto.getProcessDefinitionKey());
		
		if(StringUtils.isNotEmpty(dto.getProcessDefinitionName()))
		query.processDefinitionNameLike("%"+dto.getProcessDefinitionName()+"%");
		List<ProcessDefinition> pdList = query.list();
		if(null!=pageData){
		    List<ProcessDefinition> pageList = new ArrayList<ProcessDefinition>();
		    int totalRows = pdList.size();
		    int pageIndex = pageData.getPageIndex();
	        int pageSize = pageData.getPageSize();
	        int startRow = (pageIndex-1)*pageSize;
	        int endRow = pageIndex*pageSize;
	        if(endRow>totalRows){
	            endRow = totalRows;
	        }
	        pageData.setTotalRows(totalRows);
	        
	        for(int i=0;i<pdList.size();i++){
	            if(i>=startRow && i<endRow){
	                pageList.add(pdList.get(i));
	            }
	        }
	        pdList = pageList;
		}
		return pdList;
	}

	public String getAllTurenDirectory(String processInstanceId,String currentActiveName) throws Exception {
	
		
		return getAllTurenDirectory( processInstanceId, currentActiveName,null,null);
	}
	public String getAllTurenDirectory(String processInstanceId,String currentActiveName,String isJump,String isBack) throws Exception {
		 //ProcessInstance processInstance = executionService.startProcessInstanceByKey("DecisionConditions", variables);
		 //System.out.println(processInstance.isActive("submit document"));
		 if(StringUtils.isEmpty(processInstanceId) || StringUtils.isEmpty(currentActiveName))    return "";
		//获取流程实例
		processInstanceId = this.transProInsId(processInstanceId);
		ProcessInstance pi = myJBPMTool.getExecutionService().findProcessInstanceById(processInstanceId);
		//如果没有查询到 
		if(pi == null ) return "";
		//检验流程实例pi，是否结束了  
       //System.out.println(pi.isEnded());  
       JYLoggerUtil.info(this.getClass(), currentActiveName+"--节点是否是当前节点："+pi.isActive(currentActiveName));
       //当流程实例不需要向下进行时，而直接结束流程  
       //参数：一个流程实例ID，和一个结束流程的理由   executionService.endProcessInstance(pi.getId(), "取消流程实例");  
       
		//获取流程定义
		String processDefinitionId = pi.getProcessDefinitionId();
		//获取流程定义
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();

		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) processDefinition;
		String id=formService.queryJbpm4FormInfoIdBysql(processDefinition.getKey(), Integer.toString(processDefinition.getVersion()), currentActiveName);
		Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(id));
		Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO modifyDto = formService.queryOneJbpm4FormInfo(paramMap);
		ActivityImpl sourceActivity = pd.findActivity(currentActiveName);
		//获取从该节点出去的 流转方向
		List<Transition> list = (List<Transition>) sourceActivity.getOutgoingTransitions();
		
		List<String> turns = new ArrayList<String>();
		StringBuffer turnDirections = new StringBuffer("[");
		
		for(Transition temp:list){
			TransitionImpl tempImpl = (TransitionImpl)temp;
			String turnDir = tempImpl.getName();
			if(turnDir == null) turnDir="";
			//排除路径是系统开头的 
			//系统 开头的路径名称不展示
			if(!turnDir.startsWith("系统")){
				turns.add(turnDir);
			}
		}
		if(ConstantJBPM.JUMP_STATE_TRUE.equals(isJump)||ConstantJBPM.SHOW_JUMP.equals(modifyDto.getExt3())){
			turns.add(ConstantJBPM.JUMP_OUT_NAME);
		}
		if(ConstantJBPM.BACK_STATE_TRUE.equals(isBack)||ConstantJBPM.SHOW_BACK.equals(modifyDto.getExt3())){
			turns.add(ConstantJBPM.BACK_OUT_NAME);
		}
		if(ConstantJBPM.SHOW_ALL.equals(modifyDto.getExt3())){
			turns.add(ConstantJBPM.JUMP_OUT_NAME);
			turns.add(ConstantJBPM.BACK_OUT_NAME);
		}
		//排序
		Collections.sort(turns);
		int countTwo = 0;
		for (int i = 0; i < turns.size(); i++) {
			String turnDir = turns.get(i); 
			//排除 添加的返回线
			if(turnDir.indexOf("regect")== -1){
				if(countTwo != 0)
				turnDirections.append(",");
				
				turnDirections.append("{turnDir:'").append(turnDir).append("'}");
				countTwo ++;
			}
		}
		String returnStr = turnDirections.append("]").toString();
		JYLoggerUtil.info(this.getClass(), returnStr);
		
		return returnStr;
	}
	public String getTurenDirectory(String proDefId,String currentActiveName) {
        
		//获取流程定义
		String processDefinitionId = proDefId;
		//获取流程定义
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();

		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) processDefinition;

		ActivityImpl sourceActivity = pd.findActivity(currentActiveName);
		//获取从该节点出去的 流转方向
		List<Transition> list = (List<Transition>) sourceActivity.getOutgoingTransitions();
		
		StringBuffer turnDirections = new StringBuffer("[");
		int countTwo = 0;
		
		for(Transition temp:list){
			TransitionImpl tempImpl = (TransitionImpl)temp;
			
			String turnDir = tempImpl.getName();
			
			if(turnDir == null) turnDir="";
			//排序代码 添加的返回线
			if(turnDir.indexOf("regect")== -1){
				
				if(countTwo != 0)
				turnDirections.append(",");
				
				turnDirections.append("{turnDir:'").append(turnDir).append("'}");
				
				countTwo ++;
			}
		}
		
		String returnStr = turnDirections.append("]").toString();
		JYLoggerUtil.info(this.getClass(), returnStr);
		
		return returnStr;
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.jy.platform.jbpm4.service.IJbpmService#getIncomingTransitions(java.lang.String, java.lang.String)
	 */
	public String getIncomingTransitions(String processInstanceId,String activeName) {
		 if(StringUtils.isEmpty(processInstanceId) || StringUtils.isEmpty(activeName))    return "";
			//获取流程实例
		 	processInstanceId = this.transProInsId(processInstanceId);
			ProcessInstance pi = myJBPMTool.getExecutionService().findProcessInstanceById(processInstanceId);
			//如果没有查询到 
			if(pi == null ) return "";
			//检验流程实例pi，是否结束了  
	        //System.out.println(pi.isEnded());  
	        //JYLoggerUtil.info(this.getClass(), activeName+"----------------节点是否是当前节点："+pi.isActive(activeName));
	        //当流程实例不需要向下进行时，而直接结束流程  
	        //参数：一个流程实例ID，和一个结束流程的理由   executionService.endProcessInstance(pi.getId(), "取消流程实例");  
	        
	        StringBuffer turnDirections = new StringBuffer("[");
	        //获取流程定义
			String processDefinitionId = pi.getProcessDefinitionId();
			turnDirections = this.makeupBackNodesString(processInstanceId, activeName, processDefinitionId, turnDirections);
			String returnStr = turnDirections.append("]").toString();
			JYLoggerUtil.info(this.getClass(), returnStr);
			return returnStr;
	}

	private StringBuffer makeupBackNodesString(String processInstanceId,String activeName,String processDefinitionId,StringBuffer turnDirections){
		//获取流程定义
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();
		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) processDefinition;
		List<ActivityImpl> list1 = (List<ActivityImpl>)pd.getActivities();
		for(ActivityImpl actDef:list1){
			System.out.println("name:"+actDef.getName()+", type:"+actDef.getType());
		}
		ActivityImpl sourceActivity = pd.findActivity(activeName);
		
		//获取 流进 该节点的 流转方向
		List<Transition> list = (List<Transition>) sourceActivity.getIncomingTransitions();
		int countTwo = 0;
		for(Transition temp:list){
			TransitionImpl tempImpl = (TransitionImpl)temp;
			String destName = tempImpl.getSource().getName();
			JYLoggerUtil.info(this.getClass(), "------------回退目标节点的类型"+tempImpl.getSource().getType());
			//如果是决策节点 则不展示在回退中
			if(tempImpl.getSource().getType().equalsIgnoreCase("decision")){
				turnDirections = this.makeupBackNodesString(processInstanceId, tempImpl.getSource().getName(), processDefinitionId, turnDirections);
				continue;
			}
			//如果有多次 同一条轨迹 则 跳过
			if(turnDirections.toString().indexOf("'"+destName+"'") > -1) continue;
			//排序代码 添加的返回线
			if(turnDirections.toString().indexOf("destName") >0)
				turnDirections.append(",");
			turnDirections.append("{destName:'").append(destName).append("'");
			//判断当前 流程实例ID下 该环节是否 执行过的 历史任务 ，且历史执行 距离当前是否是最后 一个节点
			Map<String,String> param = new HashMap<String,String>();
			param.put("proInsId", processInstanceId);
			param.put("actName", tempImpl.getSource().getName());
			
			List<Map<String,String>> resList = mapper.queryExecuteHisLog(param);
			if(resList != null && resList.size() > 0){
				Map<String, String> resultHisMap = resList.get(0);
				String oldUserId = resultHisMap.get("EXE_USER_ID");
				
				turnDirections.append(",isHis:'").append("Y").append("'");
				turnDirections.append(",hisOwner:'").append(oldUserId).append("'");
			}else{
				turnDirections.append(",isHis:'").append("N").append("'");
				turnDirections.append(",hisOwner:'").append("-1").append("'");//-1表示 待分配
			}
			
			turnDirections.append("}");
		}
		
		return turnDirections;
	}
	public String getAllActiveProcNodeName(String mainProcessInstanceId,
			String subProcessInstanceId) {
		String mainId = mainProcessInstanceId;	
		String subId = subProcessInstanceId;
		//参与者
		String player = "";
		
		ProcessInstance pi = null;
		if(StringUtils.isNotEmpty(mainId)){
			mainId = this.transProInsId(mainId);
		 	pi = myJBPMTool.getExecutionService().findProcessInstanceById(mainId);
		}else{
			subId = this.transProInsId(subId);
			pi = myJBPMTool.getExecutionService().findProcessInstanceById(subId);
		}
		
		
		//开始组装json数据
		StringBuffer actSB = new StringBuffer("[");
		int count = 0;
		if(pi != null){
			//获取流程定义
			String processDefinitionId = pi.getProcessDefinitionId();
			//获取流程定义 
			ProcessDefinition processDefinition = myJBPMTool.getRepositoryService()
					.createProcessDefinitionQuery().processDefinitionId(
							processDefinitionId).uniqueResult();
			
			// 获取所有活跃的节点：
			Set<String> activitySet = pi.findActiveActivityNames();
			for( String activityName : activitySet ) {
			  ActivityCoordinates ac = myJBPMTool.getRepositoryService().getActivityCoordinates(pi.getProcessDefinitionId(),activityName);
			  boolean isSub = false;
			  ProcessDefinitionImpl processDe =(ProcessDefinitionImpl)processDefinition;
			  List list = processDe.getActivities();
				for(int i=0;i<list.size();i++){
					ActivityImpl activityImpl = (ActivityImpl)list.get(i);
					if(activityName.equals(activityImpl.getName()) ){
						//通过 活动实例ID 和 活动实例名称 取出   ASSIGNEE_ 处理人
						//参与者
						String pronInsId = "";
						
						if(StringUtils.isNotEmpty(mainId)){//主流程
							pronInsId = this.transProInsId(mainId);
						}else{// 子流程
							pronInsId = this.transProInsId(subId);
						}
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("mainId", pronInsId);
						param.put("name", activityImpl.getName());
						player = mapper.queryCurrentPlayer(param);
						if(StringUtils.isNotEmpty(player)){
							UserInfo palyerUser = orgApi.getUserInfoDetail(player);
							if(palyerUser != null) player = palyerUser.getUserName();//palyerUser.getOrgName()+":"+ palyerUser.getUserName();
						}
						//当前节点是 子流程节点
						if("sub-process".equals(activityImpl.getType())){
							isSub = true;
						}
						break;
					}
				}
				
				//开始组装json 数据
				if(count != 0) actSB.append(",");
					
				actSB.append("{aName:'").append(activityName);
				actSB.append("',aX:'").append(ac.getX()).append("'");
				actSB.append(",aY:'").append(ac.getY()).append("'");
				
				actSB.append(",aWidth:'").append(ac.getWidth()).append("'");
				actSB.append(",aHeight:'").append(ac.getHeight()).append("'");
				//添加参与者信息
				actSB.append(",player:'").append(player).append("'");
				//是否是子流程 默认不是
				if(isSub) actSB.append(",aSub:'true'");
				
				actSB.append("}");
				
				count ++;
			}
		}
		
		actSB.append("]");
		String resultStr = actSB.toString();
		JYLoggerUtil.info(this.getClass(), "---------------当前流程实例活跃的活动列表信息："+resultStr);
		
		return resultStr;
	}
	
	/**
	 * 通过自定义sql 查询所有流程实例下所有已经完成的节点
	 */
	public String getAllFinishedProcNodeName(String mainProcessInstanceId,
			String subProcessInstanceId) {
		String mainId = mainProcessInstanceId;
		String subId = subProcessInstanceId;
		
		ProcessInstance pi = null;
		if(StringUtils.isNotEmpty(mainId)){
			mainId = this.transProInsId(mainId);
		 	pi = myJBPMTool.getExecutionService().findProcessInstanceById(mainId);
		}else{
			subId = this.transProInsId(subId);
			pi = myJBPMTool.getExecutionService().findProcessInstanceById(subId);
		}
		//获取流程定义id
		String pdId = pi.getId();//pi.getProcessDefinitionId();
		//获取流程定义 
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pi.getProcessDefinitionId()).uniqueResult();
		
		// 获取所有已经完成的节点：
		List<HistoryActivityInstance> hisActInstances = myJBPMTool.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(pdId).list();
		//同一个活动可能来回执行几次 用来存储不重复的活动信息
		List<HistoryActivityInstance> tempHisActs = new ArrayList<HistoryActivityInstance>();
		//去掉重复的 多次完成的同一个活动信息
		for(HistoryActivityInstance hai : hisActInstances){
			HistoryActivityInstanceImpl haiImpl = (HistoryActivityInstanceImpl)hai;
			//通过活动的名称 来判断是否相同
			String activityName = haiImpl.getActivityName();
			boolean isExists = false;
			for(int k = 0;k < tempHisActs.size();k ++){
				HistoryActivityInstanceImpl tempHai = (HistoryActivityInstanceImpl) tempHisActs.get(k);
				String tempActName= tempHai.getActivityName();
				if(activityName.equals(tempActName)){
					isExists = true;
					break;
				}
			}
			//如果此活动在 tempHisActs 没有 则 add
			if(!isExists)tempHisActs.add(hai);
		}
		
		
		//开始组装json数据
		StringBuffer finisedActSB = new StringBuffer("[");
		int count = 0;
		
		for(HistoryActivityInstance hai : tempHisActs){
			HistoryActivityInstanceImpl haiImpl = (HistoryActivityInstanceImpl)hai;
			
			String type = haiImpl.getType();
			//只有是任务的节点 才读取tableId 以及 dataId 
			if("task".equals(type)){
				String activityName = haiImpl.getActivityName();
				//通过流程定义id 及节点名称 获取该节点在流程定义中的位置信息 属性信息
				ActivityCoordinates ac = myJBPMTool.getRepositoryService().getActivityCoordinates(pi.getProcessDefinitionId(),activityName);
				
				boolean isSub = false;
				ProcessDefinitionImpl processDe =(ProcessDefinitionImpl)processDefinition;
				List list = processDe.getActivities();
				    
				for(int i=0;i<list.size();i++){
					ActivityImpl activityImpl = (ActivityImpl)list.get(i);
					if(activityName.equals(activityImpl.getName()) ){
						//当前节点是 子流程节点
						if("sub-process".equals(activityImpl.getType())){
							isSub = true;
						}
						break;
					}
				}
				
				
				//开始组装json 数据
				if(count != 0)
					finisedActSB.append(",");
					
				finisedActSB.append("{aName:'").append(activityName);
				finisedActSB.append("',aX:'").append(ac.getX()).append("'");
				finisedActSB.append(",aY:'").append(ac.getY()).append("'");
				
				finisedActSB.append(",aWidth:'").append(ac.getWidth()).append("'");
				finisedActSB.append(",aHeight:'").append(ac.getHeight()).append("'");
				//是否是子流程 默认不是
				if(isSub) finisedActSB.append(",aSub:'true'");
				
				finisedActSB.append("}");
				
				count ++;
			}
			
		}
		finisedActSB.append("]");
		String resultStr = finisedActSB.toString();
		JYLoggerUtil.info(this.getClass(), "---------------已经完成的活动列表信息："+resultStr);
		
		return resultStr;
	}

	public String getAllProcNodeName(String processInstanceId) {
		
		ProcessInstance pi = null;
		if(StringUtils.isNotEmpty(processInstanceId)){
			processInstanceId = this.transProInsId(processInstanceId);
		 	pi = myJBPMTool.getExecutionService().findProcessInstanceById(processInstanceId);
		}
		//获取流程定义
		String processDefinitionId = pi.getProcessDefinitionId();
		//获取流程定义 
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).uniqueResult();
		
		ProcessDefinitionImpl processDe =(ProcessDefinitionImpl)processDefinition;
		//获取流程定义中的所有节点信息
		List ActivietiesList = processDe.getActivities();
		
		StringBuffer destSB = new StringBuffer("[");
		int countNum = 0;
		for(int i = 0;i<ActivietiesList.size();i++){
			ActivityImpl activityImpl = (ActivityImpl)ActivietiesList.get(i);
			
			String type = activityImpl.getType();
			
			if("task".equals(type)|| "end".equals(type) || "sub-process".equals(type)){
				if(countNum != 0)
				destSB.append(",");
				
				destSB.append("{destName:'").append(activityImpl.getName()).append("'}");
				countNum ++;
			}
			
		}
		
		String returnStr = destSB.append("]").toString();
		JYLoggerUtil.info(this.getClass(), returnStr);
		
		return returnStr;
	}
	
	
	@Transactional
	public void completeTask(String taskId, String turnDir,
			Map<String, Object> variables) throws Exception {
		
		//如果是 会签 操作
		if("会签".equals(turnDir) ){
			// 取 前台设置的多个参与者信息
			String partnerIds = (String) variables.get("owner");
			
			JYLoggerUtil.info(this.getClass(), "--------会签，参与者ids："+partnerIds);
			
			String[] ds = partnerIds.split(",");
			//会签时 参与者owner 为数组 String[]
			variables.put("partners", ds);
			//会签时 指定 达到法定个数后方能 进行下一个节点
			int size =  ds.length;
			variables.put("quorum",size);
		}else{
			TaskImpl currentTask = (TaskImpl)myJBPMTool.getTaskService().getTask(taskId);
			ProcessInstance proIns = currentTask.getProcessInstance();
			//获取会签 quorum 参数信息
			String proId = proIns.getId();
			//没有使用主子流程的会签取值
			Object o = myJBPMTool.getExecutionService().getVariable(proId, "quorum");
			Object partners = myJBPMTool.getExecutionService().getVariable(proId, "partners");
			//判断是否是 子流程
			//如果是false 则说明是主流程，true 则是子流程
			boolean isSubPro = proIns.isSuspended();
			if(o == null && isSubPro){
				//使用了主子流程的会签 取值方法
				//获取主流程实例的参数quorum 信息
				//proInsId
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("proInsId", proId);
				Map resultMap = mapper.queryMainProInsIdByHuiQainSubPro(params);
				if(resultMap != null){
					String mainProId = (String) resultMap.get("PRO_INS_ID");
					o = myJBPMTool.getExecutionService().getVariable(mainProId, "quorum");
					partners = myJBPMTool.getExecutionService().getVariable(proId, "partners");
				}
				
			}
			int size = 1;
			try{
				if(o != null){
					size = Integer.valueOf(String.valueOf(o));
				}
			}catch(Exception e){
				e.printStackTrace();
				size = 1;
			}
			
			if(o != null){
				variables.put("quorum",size);
				
			}
			
			if(partners != null){
				//为保证返回参数一致性 非会签时 参与者owner 为数组 String[] 暂时处理方法
				String[] ds = "1,2".split(",");//(String[]) partners;
				variables.put("partners", ds);
			}
		}
		
		
		
		
		if(StringUtils.isNotEmpty(turnDir)){
			myJBPMTool.dealWithTask3(taskId, turnDir, variables);
		}else{
			myJBPMTool.dealWithTask2(taskId, variables);
		}
		
		//工作流事务 与 ibatis的事务结合 处理测试
		//userService.deleteMethod();
		
	}
	
	
	@Transactional(rollbackFor= Exception.class)
	public ProcessInstance startProcessInstanceByKey(String proKey,
			Map<String, Object> variables) throws Exception {
		
		ProcessInstance proIns = myJBPMTool.startProcessInstanceByKey(proKey, variables);
		
		return proIns;
	}
	
	@Transactional
	public String singleBackWay(String taskId, String destName,
			String processInstanceId, Map<String, Object> variables) {
		String str = myJBPMTool.singleBackWay(taskId, destName, processInstanceId,variables);
		return str;
	}

	
	public List queryPartnerListInfo(PartnerDTO dto ) throws Exception {
		String sqlId = "query_by_my_sql_"+dto.getPartnerSqlId();
		
		
		Map<String,Object> params = new HashMap<String,Object>();
		//params.put("pager", page);
		params.put("dtoPar", dto);
		
		
		List dataList = new ArrayList();
		
		return dataList;
	}

	public String getProcNodeName(String processDefinitionId) {
		//getAllActiveProcNodeName
		
		//开始组装json数据
		StringBuffer actSB = new StringBuffer("[");
		int count = 0;
		//获取流程定义 
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(
						processDefinitionId).uniqueResult();
		
		ProcessDefinitionImpl processDe =(ProcessDefinitionImpl)processDefinition;
		//获取流程定义中的所有节点信息
		List ActivietiesList = processDe.getActivities();
	
		for(int i=0; i< ActivietiesList.size();i++){
			 
			boolean isSub = false;
			  
			ActivityImpl activityImpl = (ActivityImpl)ActivietiesList.get(i);
			
			String activityName = activityImpl.getName();
			//当前节点是 子流程节点
			if("sub-process".equals(activityImpl.getType())){
				isSub = true;
				continue;
			}
			
			if("start".equals(activityImpl.getType()) 
				|| "end".equals(activityImpl.getType()) 
				|| "decision".equals(activityImpl.getType())
				|| "foreach".equals(activityImpl.getType())
				|| "join".equals(activityImpl.getType())
					){
				
				continue;
			}
			//通过流程定义id 及节点名称 获取该节点在流程定义中的位置信息 属性信息
			ActivityCoordinates ac = myJBPMTool.getRepositoryService().getActivityCoordinates(processDefinitionId,activityName);
			
			//开始组装json 数据
			if(count != 0) actSB.append(",");
				
			actSB.append("{aName:'").append(activityName);
			actSB.append("',aX:'").append(ac.getX()).append("'");
			actSB.append(",aY:'").append(ac.getY()).append("'");
			
			actSB.append(",aWidth:'").append(ac.getWidth()).append("'");
			actSB.append(",aHeight:'").append(ac.getHeight()).append("'");
			//是否是子流程 默认不是
			if(isSub) actSB.append(",aSub:'true'");
			
			actSB.append("}");
			
			count ++;
				
		}
		
		actSB.append("]");
		String resultStr = actSB.toString();
		JYLoggerUtil.info(this.getClass(), "---------------当前流程定义 节点信息信息："+resultStr);
		
		return resultStr;
	}

	public String getSubProListInfo(String mainProId, boolean isInstance)
			throws Exception {
		//开始组装json数据
		StringBuffer actSB = new StringBuffer("[");
		//如果没有主流id 则返回
		if(StringUtils.isEmpty(mainProId)){
			return actSB.append("]").toString();
		}
		//系统处理特殊字符后需要转义
		mainProId = StringUtilTools.repMyStrToSpecial(mainProId);
		//获取流程定义
		
		String processDefinitionId = "";
		
		if(!isInstance){
			mainProId = this.transProInsId(mainProId);
			//ProcessInstance pi = myJBPMTool.getExecutionService().findProcessInstanceById(mainProId);
			HistoryProcessInstance hisProcessInstance = myJBPMTool.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(mainProId).uniqueResult();
			processDefinitionId = hisProcessInstance.getProcessDefinitionId();
			
		}else{
			processDefinitionId = mainProId;
		}
		
		
		int count = 0;
		
		 //获取流程定义 
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(
						processDefinitionId).uniqueResult();
		
		ProcessDefinitionImpl processDe =(ProcessDefinitionImpl)processDefinition;
		
		String deploymentId = processDe.getDeploymentId();
		
		String resourceName = "";//"uploadProcessXml/201402263403.jpdl.xml";//processDe.getName();
		Set<String> resourceNameSet = myJBPMTool.getRepositoryService().getResourceNames(deploymentId);
		
		Iterator<String> it = resourceNameSet.iterator(); 
		//取最新的 resourName;
		while(it.hasNext() ){
			String temp = (String) it.next();
			resourceName = temp;
		}

		InputStream is = myJBPMTool.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder db=dbf.newDocumentBuilder();
        Document doc=db.parse(is);
		
		Element root = doc.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("sub-process");
		//如果有 子流程的话 会进行该循环
		for(int i = 0;i < nodeList.getLength(); i ++){
			Element myElemnet = (Element)nodeList.item(i);
			String subProKey = XmlUtil.attribute(myElemnet , "sub-process-key");// sub-process-key
			String subProName = XmlUtil.attribute(myElemnet , "name");// sub-process-key
			
			String[] gInfo =XmlUtil.attribute(myElemnet , "g").split(",");// g 的 <sub-process outcome='#{result}' sub-process-key='subOne' g='286,113,109,45' name='子流程One'> 
			
			int ax = 0,ay = 0,aw =0,ah = 0;
			if(gInfo.length >0) ax = Integer.parseInt(gInfo[0]);
			if(gInfo.length >1) ay = Integer.parseInt(gInfo[1]);
			if(gInfo.length >2) aw = Integer.parseInt(gInfo[2]);
			if(gInfo.length >3) ah = Integer.parseInt(gInfo[3]);
			 
			boolean isSub = true;
			  
			//通过流程定义id 及节点名称 获取该节点在流程定义中的位置信息 属性信息
			//开始组装json 数据
			if(count != 0) actSB.append(",");
				
			actSB.append("{aName:'").append(subProName).append("'");
			actSB.append(",subProKey:'").append(subProKey).append("'");
			
			/**/
			actSB.append(",aX:'").append(ax).append("'");
			actSB.append(",aY:'").append(ay).append("'");
			
			actSB.append(",aWidth:'").append(aw).append("'");
			actSB.append(",aHeight:'").append(ah).append("'");
			//是否是子流程 默认不是
			if(isSub) actSB.append(",aSub:'true'");
			
			actSB.append("}");
			
			count ++;
			
		}
		
		actSB.append("]");
		
		String resultStr = actSB.toString();
		
		JYLoggerUtil.info(this.getClass(), "----------------sub pro info:"+resultStr);
		
		return resultStr;
	}
	@SuppressWarnings("all")
	public String getNodesOfProDefi(String proKey) throws Exception {
		
		if(!StringUtils.isNotEmpty(proKey)) return "[]";
		//获取该流程编码下 最新版本的流程定义
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("proKey", proKey);
		
		//Map resultMap = (Map) dao.queryObjectBySql("query_jbpm4_lob_info_by_pro_key", param );
		Map resultMap = mapper.queryJbpm4LobInfoByProkey(param );
		
		
		String deploymentId = (String) resultMap.get("DEPLOYMENT");
		
		String resourceName  = (String) resultMap.get("NAME");
		
		//开始组装json数据
		StringBuffer actSB = new StringBuffer("[");
		int count = 0;

		InputStream is = myJBPMTool.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder db=dbf.newDocumentBuilder();
        Document doc=db.parse(is);
		
		Element root = doc.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("task");
		//如果有 子流程的话 会进行该循环
		for(int i = 0;i < nodeList.getLength(); i ++){
			Element myElemnet = (Element)nodeList.item(i);
			String name = XmlUtil.attribute(myElemnet , "name");// sub-process-key
			  
			//通过流程定义id 及节点名称 获取该节点在流程定义中的位置信息 属性信息
			//开始组装json 数据
			if(count != 0) actSB.append(",");
				
			actSB.append("{destName:'").append(name).append("'");
			
			actSB.append("}");
			
			count ++;
			
		}
		
		//追加 end 节点
		NodeList nodeEndList = root.getElementsByTagName("end");
		for(int i = 0;i < nodeEndList.getLength(); i ++){
			Element myElemnet = (Element)nodeEndList.item(i);
			String name = XmlUtil.attribute(myElemnet , "name");// sub-process-key
			if(count != 0) actSB.append(",");
				
			actSB.append("{destName:'").append(name).append("'");
			
			actSB.append("}");
			
			count ++;
		}
		
		actSB.append("]");
		
		String resultStr = actSB.toString();
		
		JYLoggerUtil.info(this.getClass(), "----------------pro def info:"+resultStr);
		
		return resultStr;
		
	}
	/**
	 * 跨流程处理待办 以达到子流程之间的快速流转
	 * 1.将当前的子流程快速处理至 end节点
	 * 2.主流程中添加指向目标子流程的 路径
	 * 3.快速处理 目标子流程内的 task
	 */
	//@Transactional(rollbackFor=Exception.class)
	public String toOtherProTask(String taskId, String curSubProKey,
			String curDestName, String destSubProKey, String destSubProName,String destName,
			String processInstanceId, String mainProIns,
			Map<String, Object> variables) throws Exception {
		
		String toOtherSubProPath = "regect " +curSubProKey+" to "+ destSubProKey +" "+destName;
		
		String mainDestName = destSubProName;//"subPro";
		
		String str = myJBPMTool.singleSubProWay(toOtherSubProPath,mainDestName, mainProIns, taskId, curDestName, processInstanceId, variables);
		
		toOtherProTask2(destName,mainProIns,variables);
		
		return str;
	}
	/**
	 * 自动处理 子流程内的快速处理
	 * @param destName
	 * @param mainProIns
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	public String toOtherProTask2(String destName,String mainProIns,Map<String, Object> variables) throws Exception {
		String msg = "";
		//自动处理 子流程内的快速处理
		Map<String, Object> paramLast = new HashMap<String,Object>();
		paramLast.put("mainProInsId", mainProIns);
		//List lastTaskList = dao.queryListBySql("query_last_task_info_by_main_pro_ins_id", paramLast );
		List lastTaskList = mapper.queryLastTaskInfoByMainProInsId(paramLast );
		
		if(lastTaskList != null && lastTaskList.size() > 0){
			Map taskInfo = (Map) lastTaskList.get(0);
			String newTaskId = (String) taskInfo.get("DBID");
			String newProInsId = (String) taskInfo.get("EXECUTION_ID");
			
			//快速处理 目标子流程内的 task
			msg = myJBPMTool.singleBackWay(newTaskId, destName, newProInsId, variables);
		}
		return msg;
	}
	public byte[] fetchProPng(Map paraMap) {
		byte[] pngInfo = null;
		try {
			pngInfo = tempService.fetchProPng(paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pngInfo;
	}
	public void cancelPublishProcess(String deploymentId) throws Exception {
		modifyProService.cancelPublishProcess(deploymentId);
	}
	public String deployProcess(TemporaryJbpm4InfoDTO dto, String lastId)
			throws Exception {
		String deployId = modifyProService.deployProcess(dto, lastId);
		return deployId;
	}
	@Transactional(rollbackFor=Exception.class)
	public String updateJbpm4LobTableImmediately(Map<String, Object> paramMap)
			throws Exception {
		String deploymentId = modifyProService.updateJbpm4LobTableImmediately(paramMap);
		return deploymentId;
	}
	/**
	 * 将会签流程的实例id 转换 其对应的流程实例id
	 * 如 将：会签.90011.2.90025 转为会签.90011
	 * @param proInsId
	 * @return
	 */
	private String transProInsId(String proInsId){
		if(StringUtils.isNotEmpty(proInsId)	&& StringUtilTools.countStr(proInsId, ".") >1){
			//如果含有的.大于1说明其 是会签流程 格式如：会签.90011.2.90025, 其实际流程实例为 会签.90011
			String headStr = proInsId.substring(0, proInsId.indexOf("."));
			String temStr = proInsId.substring(proInsId.indexOf(".") +".".length());
			proInsId = headStr +"."+temStr.substring(0,temStr.indexOf("."));
		}
		return proInsId;
	}
/*
 * (non-Javadoc)
 * @see com.jy.platform.jbpm4.service.IJbpmService#obtainJbpm4FormByProInsId(java.lang.String)
 */
	public Map<String, Object> obtainJbpm4FormByProInsId(String executionId,String actName) {
		Map<String,Object> formMap = null;
		//通过流程实例获取流程定义 信息
		Map<String, Object> paMap = new HashMap<String,Object>();
		paMap.put("proInsId", executionId);
		Map<String,Object> defMap = mapper.queryProDefindInfo(paMap);
		if(defMap != null){
			String proDefKey = (String)defMap.get("PRO_KEY");
			String proName = (String) defMap.get("PRO_NAME");
			String proDefVersion = String.valueOf((BigDecimal) defMap.get("VERSION"));
			//获取表单 配置 及选人规则的配置信息
			formMap = taskService.obtainJbpm4FormInfo(proDefKey,proDefVersion,actName);
		}else{
			//说明该流程是子流程或是会签 结束
			formMap = null;
		}
		return formMap;
	}
	/*
	 * (non-Javadoc)
	 * @see com.jy.platform.jbpm4.service.IJbpmService#viewWorkflowHisLog(java.util.Map)
	 */
	public List<Map<String, Object>> viewWorkflowHisLog(
			Map<String, Object> searchParams) throws Exception {
		//List<Map<String, Object>> resultList = mapper.viewWorkflowHisLogByPaging(searchParams);
	    //包含task、custom、decision 类型的活动
		List<Map<String, Object>> resultList = mapper.viewActHisLogByPaging(searchParams);
		if(resultList != null && resultList.size() >0){
			//todo 多线程 控制 获取 人员 姓名 信息等
			resultList = this.makeupUserInfo(resultList);
			for(int i = resultList.size() -1 ; i >= 0 ;i --){
				Map<String,Object> tem = resultList.get(i);
				String userName1 = (String) tem.get("EXE_USER_NAME");
				StringBuilder sb = new StringBuilder();
				if( (i - 1) < resultList.size() && i >0 ){
					Map<String,Object> tem2 = resultList.get(i -1);
					String userName2 = (String) tem2.get("EXE_USER_NAME");
					if(null == userName1 && null == userName2){
					    continue;
					}
					sb.append(userName1==null ?"":userName1).append("-->").append(userName2==null?"":userName2);
				}
				tem.put("LOG_DESC", sb.toString());
			}
			
		}
		return resultList;
	}
	
	/**
	 * 根据流程定义key 获取活动定义列表
	 * 只返回 类型为 task和custom的活动定义
	 * @param proDefKey 流程定义key
	 * @return 例：[{actName:'风险管理部'},{actName:'门店经理'}]
	 */
	public String getActivityDefines(String proDefKey){
		//获取流程定义 
		ProcessDefinition processDefinition = myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey(proDefKey).uniqueResult();
		ProcessDefinitionImpl processDe =(ProcessDefinitionImpl)processDefinition;
		List<ActivityImpl> actDefList = (List<ActivityImpl>)processDe.getActivities();
		StringBuffer actDefStr = new StringBuffer();
		if(actDefList!=null && actDefList.size()>0){
			actDefStr.append("[");
			int count = 0;
			for(int i=0;i<actDefList.size();i++){
				if("task".equals(actDefList.get(i).getType()) || "custom".equals(actDefList.get(i).getType())){
					if(count != 0){
						actDefStr.append(",");
					}
					actDefStr.append("{actName:'").append(actDefList.get(i).getName()).append("'}");
					count++;
				}
				
			}
			actDefStr.append("]");
		}
		return actDefStr.toString();
	}
	
	/**
	 * 根据流程实例ID和活动名称获取活动信息
	 * @param proInstId
	 * @param actName
	 * @return 例：{performerId:'1',performerName:'admin',completeTime:'2014-12-10 18:31:40'}
	 */
	public String getActInstByProInstIdAndActName(String proInstId,String actName){
		String actInfoString = "";
		Map<String, String> param = new HashMap<String,String>();
		param.put("proInstId", proInstId);
		param.put("actName", actName);
		List<Map<String, Object>>  actInstList = mapper.getActInstByProInstIdAndActName(param);
		if(actInstList==null || actInstList.size()==0)
			return "{performerId:'',performerName:'',completeTime:''}";
		Map<String, Object> actInstInfoMap = actInstList.get(0);
		String performer = (String)actInstInfoMap.get("PERFORMER");
		String performerName = "";
		if(null!=performer && !"".equals(performer)){
			UserInfo curUser = orgApi.getUserInfoDetail(performer);
			performerName = curUser.getUserName();
		}else{
			performer="";
		}
		
		actInfoString = "{performerId:'"+performer+"',performerName:'"+performerName+"',completeTime:'"+actInstInfoMap.get("COMPLETETIME")+"'}";
		return actInfoString;
	}
	
	/**
     * 根据流程实例ID查询活动历史列表
     * @param proInstId 流程实例ID
     * @return
     */
    public List<Map<String, Object>> queryHisActInstsByProInstId(String proInstId){
        proInstId = this.transProInsId(proInstId);
        // 获取所有已经完成的节点：
        List<HistoryActivityInstance> hisActInstances = myJBPMTool.getHistoryService().createHistoryActivityInstanceQuery().processInstanceId(proInstId).list();
        //同一个活动可能来回执行几次 用来存储不重复的活动信息
        List<Map<String,Object>> tempHisActs = new ArrayList<Map<String,Object>>();
        if(null == hisActInstances){
            return tempHisActs;
        }
        //去掉重复的 多次完成的同一个活动信息
        for(HistoryActivityInstance hai : hisActInstances){
            HistoryActivityInstanceImpl haiImpl = (HistoryActivityInstanceImpl)hai;
            //通过活动的名称 来判断是否相同
            String activityName = haiImpl.getActivityName();
            //过滤掉类型不是 task和custom的节点
            if(!("task".equals(haiImpl.getType()) || "custom".equals(haiImpl.getType())))
                continue;
            boolean isExists = false;
            for(int k = 0;k < tempHisActs.size();k ++){
                Map<String,Object> tempHai = (Map<String,Object>) tempHisActs.get(k);
                String tempActName= (String)tempHai.get("actName");
                if(activityName.equals(tempActName)){
                    isExists = true;
                    break;
                }
            }
            //如果此活动在 tempHisActs 没有 则 add
            if(!isExists){
                Map<String,Object> actMap = new HashMap<String,Object>();
                actMap.put("actName", activityName);
                tempHisActs.add(actMap);
            }
        }
        return tempHisActs;
    }
    
    /**
     * 处理角色映射权限控制
     * 管理员角色(admin)不限制
     * @param dto
     * @throws Exception
     */
    private void dealRoleMapping(JbpmDTO dto) throws Exception{
    	StringBuffer roleMappingSql = new StringBuffer();
    	boolean isAdmin = false;
    	UserInfo userInfo = sessionAPI.getCurrentUserInfo();
    	String userId = userInfo.getUserId()+"";
    	//查询当前登录用户归属的角色列表
    	List<Map<String, Object>> roleList = sysRoleAPI.getRoleByUserId(userId);
    	if(null!=roleList && roleList.size()>0){
    		StringBuffer roles = new StringBuffer();
    		for(int i=0;i<roleList.size();i++){
    			Map<String, Object> roleInfo = roleList.get(i);
    			String roleCode = (String)roleInfo.get("roleCode");
    			//判断是否为管理员角色
    			if("admin".equalsIgnoreCase(roleCode)){
    				isAdmin = true;
    				break;
    			}
    			roles.append("'").append(roleCode).append("'");
    			if(roleList.size()>1 && i!=(roleList.size()-1)){
    				roles.append(",");
    			}
    		}
    		//非管理员角色 处理角色映射权限
    		if(!isAdmin){
    			Map<String,Object> searchParams = new HashMap<String,Object>();
    			Jbpm4RoleMappingDTO queryDTO = new Jbpm4RoleMappingDTO();
    			queryDTO.setRoleCodes(roles.toString());
    			searchParams.put("dto", queryDTO);
    			//查询映射角色列表
    			List<Jbpm4RoleMappingDTO> roleMappingList = roleMappingService.searchJbpm4RoleMapping(searchParams);
    			if(null!=roleMappingList && roleMappingList.size()>0){
    				StringBuffer mappingRoles = new StringBuffer();
    				//拼装映射角色
    				for(int i=0;i<roleMappingList.size();i++){
    					Jbpm4RoleMappingDTO roleMappingDTO = roleMappingList.get(i);
    					mappingRoles.append("'").append(roleMappingDTO.getMappingRoleCode()).append("'");
    					if(roleMappingList.size()>1 && i!=(roleMappingList.size()-1)){
    						mappingRoles.append(",");
    					}
    				}    				
    				
    				//待办
    				if("PROCESS_TASK".equals(dto.getProcessState())){
    					roleMappingSql.append(" and (assignee_ in")
    								  .append(" (select to_char(ru.target_id) from sys_role_user ru, sys_role r ")
    								  .append(" where ru.role_id = r.id ")
    								  .append(" and r.role_code in (").append(mappingRoles.toString()).append("))")
    								  .append(" or assignee_ in (select g.role_group_code from sys_role_group g, sys_role r, sys_role_group_role s where g.id = s.role_group_id and r.id = s.role_id  and s.validate_state='1'  and g.validate_state='1' and r.validate_state='1' and r.role_code in (")
    								  .append(mappingRoles.toString()).append(" )  ) ")
    								  .append("  or  assignee_ in (").append(mappingRoles.toString()).append(" )")
    								  .append("  or  assignee_ ='").append(userId).append("'")
    								  .append(")");
    				}else if("COMPLETED_TASK".equals(dto.getProcessState())){
    					roleMappingSql.append(" and (assignee_ in ( select to_char(ru.target_id) from sys_role_user ru, sys_role r where ru.role_id = r.id ")
						  			  .append("  and r.role_code in (").append(mappingRoles.toString()).append("))")
						  			  .append(" or assignee_='").append(userId).append("')");
    				}else if("END_TASK".equals(dto.getProcessState())){
    					roleMappingSql.append(" and exists ( select to_char(ru.target_id) from sys_role_user ru, sys_role r where ru.role_id = r.id ")
    					.append("  and exists (select ht.execution_ from jbpm4_hist_task ht where ht.assignee_ = to_char(ru.target_id) and ht.execution_ = t1.pro_instance_id) and r.role_code in (")
    					.append(mappingRoles.toString()).append(" ) )") ;
    					
    				}else if("END_HIST_TASK".equals(dto.getProcessState())){//by xyz
    					roleMappingSql.append(" and exists ( select to_char(ru.target_id) from sys_role_user ru, sys_role r where ru.role_id = r.id ")
    					.append("  and exists (select ht.execution_ from jbpm4_hist_task_old ht where ht.assignee_ = to_char(ru.target_id) and ht.execution_ = t1.pro_instance_id) and r.role_code in (")
    					.append(mappingRoles.toString()).append(" ) )") ;
    					
    				}
    			}else{
    				roleMappingSql.append(" and 1=0 ");
    			}
    		}
    	}else{//没有角色就没有权限查看
    		roleMappingSql.append(" and 1=0 ");
    	}
    	if(roleMappingSql.length()>0){
    		dto.setRoleMappingSql(roleMappingSql.toString());
    	}
    }
    
	/**
	 * 判断userId 是否请假中
	 * @param userId
	 * @return
	 */
	public boolean  checkLeaveByUserId(String userId){
		boolean leave = false;//请假中
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		
		try {
			Map<String,String> resuMap = mapper.checkLeaveByUserId(paramMap);
			String cun = resuMap.get("CUN");
			if(Integer.parseInt(cun) > 0){
				leave = true;
			}
		} catch (Exception e) {
			JYLoggerUtil.error(this.getClass(), "执行方法checkLeaveByUserId 失败："+e.getMessage(),e);
			leave = false;
		}
		return leave;
	}
	
	/**
	 * 分页查询已结历史数据  by xyz
	 */
	public List<Map<String,Object>> queryEndHistTaskListByPaging(Map<String, Object> params) throws Exception {
		if(params != null){
			JbpmDTO dto  = (JbpmDTO) params.get("dto");
			if(dto != null  && "1".equals(dto.getIsMonitor())){
				dealRoleMapping(dto);
			}
			String processParticipationName = dto.getProcessParticipationName();
			//如果不为空
			if(StringUtils.isNotEmpty(processParticipationName)){
				StringBuilder sb = new StringBuilder();
				String[] parts = processParticipationName.split(",");
				for(String par: parts){
					if(sb.length() >0)
					sb.append(",");
					sb.append("'").append(par).append("'");
				}
				dto.setProcessParticipationName(sb.toString());
			}
			params.put("dto", dto);
		}
		List<Map<String,Object>> dataList = mapper.queryPersonalEndHistTaskInfoByPaging(params);
		
		dataList = makeupPersonalEndHistTaskInfo(dataList);
		dataList = makeupUserInfo(dataList);
		return dataList;
	}
	/**
	 * 处理已结历史任务信息 by xyz
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	private List<Map<String, Object>> makeupPersonalEndHistTaskInfo(List<Map<String, Object>> dataList) throws Exception {
		if(dataList != null && dataList.size() >0){
				int count = dataList.size() ;
				//定义个数
				final CountDownLatch countDownLatch = new CountDownLatch(count);
				for(int i = 0;i < count;i ++){
					final Map temp = dataList.get(i);
					
					String proInsId = (String) temp.get("PRO_INSTANCE_ID");
					final Map<String,Object> tempParam = new HashMap<String,Object>();
					tempParam.put("proInsId", proInsId);
					
					executor.execute(new Runnable(){
						private String transProInsId(String proInsId){
							if(StringUtils.isNotEmpty(proInsId)	&& StringUtilTools.countStr(proInsId, ".") >1){
								//如果含有的.大于1说明其 是会签流程 格式如：会签.90011.2.90025, 其实际流程实例为 会签.90011
								String headStr = proInsId.substring(0, proInsId.indexOf("."));
								String temStr = proInsId.substring(proInsId.indexOf(".") +".".length());
								proInsId = headStr +"."+temStr.substring(0,temStr.indexOf("."));
							}
							return proInsId;
						}
						public void run() {
							try {
								String proInsId  = (String) tempParam.get("proInsId");
								//通过流程实例获取流程定义 信息
								proInsId = this.transProInsId(proInsId);
								Map<String, Object> paMap = new HashMap<String,Object>();
								paMap.put("proInsId", proInsId);
								Map<String,Object> defMap = mapper.queryProDefindInfoByHistData(paMap);
								
								if(defMap != null){
									String proName = (String) defMap.get("PRO_NAME");
									String proDefVersion = String.valueOf((BigDecimal) defMap.get("VERSION"));
									temp.put("VERSION", proDefVersion);
									temp.put("NAME", proName);
								}
							} catch (Exception e) {
								JYLoggerUtil.error(this.getClass(), e.getMessage());
							}
							
							countDownLatch.countDown();
						}
						
					});
				}
				//等待子线程都执行完了再执行主线程剩下的动作
				countDownLatch.await();
			
		}
		
		return dataList;
	}
	/**
	 * 查询历史数据 轨迹数据 by xyz
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> viewWorkflowHisLogForHistData(Map<String, Object> searchParams) throws Exception {
		
		List<Map<String, Object>> resultList = mapper.viewActHisLogForHistByPaging(searchParams);
		if(resultList != null && resultList.size() >0){
			//todo 多线程 控制 获取 人员 姓名 信息等
			resultList = this.makeupUserInfo(resultList);
			for(int i = resultList.size() -1 ; i >= 0 ;i --){
				Map<String,Object> tem = resultList.get(i);
				String userName1 = (String) tem.get("EXE_USER_NAME");
				StringBuilder sb = new StringBuilder();
				if( (i - 1) < resultList.size() && i >0 ){
					Map<String,Object> tem2 = resultList.get(i -1);
					String userName2 = (String) tem2.get("EXE_USER_NAME");
					if(null == userName1 && null == userName2){
					    continue;
					}
					sb.append(userName1==null ?"":userName1).append("-->").append(userName2==null?"":userName2);
				}
				tem.put("LOG_DESC", sb.toString());
			}
			
		}
		return resultList;
	}
	/**
	 * 获取图片 by  xyz
	 */
	public byte[] fetchProPngByHist(Map paraMap) {
		byte[] pngInfo = null;
		try {
			pngInfo = tempService.fetchProPngByHist(paraMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pngInfo;
	}
	/**
	 * 查询节点信息  by xyz
	 */
	public String getAllActiveProcNodeNameByHist(String mainProcessInstanceId,
			String subProcessInstanceId) {
		StringBuffer actSB = new StringBuffer("[");
		actSB.append("]");
		String resultStr = actSB.toString();
		JYLoggerUtil.info(this.getClass(), resultStr);
		
		return resultStr;
	}
	/**
	 * 查询任务数据
	 */
	@Override
	public List<TaskInfo> queryNextTaskInfoList(String processInsId, String isAll) {
		List<String>  list=myJBPMTool.getAllTaskName(processInsId);
		if(!"true".equals(isAll)){
			List<String>  listHist= myJBPMTool.getHistTaskName(processInsId);
			list.removeAll(listHist);
		}
		List<TaskInfo> listTask=new ArrayList<TaskInfo>();
		int i=1;
		for(String str:list){
			listTask.add(new TaskInfo(""+i++, str));
		}
		return listTask;
	}
	/**
	 * 查询任务数据
	 * @throws Exception 
	 */
	@Override
	public List<TaskInfo> queryNextTaskInfoList(String processInsId, String isAll,String myTurn,String acitityName) throws Exception {
		List<String>  list=myJBPMTool.getAllTaskName(processInsId);
		String prodDefId=myJBPMTool.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(processInsId).uniqueResult().getProcessDefinitionId();
		ProcessDefinition processDefinition=myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(prodDefId).uniqueResult();
		
		String id=formService.queryJbpm4FormInfoIdBysql(processDefinition.getKey(), Integer.toString(processDefinition.getVersion()), acitityName);
		Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(id));
		Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO modifyDto = formService.queryOneJbpm4FormInfo(paramMap);
		if(!"true".equals(isAll)&&ConstantJBPM.JUMP_OUT_NAME.equals(myTurn)){
			if(modifyDto.getExt2()!=null&&!modifyDto.getExt2().trim().equals("")){
				String[] names=modifyDto.getExt2().split(",");
				list=Arrays.asList(names);
			}else{
				List<String>  listHist= myJBPMTool.getHistTaskName(processInsId);
				list.removeAll(listHist);
			}
		}
		if(ConstantJBPM.BACK_OUT_NAME.equals(myTurn)){
			if(modifyDto.getExt1()!=null&&!modifyDto.getExt1().trim().equals("")){
				String[] names=modifyDto.getExt1().split(",");
				list=Arrays.asList(names);
			}else{
				list= myJBPMTool.getHistTaskName(processInsId);
			}
		}
		Collections.sort(list);
		List<TaskInfo> listTask=new ArrayList<TaskInfo>();
		int i=1;
		for(String str:list){
			listTask.add(new TaskInfo(""+i++, str));
		}
		return listTask;
	}
	/**
	 * 获取任务选择框 状态
	 * @param processInsId
	 * @param myTurn
	 * @param acitityName
	 * @return
	 * @throws Exception 
	 */
	public boolean isShowTaskInfo(String processInsId, String myTurn, String acitityName) throws Exception{
	    logger.debug("===1=====isShowTaskInfo processInsId:{},myTurn:{},acitityName:{}",processInsId,myTurn,acitityName);
		String prodDefId=myJBPMTool.getHistoryService().createHistoryProcessInstanceQuery().processInstanceId(processInsId).uniqueResult().getProcessDefinitionId();
		
		logger.debug("===2=====isShowTaskInfo processInsId:{},myTurn:{},acitityName:{}",processInsId,myTurn,acitityName);
		ProcessDefinition processDefinition=myJBPMTool.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(prodDefId).uniqueResult();
		
		logger.debug("===3=====isShowTaskInfo processInsId:{},myTurn:{},acitityName:{}",processInsId,myTurn,acitityName);
		String id=formService.queryJbpm4FormInfoIdBysql(processDefinition.getKey(), Integer.toString(processDefinition.getVersion()), acitityName);
		logger.debug("===4=====isShowTaskInfo processInsId:{},myTurn:{},acitityName:{}",processInsId,myTurn,acitityName);
		Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(id));
		Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO modifyDto = formService.queryOneJbpm4FormInfo(paramMap);
    	
    	logger.debug("===5=====isShowTaskInfo processInsId:{},myTurn:{},acitityName:{}",processInsId,myTurn,acitityName);
    	if(ConstantJBPM.BACK_OUT_NAME.equals(myTurn)&&(ConstantJBPM.SHOW_BACK.equals(modifyDto.getExt3())||ConstantJBPM.SHOW_ALL.equals(modifyDto.getExt3()))){
			return true;
		}else if(ConstantJBPM.JUMP_OUT_NAME.equals(myTurn)&&(ConstantJBPM.SHOW_JUMP.equals(modifyDto.getExt3())||ConstantJBPM.SHOW_ALL.equals(modifyDto.getExt3()))){
			return true;
		}
		return false;
	}
}

