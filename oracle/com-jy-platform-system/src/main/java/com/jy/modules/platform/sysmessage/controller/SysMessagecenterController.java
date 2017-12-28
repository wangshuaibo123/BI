package com.jy.modules.platform.sysmessage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.modules.platform.sysmessage.dto.SysMessageDTO;
import com.jy.modules.platform.sysmessage.dto.SysUserMsgRelationDTO;
import com.jy.modules.platform.sysmessage.dto.UserSysMessageDTO;
import com.jy.modules.platform.sysmessage.service.SysMessageService;
import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysMessageController 我的消息 消息中心
 * @description: 定义  SYS_MESSAGE 控制层
 * @author:  luoyr
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysMessagecenter")
public class SysMessagecenterController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysMessagecenterController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.sysmessage.service.SysMessageService")
    private SysMessageService service;
    
    @Autowired
    private SessionAPI sessionAPI;
    @Autowired
	private OrgAPI orgApi;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysmessage/queryMySysMessage");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysmessage/viewMySysMessage");
        }
        
        return model;
    }
    
    /**
     * @author lin
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
    public DataMsg queryListSysMessage(HttpServletRequest request, UserSysMessageDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	//收件人
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
    	
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	dto.setUserId(currentUserId.toString());
    	searchParams.put("dto", dto);
    	
        QueryReqBean params = new QueryReqBean();
        
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<UserSysMessageDTO> list = service.searchMySysMessageByPaging(params.getSearchParams());
        
        Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("publisher", "publisherNameShow");
		
		list = orgApi.mappingOrgUser(list, userMap, null);
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        dataMsg.setStatus(DataMsg.STATUS_OK);
        return dataMsg;
    }
    

   
    /**
     * @author lin
     * @description:删除
     * @date 2014-11-14 11:07:13
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysMessage")
    @ResponseBody
    public DataMsg deleteSysMessage(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
    	Long currentUserId = currentUserInfo.getUserId();
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteMySysMessageByApi(dto,ids,currentUserId.toString());
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
     * @author luoyr
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
        	
        	SysMessageDTO dto = service.querySysMessageAndRelationByApi(id,currentUserId.toString());
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
    
    
}
