package com.fintech.modules.platform.sysmessage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.common.util.ObtainPropertiesInfo;
import com.fintech.modules.platform.sysmessage.dto.MessageStatus;
import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.fintech.modules.platform.sysmessage.impl.MsgApiGetImpl;
import com.fintech.modules.platform.sysmessage.impl.MsgApiUpdateImpl;
import com.fintech.modules.platform.sysmessage.service.SysMessageService;
import com.fintech.platform.api.msg.Msg;
import com.fintech.platform.api.msg.MsgAPI;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.common.SystemFlag;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restclient.http.RestClient;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.common.DateUtil;


/**
 * @classname: SysMessageController
 * @description: 定义  SYS_MESSAGE 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysMessage")
public class SysMessageController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysMessageController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.sysmessage.service.SysMessageService")
    private SysMessageService service;
    
    @Autowired
    private SessionAPI sessionAPI;
    @Autowired
	private OrgAPI orgApi;
    @Autowired
    private SysConfigAPI sysConfig;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysmessage/querySysMessage");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
            String appCode = queryUserAppCode();
            model.addObject("appCode", appCode);
        	model.setViewName("platform/sysmessage/addSysMessage");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysmessage/updateSysMessage");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysmessage/viewSysMessage");
        }   
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-11-14 11:07:13
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysMessage")
    @ResponseBody
    public DataMsg queryListSysMessage(HttpServletRequest request, SysMessageDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysMessageDTO> list = service.searchSysMessageByPaging(params.getSearchParams());
        //匹配用户ID
        Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("publisher", "publisherNameShow");
		
		list = orgApi.mappingOrgUser(list, userMap, null);
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        dataMsg.setStatus(DataMsg.STATUS_OK);
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2014-11-14 11:07:13
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysMessage")
    @ResponseBody
    public DataMsg insertSysMessage(HttpServletRequest request, SysMessageDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysMessageDTO)super.initDto(dto);
        	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
        	Long currentUserId = currentUserInfo.getUserId();
        	//初始化数据
        	dto.setStatus(MessageStatus.LifeStatus.INIT.getValue());//消息状态为初始化
        	dto.setPublisher(currentUserId.toString());//发布人为当前操作人
        	dto.setCreateDate(new Date());//创建日期为当前日期
        	String sysFlag = ObtainPropertiesInfo.getValByKey("app.code");
            dto.setSysFlag(sysFlag);
            //执行插入操作
            if(service.insertSysMessageByApi(dto)){
            	 dataMsg = super.initDataMsg(dataMsg);
            	 String successMsg = "新增成功";
                 dataMsg.setStatus(DataMsg.STATUS_OK);
                 dataMsg.setData(this.makeJSONData(dto.getMsgId()));
           	 	 dataMsg.setMsg(successMsg);
            }else{
            	 dataMsg = super.initDataMsg(dataMsg);
                 dataMsg.setMsg("新增失败");
                 dataMsg.setStatus(DataMsg.STATUS_FAILED);
                 dataMsg.setData(this.makeJSONData(dto.getMsgId()));
            }
          
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }
    
    /**
     * @author
     * @description:重发
     * @date 2014-11-14 11:07:13
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/redoInsertSysMessage")
    @ResponseBody
    public DataMsg redoInsertSysMessage(HttpServletRequest request, SysMessageDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
        	Long currentUserId = currentUserInfo.getUserId();
        	
        	dto = (SysMessageDTO)super.initDto(dto);
        	dto.setStatus(MessageStatus.LifeStatus.INIT.getValue());//初始化
        	dto.setCreateDate(new Date());//消息创建时间
        	dto.setPublisher(currentUserId.toString());//发布人为当前操作人
        	String sysFlag = ObtainPropertiesInfo.getValByKey("app.code");
            dto.setSysFlag(sysFlag);
            if(service.insertSysMessageByApi(dto)){
            	dataMsg = super.initDataMsg(dataMsg);
            	String successMsg = "重发成功";
                dataMsg.setStatus(DataMsg.STATUS_OK);
                dataMsg.setData(this.makeJSONData(dto.getMsgId()));
          	 	dataMsg.setMsg(successMsg);
            }else{
            	dataMsg = super.initDataMsg(dataMsg);
                dataMsg.setMsg("重发失败");
                dataMsg.setStatus(DataMsg.STATUS_FAILED);
                dataMsg.setData(this.makeJSONData(dto.getMsgId()));
            }
            
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法redoInsertSysMessage异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2014-11-14 11:07:13
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysMessage")
    @ResponseBody
    public DataMsg updateSysMessage(HttpServletRequest request, SysMessageDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysMessageDTO)super.initDto(dto);
           
            service.updateSysMessage(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setStatus(DataMsg.STATUS_OK);
            dataMsg.setData(this.makeJSONData(dto.getMsgId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @throws Exception 
     * @author
     * @description:删除
     * @date 2014-11-14 11:07:13
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysMessage")
    @ResponseBody
    public DataMsg deleteSysMessage(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) throws Exception{
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
   	 	String [] idArray = ids.split(",");  
   	 	for (int i = 0; i < idArray.length; i++) {
   	 		SysMessageDTO msg = service.querySysMessageByPrimaryKey(idArray[i]);
   	 		if (msg != null && "3".equals(msg.getStatus())) {//存在已经删除状态的消息，结束不再进行删除操作
   	 		 dataMsg.setStatus(DataMsg.STATUS_OK);
			 dataMsg.setMsg("选择删除的记录中存在已删除状态的消息，请重新选择");
			 return dataMsg;
			}
		}
   	 	
		try {
			 service.deleteSysMessageByApi(dto,ids,currentUserId.toString());
			 dataMsg.setStatus(DataMsg.STATUS_OK);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    @RequestMapping(value = "/doCountMessage")
    @ResponseBody
    public DataMsg doCountMessage(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	BaseDTO dto = super.initDto(null);
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 int count = service.countSysMessageByUserId(dto,currentUserId.toString());
			 dataMsg.setStatus(DataMsg.STATUS_OK);
			 dataMsg.setData(count);
			 dataMsg.setMsg("查询成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法doCountMessage异常：", e);

		}
        
        return dataMsg;
    }
    
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-11-14 11:07:13
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	
        	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
        	Long currentUserId = currentUserInfo.getUserId();
        	//对于管理平台,查看消息内容,不需要更新消息的可读状态,并且不需要同步redis
        	SysMessageDTO dto = service.querySysMessageByPrimaryKey(id);
        	dto.removeNullRelationData();//判断是否移除空的关联数据
        	List<SysUserMsgRelationDTO> sysUserMsgRelations = dto.getSysUserMsgRelations();
        	//userid 匹配 用户姓名
        	if(sysUserMsgRelations != null){
        		Map<String,String> userMap = new HashMap<String,String>();
        		userMap.put("userId", "userNameShow");
        		sysUserMsgRelations = orgApi.mappingOrgUser(sysUserMsgRelations, userMap, null);
        		dto.setSysUserMsgRelations(sysUserMsgRelations);
        	}
        	//匹配发布人姓名
        	UserInfo userInfoDetail = orgApi.getUserInfoDetail(dto.getPublisher());
        	dto.setPublisherNameShow(userInfoDetail.getUserName());
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    /**
     * 是否有消息
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/hasMyMessage")
    @ResponseBody
    public DataMsg hasMyMessage(HttpServletRequest request, SysMessageDTO dto,String systemFlag,@ModelAttribute DataMsg dataMsg) throws Exception {
        
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
		MsgAPI api = new MsgApiGetImpl();
	    List<Msg> globalMsg = api.getMsg(String.valueOf(currentUserId), systemFlag, "0");
	    List<Msg> specificMsg = api.getMsg(String.valueOf(currentUserId), systemFlag, "1");
	    List<Msg> result = new ArrayList<Msg>();
	    result.addAll(globalMsg);
	    result.addAll(specificMsg);
	    dataMsg.setData(result);
	    dataMsg.setTotalRows(result.size());
	    dataMsg.setStatus(DataMsg.STATUS_OK);
	    if ((globalMsg!= null && globalMsg.size()>0 )||(specificMsg != null && specificMsg.size() >0)){
	    	 int count = service.countSysMessageByUserId(dto,currentUserId.toString());
	    	 dataMsg.setData(count);
		}else{
			dataMsg.setData("0");
		}
        return dataMsg;
    }
     
    
    /**
     * 是否有消息
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateMessageHasRead")
    @ResponseBody
    public DataMsg updateMessageHasRead(HttpServletRequest request, SysMessageDTO dto,String msgId,String systemFlag, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
		MsgAPI api = new MsgApiUpdateImpl();
		//
		boolean updateResult = api.updateMsgHasRead(msgId, systemFlag, String.valueOf(currentUserId));
		if (updateResult) {
			 dataMsg.setStatus(DataMsg.STATUS_OK);
		}
        return dataMsg;
    }
    
    
    
    /**
     * 查询消息
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryMyMessage")
    @ResponseBody
    public DataMsg queryMyMessage(HttpServletRequest request,HttpServletResponse response, SysMessageDTO dto,String systemFlag, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	//用于独立部署时，解决跨域问题
    	//response.setHeader("Access-Control-Allow-Origin", "*");
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
    	 long time =20000;  
 		Long start=new Date().getTime();
 		long end=new Date().getTime();
 		while(end-start<time){
 			Thread.sleep(6000);
			end=new Date().getTime();
			MsgAPI api = new MsgApiGetImpl();
	    	List<Msg> globalMsg = api.getMsg(String.valueOf(currentUserId),systemFlag, "0");
	    	List<Msg> specificMsg = api.getMsg(String.valueOf(currentUserId), systemFlag, "1");
	    	List<Msg> result = new ArrayList<Msg>();
	    	result.addAll(globalMsg);
	    	result.addAll(specificMsg);
	        dataMsg.setData(result);
	        dataMsg.setTotalRows(result.size());
	        dataMsg.setStatus(DataMsg.STATUS_OK);
 		}
        return dataMsg;
    }
    
    @RequestMapping(value = "/loadUnReadMsg")
    @ResponseBody
    public DataMsg loadUnReadMsg(HttpServletRequest request,HttpServletResponse response,String systemFlag,@ModelAttribute DataMsg dataMsg)throws Exception{
    	try {
	    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
	    	Long currentUserId = currentUserInfo.getUserId();
	    	
	    	Map<String, Object> searchParams = new HashMap<String, Object>();
			SysMessageDTO dto = new SysMessageDTO();
	    	dto.setSysFlag(systemFlag);
	    	dto.setUserId(currentUserId+"");
	    	String time = DateUtil.getSpecifiedDayBefore(new Date(), "yyyy/MM/dd", 1);
	    	dto.setCreateDate(DateUtil.getDateFromString(time,"yyyy/MM/dd"));
	    	searchParams.put("dto", dto);
	    	//读取当前用户下两天内前五条未读消息
	    	List<SysMessageDTO> list = service.searchUnRead2DSysMessage(searchParams);
	    	for(SysMessageDTO msg:list){
	    		msg.setUserId(currentUserId+"");
	    		msg.setType("1");
	    		String msgPushURL = sysConfig.getValue("MSG_PUSH_WEBROOT");
	            String jyptAppId = "ptpt"; // rest服务appId
	      	 	String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址
	      	 	String MSG_PUSH_URL   = jyptURL + "/api/platform/smsPushlet/push/v1";
	      	 	if(StringUtils.isNotEmpty(msgPushURL)){
	      	 		msgPushURL = msgPushURL +"/api/platform/smsPushlet/push/v1";
	      	 		MSG_PUSH_URL = msgPushURL;
	      	 	}
	      	 	
      	 		ResponseMsg<String> responseMsg = RestClient.doPost(jyptAppId, MSG_PUSH_URL, msg, new TypeReference<ResponseMsg<String>>(){});
    			if(!StringUtils.isNotEmpty(responseMsg.getErrorDesc())){
    				logger.info("userId:"+currentUserId+" do repush operation in loadUnReadMsg method Sucess,read message size:"+list.size());
    			}else{
    				logger.error("userId:"+currentUserId+" do repush operation in loadUnReadMsg method failed! {MSG_PUSH_URL:"+MSG_PUSH_URL+",jyptAppId:"+jyptAppId+"}");
    			}
	    		
	    	}
	    	dataMsg.setData(list);
    	} catch (Exception e) {
			logger.error("用户 首次登录，重新推送未读消息异常：", e);
		}
    	return dataMsg;
    }
    
    /**
     * 查询消息
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryMyMessage.jsonp")
    @ResponseBody
    public void queryMyMessageJson(HttpServletRequest request,HttpServletResponse response,String userId,String systemFlag, String msgType,String callback) throws Exception {
        
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
        long time =20000;  
		Long start=new Date().getTime();
		long end=new Date().getTime();
		while(end-start<time){
			try {
				Thread.sleep(4000);
				end=new Date().getTime();
				MsgAPI api = new MsgApiGetImpl();
		    	List<Msg> globalMsg = api.getMsg(String.valueOf(currentUserId), SystemFlag.PT.getFlag(), "0");
		    	List<Msg> specificMsg = api.getMsg(String.valueOf(currentUserId), SystemFlag.PT.getFlag(), "1");
		    	List<Msg> result = new ArrayList<Msg>();
		    	result.addAll(globalMsg);
		    	result.addAll(specificMsg);
				PrintWriter out=null;
				try {
					out=response.getWriter();
					out.write("callback("+JSONArray.toJSONString(result)+");");
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					if(null!=out){
						out.close();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    

    /**
     * 查询登录用户的机构编码(app.code),默认是S000
    	* @title: queryUserAppCode
    	* ljw
    	* @description:
    	* @date 2015年4月24日 下午2:53:14
    	* @return
    	* @throws AbaboonException
    	* @throws
     */
    private String queryUserAppCode() throws AbaboonException {
        String appCode="S000";
        try{
            Properties properties = new Properties();   
            ClassPathResource cp = new ClassPathResource("biz_app.properties");
            properties.load(cp.getInputStream());
            appCode = properties.getProperty("app.code").trim(); 
        } catch(IOException e) {
            logger.debug("load biz_app.properties error, default value , appCode=S000");
            return appCode;
            //throw new RuntimeException("load biz_app.properties error!");
        }
        return appCode;
        
    }
    
}
