package com.jy.modules.jbpm4.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jbpm.api.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.api.sysauth.SysRoleAPI;
import com.jy.platform.core.common.JYLoggerUtil;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.jbpm4.dto.JbpmDTO;
import com.jy.platform.jbpm4.dto.TaskInfo;
import com.jy.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.jy.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.jy.platform.jbpm4.service.IJbpmService;
import com.jy.platform.jbpm4.service.JbpmTastService;
import com.jy.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
import com.jy.platform.jbpm4.temporaryJbpm4Info.service.ITemporaryJbpm4InfoService;
import com.jy.platform.jbpm4.tool.StringUtilTools;
import com.jy.platform.restservice.web.base.BaseController;
/**
 * 
 * @Description: 前台web页面查询 关于工作流的 proiver 此controller 仅作查询
 * @author chen_gang
 * @date 2014年9月5日 上午11:05:20
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping("/workFlowProvider")
public class BestWorkFlowProviderController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(BestWorkFlowProviderController.class);
	//获取 访问业务层的对象
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.JbpmServiceImpl")
	private IJbpmService jbpmServcie;
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.service.impl.JbpmTaskServceImpl")
	private JbpmTastService taskServcie;
	
	//获取 访问业务层的对象
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.temporaryJbpm4Info.service.impl.TemporaryJbpm4InfoServiceImpl")
	private ITemporaryJbpm4InfoService tempService;
	@Autowired
	@Qualifier("com.jy.platform.jbpm4.jbpm4FormInfo.service.impl.Jbpm4FormInfoServiceImpl")
	private IJbpm4FormInfoService	formService;
	
	@Autowired
	private SysRoleAPI sysRoleAPI;
	
    @Autowired
    private SessionAPI sessionAPI;
    
	/**
	 * 查询 待办任务，活动的流程实例 、结束的流程实例、已经完成的任务、流程定义信息
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
    @RequestMapping(value = "/findTaskInfo.do")
    @ResponseBody
    @SuppressWarnings("all")
    public DataMsg findWorkFlowInfo(JbpmDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
    	logger.info("====workFlowProvider/findTaskInfo.do=="+dto.getProcessState());
        if (dataMsg == null) dataMsg = new DataMsg();
 		if("PROCESS_TASK".equals(dto.getProcessState())){//获取  待办任务
 			dataMsg = this.queryTaskByMySql(dto,dataMsg);
 		}else if("ACTIVE_PROCESS_INFO".equals(dto.getProcessState())){// 活动的流程 信息
 			dataMsg = this.queryActiveProcessInfoByMySql(dto,dataMsg);
 		}else if("END_PROCESS_INFO".equals(dto.getProcessState())){// 结束的流程 信息
 			dataMsg = this.queryEndProcessInfoByMySql(dto,dataMsg);
 		}else if("COMPLETED_TASK".equals(dto.getProcessState())){//获得所有 已经 完成的任务
 			dataMsg = this.queryCompletedTaskByMySql(dto,dataMsg);
 		}else if("END_TASK".equals(dto.getProcessState())){//已办结的任务 查询
 			dataMsg = this.queryEndTaskInfo(dto,dataMsg);
 		}else if("PROCESS_DEFINE_INFO".equals(dto.getProcessState())){//查询流程定义信息
 			dataMsg = this.queryProcessDefineInfo(dto,dataMsg);
 		}else if("END_HIST_TASK".equals(dto.getProcessState())){//已办结的历史任务 查询
 			dataMsg = this.queryEndHistTaskInfo(dto,dataMsg);
 		}
 		/*List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
 		Map<String,Object> temp = new HashMap<String,Object>();
 		temp.put("NODE", "=");
 		data.add(temp);
 		dataMsg.setData(data);*/
 		logger.info("====workFlowProvider/findTaskInfo.do=dataMsg="+dataMsg.getData());
        return dataMsg;
    }
    @RequestMapping(value = "/findTemporaryJbpm4InfoByPage.do")
    @ResponseBody
    @SuppressWarnings("all")
    public DataMsg findTemporaryJbpm4InfoPage(TemporaryJbpm4InfoDTO tempDto, @ModelAttribute DataMsg pageData) {
        if (pageData == null) pageData = new DataMsg();
        int totalRows = 0;
        Object data = null;
        List<TemporaryJbpm4InfoDTO> resultList = this.queryListOfTemporaryJbpm4InfoPage(tempDto,pageData);
		totalRows = StringUtilTools.getTotalCountOfListByPage(resultList);
		data = resultList;
        pageData.setData(data);
        pageData.setTotalRows(totalRows);
        return pageData;
    }
    /**
     * 查看流程 执行 轨迹 信息
     * @param dataMsg
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/viewWorkflowHisLog.do")
    @ResponseBody
    public DataMsg viewWorkflowHisLog(@ModelAttribute DataMsg dataMsg) throws Exception {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        
        String processInstantceId = this.getParameterString("processInstantceId");//主流程实例 id
        String bizInfId = this.getParameterString("bizInfId");//jbpm4_biz_tab 业务主键ID
        String bizTabName = this.getParameterString("bizTabName");//jbpm4_biz_tab 业务表名称
        String type = this.getParameterString("type");
        
        searchParams.put("processInstantceId", processInstantceId);
        searchParams.put("bizInfId", bizInfId);
        searchParams.put("bizTabName", bizTabName);
        
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if("hist".equals(type)){//归档数据查询
			list = jbpmServcie.viewWorkflowHisLogForHistData(params.getSearchParams());
		}else{
			list = jbpmServcie.viewWorkflowHisLog(params.getSearchParams());
		}
		if(list != null && list.size() >0){
		    for(int i =0;i < list.size();i ++){
		        Map<String,Object> maps = list.get(i);
		        String tempOut = org.springframework.util.StringUtils.isEmpty(maps.get("OUTCOME"))?"":maps.get("OUTCOME").toString().replace("jump", "").replace("from", "").replace("to", "-->");
                maps.put("OUTCOME", tempOut);
		    }
        }
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    /**
     * 通过 主键id 获取 该流程定义的 xml文件内容
     * @param requ
     * @param response
     * @return xml string
     * @throws Exception
     */
    @RequestMapping(value = "/getOneProcessXMLContent.do")
    @ResponseBody
    @SuppressWarnings("all")
    public DataMsg getOneProcessXMLContent(HttpServletRequest requ, HttpServletResponse response) throws Exception {
    	String id = requ.getParameter("keyId");
        String resultMsg = tempService.getOneProcessXMLContent(id);
        TemporaryJbpm4InfoDTO tempDto = tempService.getOneTemporaryJbpm4InfoDTO(id);
        String bizFile = tempDto.getBizFile();
        //resultMsg =resultMsg+"";
        //this.outWriteString(response, );
        response.setContentType("text/Xml;charset=utf-8");
			Writer out = response.getWriter();
		   //写数据至页面
        out.write(bizFile+"龍"+resultMsg);
        out.flush();
        out.close();
        return null;
    }
	/**
     * 查看流程图片
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/viewPhone.do")
	@SuppressWarnings("all")
	public ModelAndView viewPhone(HttpServletRequest requ, HttpServletResponse response) throws Exception{
		String processName = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processName"));
		String processVersion = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processVersion"));
		String processInstantceId = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processInstantceId"));
		//增加 可以通过 流程定义key 获取流程图片信息 
		String proDefKey = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processDefKey"));
		 String type = this.getParameterString("type");
		try {
            ServletOutputStream outStream = response.getOutputStream();// 得到向客户端输出二进制数据的对象
            response.setContentType("image/*"); // 设置返回的文件类型
            Map paraMap = new HashMap();
            paraMap.put("processName", processName);
            paraMap.put("processVersion", processVersion);
            paraMap.put("processInstantceId", processInstantceId);
            paraMap.put("processDefKey", proDefKey);
            
            //paraMap.put("id", "693");
            byte[] pngInfo=null;
            if("hist".equals(type)){
            	pngInfo= jbpmServcie.fetchProPngByHist(paraMap);
            }else{
            	pngInfo= jbpmServcie.fetchProPng(paraMap);
            }
            
            // 读数据
            outStream.write(pngInfo);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
    
    /**
     * 获取流程定义的 节点信息
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getProcNodeName.do")
   	@SuppressWarnings("all")
   	public ModelAndView getProcNodeName(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	String processDefinitionId = requ.getParameter("processDefinitionId");//StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"mainProcessInstanceId"));
    	String resultMsg = jbpmServcie.getProcNodeName(processDefinitionId);
        this.outWriteString(response, resultMsg);
   		return null;
   	}
    
    /**
     * 获取 流程定义下某个节点的 流程走向 信息
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getTurenDirectory.do")
   	@SuppressWarnings("all")
   	public ModelAndView getTurenDirectory(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//流程定义 id
    	String proDefId = requ.getParameter("processDefinitionId");//StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"mainProcessInstanceId"));
    	//节点名称
        String activeName = requ.getParameter("activeName");
    	String resultMsg = jbpmServcie.getTurenDirectory(proDefId, activeName);
        this.outWriteString(response, resultMsg);
   		return null;
   	}
    /**
     * 获取 某个流程实例节点的流程 回退节点 
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getIncomingTransitions.do")
   	@SuppressWarnings("all")
   	public ModelAndView getIncomingTransitions(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//流程实例 id
    	String proInstanceId = requ.getParameter("proInstanceId");
    	//节点名称
        String activeName = requ.getParameter("activeName");
    	String resultMsg = jbpmServcie.getIncomingTransitions(proInstanceId, activeName);
        this.outWriteString(response, resultMsg);
   		return null;
   	}
    /**
     * 通过 流程实例id ，活动节点名称 获取该节点下所有的流程走向
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllTurenDirectory.do")
   	public ModelAndView getAllTurenDirectory(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//流程定义 id
    	String proInsId = requ.getParameter("processInsId");
    	//节点名称
        String activeName = requ.getParameter("acitityName");
        String isJump = requ.getParameter("isJump");
        String isBack = requ.getParameter("isBack");
    	String resultMsg = jbpmServcie.getAllTurenDirectory(proInsId, activeName,isJump,isBack);
        this.outWriteString(response, resultMsg);
   		return null;
   	}
   /**
    * 准备 修改 节点 表单 配置的数据信息
    * @param requ
    * @param response
    * @return
    * @throws Exception
    */
    @RequestMapping(value="/prepareModifyFormInfo.do")
   	@SuppressWarnings("all")
   	public ModelAndView prepareModifyFormInfo(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//工作流不 使用InternalResourceViewResolver 配置
    	ModelAndView model = new ModelAndView("forward:/component/jbpm/jbpm4FormInfo/updateJbpm4FormInfo.jsp");
    	String id  = requ.getParameter("dto.id");
    	Jbpm4FormInfoDTO dto = new Jbpm4FormInfoDTO();
    	dto.setId(Long.parseLong(id));
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	paramMap.put("dto", dto);
    	Jbpm4FormInfoDTO modifyDto = formService.queryOneJbpm4FormInfo(paramMap);
    	model.addObject("modifyDto",modifyDto);
   		return model;
   	}
    /**
     * 判断 该节点是否 增加了表单 配置信息
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/queryJbpm4FormInfoId.do")
   	@SuppressWarnings("all")
   	public ModelAndView queryJbpm4FormInfoId(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	String proKey = requ.getParameter("proKey");
    	String proVersion = requ.getParameter("proVersion");
    	String aName = requ.getParameter("aName");
		response.setContentType("text/Xml;charset=utf-8");
		Writer out = response.getWriter();
       
        String resultMsg = formService.queryJbpm4FormInfoIdBysql(proKey, proVersion, aName);
        if(resultMsg == null)  resultMsg = "";
        this.outWriteString(response, resultMsg);
   		return null;
   	}
    /**
     * 查询 流程实例 活动着 的节点信息
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllActiveProcNodeName.do")
   	@SuppressWarnings("all")
   	public ModelAndView getAllActiveProcNodeName(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	String mainProcessInstanceId = requ.getParameter("mainProcessInstanceId");//StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"mainProcessInstanceId"));
   		String subProcessInstanceId = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"subProcessInstanceId"));
   		String type = requ.getParameter("type");
   		String resultMsg = null;
   		if("hist".equals(type)){
   		 resultMsg = jbpmServcie.getAllActiveProcNodeNameByHist(mainProcessInstanceId, subProcessInstanceId);
   		}else{
   		 resultMsg = jbpmServcie.getAllActiveProcNodeName(mainProcessInstanceId, subProcessInstanceId);
   		}
       
        this.outWriteString(response, resultMsg);
   		return null;
   	}
    
    /**
     * 获取 主流下 所有子流程 节点信息 
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getSubProListInfo.do")
   	@SuppressWarnings("all")
    public ModelAndView getSubProListInfo(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	// 程实例id
    	String mainProId = requ.getParameter("processInstantceId");
    	//是否是主流程
   		String isInstance = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"isInstance"));
   		boolean isInstancFlag = true;
   		
   		if("true".equalsIgnoreCase(isInstance)){
   			isInstancFlag = true;
   		}else{
   			isInstancFlag = false;
   		}
        String resultMsg = jbpmServcie.getSubProListInfo(mainProId, isInstancFlag);
        this.outWriteString(response, resultMsg);
   		return null;
    }
   /**
    * 通过 流程实例ID 获取 待办任务信息
    * @param requ
    * @param response
    * @return
    * @throws Exception
    */
    @RequestMapping(value="/getTaskInfoByProInsId.do")
   	public ModelAndView getTaskInfoByProInsId(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	//流程实例 id
    	String proInstanceId = requ.getParameter("proInsId");
    	Map<String,String> taskInfo = taskServcie.getTaskInfoByProInsId(proInstanceId); 
    	String lockState = taskInfo.get("LOCK_STATE");//1：锁定状态 不能回退 收回 ，0：解锁状态 
    	String lastTaskId = taskInfo.get("TASK_ID");//当前流程实例ID下的　待办任务ID
    	StringBuilder resultMsg = new StringBuilder();
    	resultMsg.append("[{");
    	resultMsg.append("lockState:'").append(lockState).append("'");
    	resultMsg.append(",lastTaskId:'").append(lastTaskId).append("'");
    	resultMsg.append("}]");
        this.outWriteString(response, resultMsg.toString());
   		return null;
   	}
    /**
	 * 查询流程定义信息
	 * @param index
	 * @param count
	 * @return
	 */
	private DataMsg queryProcessDefineInfo(JbpmDTO dto, DataMsg dataMsg) {
		List<Map<String,Object>> myResultList = new ArrayList<Map<String,Object>>();
		try {
			List<ProcessDefinition> resultList = jbpmServcie.getAllPdList(dto,dataMsg);
			if(resultList != null && resultList.size() >0){
				for(ProcessDefinition pd :resultList){
					String deploymentId = pd.getDeploymentId();
					int version = pd.getVersion();
					String description = pd.getDescription();
					String name = pd.getName();
					String id = pd.getId();
					String key = pd.getKey();
					
					Map<String,Object> teMap = new HashMap<String,Object>();
					teMap.put("deploymentId", deploymentId);
					teMap.put("version", version);
					teMap.put("description", description);
					teMap.put("name", name);
					teMap.put("id", id);
					teMap.put("key", key);
					myResultList.add(teMap);
				}
			}
			
		} catch (Exception e) {
			JYLoggerUtil.error(this.getClass(), e.getMessage());
		}
		int totalRows = dataMsg.getTotalRows();
		dataMsg.setData(myResultList);
		dataMsg.setTotalRows(totalRows);
		return dataMsg;
	}

	/**
	 * 查询 已经办结的 历史任务信息
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private DataMsg queryEndTaskInfo(JbpmDTO dto, DataMsg dataMsg) throws Exception {
		//使用分页查询
		Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean paramsPT = new QueryReqBean();
        paramsPT.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
    	paramsPT.setPageParameter(pageInfo);
    	
    	List dataList  = jbpmServcie.queryEndTaskListByPaging(paramsPT.getSearchParams());
    	
		dataMsg.setData(dataList);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}
	/**
	 * 查询 已经结束的流程 信息
	 * QUERY_END_PRO_INFO_BY_MY_SQL
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private DataMsg queryEndProcessInfoByMySql(JbpmDTO dto, DataMsg dataMsg) throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean paramsPT = new QueryReqBean();
        paramsPT.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
    	paramsPT.setPageParameter(pageInfo);
    	
		List<Map<String,Object>> resultList = jbpmServcie.queryEndProInfoListByPaging(paramsPT.getSearchParams());
		dataMsg.setData(resultList);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * 通过 自定义 sql 查询 所有  流程信息
	 * @param index
	 * @param count
	 * @return
	 * @throws Exception 
	 */
	private DataMsg queryActiveProcessInfoByMySql(JbpmDTO dto, DataMsg dataMsg) throws Exception {
		//使用分页查询
		dataMsg.setStartIndex((dataMsg.getPageIndex() - 1) * dataMsg.getPageSize());
		dataMsg.setEndIndex(dataMsg.getPageIndex() * dataMsg.getPageSize());
			    
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", dataMsg);
		params.put("dto", dto);
		List resultList = jbpmServcie.queryActiveProcessInfoList(params);
		
		int totalRows = StringUtilTools.getTotalCountOfListByPage(resultList);
		dataMsg.setData(resultList);
		dataMsg.setTotalRows(totalRows);
		return dataMsg;
	}
	/**
	 * 通过 自定义 sql 查询 所有  已经完成的任务
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private DataMsg queryCompletedTaskByMySql(JbpmDTO dto, DataMsg dataMsg) throws Exception {
		//使用分页查询
		Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean paramsPT = new QueryReqBean();
        paramsPT.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
    	paramsPT.setPageParameter(pageInfo);
    	
    	List dataList  = jbpmServcie.queryCompletedTaskByPaging(paramsPT.getSearchParams());
    	
		dataMsg.setData(dataList);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}
	/**
	 * 通过 自定义 sql 查询 所有待办任务
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private DataMsg queryTaskByMySql(JbpmDTO dto, DataMsg dataMsg) throws Exception {
		//查询当前登录用户及归属角色组的任务
		queryByUserIdAndRoleGroup(dto);
		String assignee = dto.getProcessParticipationName();
	    //使用页面排序
	    if(null!=dataMsg.getSortName() && !"".equals(dataMsg.getSortName())){
	    	dto.setSortName(dataMsg.getSortName());	    	
	    }
	    if(null!=dataMsg.getSortType() && !"".equals(dataMsg.getSortType())){
	    	dto.setSortType(dataMsg.getSortType());
	    }
	    //dto.setCustomSQL("Y");
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean paramsPT = new QueryReqBean();
        paramsPT.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
    	paramsPT.setPageParameter(pageInfo);
    	
    	List dataList  = jbpmServcie.queryAllTaskByPaging(paramsPT.getSearchParams());
    	
		dataMsg.setData(dataList);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}
	
	/**
	 * 查询当前登录用户及归属角色组的任务
	 * @param dto
	 */
	private void queryByUserIdAndRoleGroup(JbpmDTO dto){
		//如果是管理监控调用，不做处理
		if(null==dto.getParamUserId() || "".equals(dto.getParamUserId())){
			return;
		}
		UserInfo userInfo = sessionAPI.getCurrentUserInfo();
    	String userId = userInfo.getUserId()+"";
    	List<Map<String, Object>> roleGroupList = sysRoleAPI.getRoleGroupByUserId(userId);
    	StringBuffer userIdsBuf = new StringBuffer();
        userIdsBuf.append("(");
    	for(int i=0;i<roleGroupList.size();i++){
    		Map<String, Object> roleGroup = roleGroupList.get(i);
    		String roleGroupCode = (String)roleGroup.get("roleGroupCode");
    		userIdsBuf.append("'").append(roleGroupCode).append("',");
    	}
    	userIdsBuf.append("'").append(userId).append("')");
    	dto.setParamUserIds(userIdsBuf.toString());
    	dto.setParamUserId("");
	}

	 /**
     * 写 字符串 至前台页面
     * @param response
     * @param resultMsg
     * @throws IOException 
     */
    private void outWriteString( HttpServletResponse response,String resultMsg) throws IOException{
   			response.setContentType("text/html;charset=utf-8");
   			Writer out = response.getWriter();
   		   //写数据至页面
           out.write(resultMsg);
           out.flush();
           out.close();
    }

    private List<TemporaryJbpm4InfoDTO> queryListOfTemporaryJbpm4InfoPage(TemporaryJbpm4InfoDTO tempDto, DataMsg page) {
    	//使用分页查询
    	page.setStartIndex((page.getPageIndex() - 1) * page.getPageSize());
    	page.setEndIndex(page.getPageIndex() * page.getPageSize());
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("dto", tempDto);
		List<TemporaryJbpm4InfoDTO> dataList = null;
		try {
			dataList = tempService.queryListOfTemporaryJbpm4InfoByPage(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
    /**
	 * 已结历史任务查询  by xyz
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	private DataMsg queryEndHistTaskInfo(JbpmDTO dto, DataMsg dataMsg) throws Exception {
		//使用分页查询
		Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean paramsPT = new QueryReqBean();
        paramsPT.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
    	paramsPT.setPageParameter(pageInfo);
    	
    	List dataList  = jbpmServcie.queryEndHistTaskListByPaging(paramsPT.getSearchParams());
		dataMsg.setData(dataList);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}
	
    /**
     * 查询历史数据 流程轨迹 by xyz
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/viewWorkflowHisLogForHistData.do")
    @ResponseBody
    public DataMsg viewWorkflowHisLogForHistData(@ModelAttribute DataMsg dataMsg) throws Exception {
        Map<String, Object> searchParams = new HashMap<String, Object>();
        
        String processInstantceId = this.getParameterString("processInstantceId");//主流程实例 id
        String bizInfId = this.getParameterString("bizInfId");// 业务主键ID
        String bizTabName = this.getParameterString("bizTabName");// 业务表名称
        
        searchParams.put("processInstantceId", processInstantceId);
        searchParams.put("bizInfId", bizInfId);
        searchParams.put("bizTabName", bizTabName);
        
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<Map<String,Object>> list = jbpmServcie.viewWorkflowHisLogForHistData(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    /**
     * 查询图片 byxyz
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/viewPhoneByHist.do")
	@SuppressWarnings("all")
	public ModelAndView viewPhoneByHist(HttpServletRequest requ, HttpServletResponse response) throws Exception{
		String processName = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processName"));
		String processVersion = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processVersion"));
		String processInstantceId = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processInstantceId"));
		//增加 可以通过 流程定义key 获取流程图片信息 
		String proDefKey = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"processDefKey"));
		try {
            ServletOutputStream outStream = response.getOutputStream();// 得到二进制数据liu的对象
            response.setContentType("image/*"); 
            Map paraMap = new HashMap();
            paraMap.put("processName", processName);
            paraMap.put("processVersion", processVersion);
            paraMap.put("processInstantceId", processInstantceId);
            paraMap.put("processDefKey", proDefKey);
            
            byte[] pngInfo = jbpmServcie.fetchProPngByHist(paraMap);
         
            outStream.write(pngInfo);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;
	}
    /**
     * 查询节点信息 by xyz
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/getAllActiveProcNodeNameByHist.do")
   	@SuppressWarnings("all")
   	public ModelAndView getAllActiveProcNodeNameByHist(HttpServletRequest requ, HttpServletResponse response) throws Exception{
    	String mainProcessInstanceId = requ.getParameter("mainProcessInstanceId");
   		String subProcessInstanceId = StringUtilTools.repMyStrToSpecial(StringUtilTools.filterSpecial(requ,"subProcessInstanceId"));
           
        String resultMsg = jbpmServcie.getAllActiveProcNodeNameByHist(mainProcessInstanceId, subProcessInstanceId);
        this.outWriteString(response, resultMsg);
   		return null;
   	}
    /**
     * 查询任务数据
     * @param requ
     * @param response
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/queryNextTaskInfo.do")
    @ResponseBody
    public DataMsg queryNextTaskInfo(HttpServletRequest request, HttpServletResponse response, DataMsg dataMsg) throws Exception {
    	String processInsId = request.getParameter("processInsId");
    	String acitityName = request.getParameter("acitityName");
    	String isAll = this.getParameterString("isAll");
    	String myTurn = this.getParameterString("myTurn");
    	List<TaskInfo> dataList  = jbpmServcie.queryNextTaskInfoList(processInsId,isAll,myTurn,acitityName);
		dataMsg.setData(dataList);
		dataMsg.setTotalRows(dataList.size());
		return dataMsg;
	}
    @RequestMapping(value="/isShowTaskInfo.do")
    @ResponseBody
    public Boolean isShowTaskInfo(HttpServletRequest request, HttpServletResponse response, DataMsg dataMsg) throws Exception {
    	String processInsId = request.getParameter("processInsId");
    	String acitityName = request.getParameter("acitityName");
    	String myTurn = request.getParameter("myTurn");
    	boolean   b  = jbpmServcie.isShowTaskInfo(processInsId,myTurn,acitityName);
		
		return b;
	}
}
