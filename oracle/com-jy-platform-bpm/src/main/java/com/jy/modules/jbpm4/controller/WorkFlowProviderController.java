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
import com.jy.platform.jbpm4.jbpm4FormInfo.dto.Jbpm4FormInfoDTO;
import com.jy.platform.jbpm4.jbpm4FormInfo.service.IJbpm4FormInfoService;
import com.jy.platform.jbpm4.service.IJbpmService;
import com.jy.platform.jbpm4.service.JbpmTastService;
import com.jy.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
import com.jy.platform.jbpm4.temporaryJbpm4Info.service.ITemporaryJbpm4InfoService;
import com.jy.platform.jbpm4.tool.StringUtilTools;
import com.jy.platform.restservice.web.base.BaseController;
/**
 * 此类作废 使用BestWorkFlowProviderController类
 * @Description: 前台web页面查询 关于工作流的 proiver 此controller 仅作查询
 * @author chen_gang
 * @date 2014年9月5日 上午11:05:20
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping("/workFlowProviderOLD")
public class WorkFlowProviderController extends BaseController{
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
    public DataMsg findWorkFlowInfo(JbpmDTO dto, @ModelAttribute DataMsg pageData) throws Exception {
        if (pageData == null) pageData = new DataMsg();
        int totalRows = 0;
        Object data = null;
 		if("PROCESS_TASK".equals(dto.getProcessState())){//获取  待办任务
 			List resultList = this.queryTaskByMySql(dto,pageData);
 			totalRows = StringUtilTools.getTotalCountOfListByPage(resultList);
 			data = resultList;
 		}else if("ACTIVE_PROCESS_INFO".equals(dto.getProcessState())){// 活动的流程 信息
 			List resultList = this.queryActiveProcessInfoByMySql(dto,pageData);
 			totalRows = StringUtilTools.getTotalCountOfListByPage(resultList);
 			data = resultList;
 		}else if("END_PROCESS_INFO".equals(dto.getProcessState())){// 结束的流程 信息
 			List resultList = this.queryEndProcessInfoByMySql(dto,pageData);
 			totalRows = StringUtilTools.getTotalCountOfListByPage(resultList);
 			data = resultList;
 		}else if("COMPLETED_TASK".equals(dto.getProcessState())){//获得所有 已经 完成的任务
 			List resultList = this.queryCompletedTaskByMySql(dto,pageData);
 			totalRows = StringUtilTools.getTotalCountOfListByPage(resultList);
 			data = resultList;
 		}else if("END_TASK".equals(dto.getProcessState())){//已办结的任务 查询
 			List resultList = this.queryEndTaskInfo(dto,pageData);
 			totalRows = StringUtilTools.getTotalCountOfListByPage(resultList);
 			data = resultList;
 		}else if("PROCESS_DEFINE_INFO".equals(dto.getProcessState())){//查询流程定义信息
 			List<Map<String,Object>> resultList = this.queryProcessDefineInfo(dto,pageData);
 			totalRows = pageData.getTotalRows();
 			data = resultList;
 		}
        pageData.setData(data);
        pageData.setTotalRows(totalRows);
        return pageData;
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
        
        searchParams.put("processInstantceId", processInstantceId);
        searchParams.put("bizInfId", bizInfId);
        searchParams.put("bizTabName", bizTabName);
        
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<Map<String,Object>> list = jbpmServcie.viewWorkflowHisLog(params.getSearchParams());
        
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
        this.outWriteString(response, bizFile+"龍"+resultMsg);
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
		try {
            ServletOutputStream outStream = response.getOutputStream();// 得到向客户端输出二进制数据的对象
            response.setContentType("image/*"); // 设置返回的文件类型
            Map paraMap = new HashMap();
            paraMap.put("processName", processName);
            paraMap.put("processVersion", processVersion);
            paraMap.put("processInstantceId", processInstantceId);
            paraMap.put("processDefKey", proDefKey);
            
            //paraMap.put("id", "693");
            byte[] pngInfo = jbpmServcie.fetchProPng(paraMap);
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
    	String resultMsg = jbpmServcie.getAllTurenDirectory(proInsId, activeName);
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
           
        String resultMsg = jbpmServcie.getAllActiveProcNodeName(mainProcessInstanceId, subProcessInstanceId);
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
	private List<Map<String,Object>> queryProcessDefineInfo(JbpmDTO dto, DataMsg pageData) {
		List<Map<String,Object>> myResultList = new ArrayList<Map<String,Object>>();
		try {
			List<ProcessDefinition> resultList = jbpmServcie.getAllPdList(dto,pageData);
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
			e.printStackTrace();
		}
		
		return myResultList;
	}

	/**
	 * 查询 已经办结的 历史任务信息
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String,Object>> queryEndTaskInfo(JbpmDTO dto, DataMsg page) throws Exception {
		//使用分页查询
		page.setStartIndex((page.getPageIndex() - 1) * page.getPageSize());
	    page.setEndIndex(page.getPageIndex() * page.getPageSize());
	    
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("dto", dto);
		List<Map<String,Object>> list = jbpmServcie.queryEndTaskList(params);
		return list;
	}
	/**
	 * 查询 已经结束的流程 信息
	 * QUERY_END_PRO_INFO_BY_MY_SQL
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private List<Map<String,Object>> queryEndProcessInfoByMySql(JbpmDTO dto, DataMsg page) throws Exception {
		//使用分页查询
		page.setStartIndex((page.getPageIndex() - 1) * page.getPageSize());
	    page.setEndIndex(page.getPageIndex() * page.getPageSize());
			    
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("dto", dto);
		List<Map<String,Object>> list = jbpmServcie.queryEndProInfoList(params);
		return list;
	}

	/**
	 * 通过 自定义 sql 查询 所有  流程信息
	 * @param index
	 * @param count
	 * @return
	 * @throws Exception 
	 */
	private List queryActiveProcessInfoByMySql(JbpmDTO dto, DataMsg page) throws Exception {
		//使用分页查询
		page.setStartIndex((page.getPageIndex() - 1) * page.getPageSize());
	    page.setEndIndex(page.getPageIndex() * page.getPageSize());
			    
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("dto", dto);
		List list = jbpmServcie.queryActiveProcessInfoList(params);
		return list;
	}
	/**
	 * 通过 自定义 sql 查询 所有  已经完成的任务
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private List queryCompletedTaskByMySql(JbpmDTO dto, DataMsg page) throws Exception {
		//使用分页查询
		page.setStartIndex((page.getPageIndex() - 1) * page.getPageSize());
	    page.setEndIndex(page.getPageIndex() * page.getPageSize());
			    
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("dto", dto);
		List list = jbpmServcie.queryCompletedTask(params);
		
		return list;
	}
	/**
	 * 通过 自定义 sql 查询 所有待办任务
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws Exception 
	 */
	private List<Map> queryTaskByMySql(JbpmDTO dto, DataMsg page) throws Exception {
		//查询当前登录用户及归属角色组的任务
		queryByUserIdAndRoleGroup(dto);
		String assignee = dto.getProcessParticipationName();
		//使用分页查询
		page.setStartIndex((page.getPageIndex() - 1) * page.getPageSize());
	    page.setEndIndex(page.getPageIndex() * page.getPageSize());
	    //使用页面排序
	    if(null!=page.getSortName() && !"".equals(page.getSortName())){
	    	dto.setSortName(page.getSortName());	    	
	    }
	    if(null!=page.getSortType() && !"".equals(page.getSortType())){
	    	dto.setSortType(page.getSortType());
	    }
	    //dto.setCustomSQL("Y");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("page", page);
		params.put("dto", dto);
		List dataList  = jbpmServcie.queryAllTask(params);
		return dataList;
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
   			response.setContentType("text/Xml;charset=utf-8");
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
    
}
