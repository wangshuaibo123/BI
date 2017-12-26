package com.fintech.modules.platform.sysmessage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.service.SysMessageService;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: GrobalMesController 全局消息,供cas首页使用
 * @description: 定义  GrobalMes 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/grobalMes")
public class GrobalMesController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(GrobalMesController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.sysmessage.service.SysMessageService")
    private SysMessageService service;
    
    @Autowired
    private SessionAPI sessionAPI;
    @Autowired
	private OrgAPI orgApi;
    
    
    /**
     * @description:查询最新消息列表,默认为20条(分页的参数)
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryNewMessages")
    @ResponseBody
    public ModelAndView queryNewMessages(HttpServletRequest request,HttpServletResponse response, SysMessageDTO dto, @ModelAttribute DataMsg dataMsg,String systemFlag) throws Exception {
    	ModelAndView model = new ModelAndView();
    	model.setViewName("platform/sysmessage/groabalMes");
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	dto.setType("0");//全局消息
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
        List<SysMessageDTO> list = service.searchSysMessageByPaging(params.getSearchParams());
        model.addObject("list", list);
        return model;
    }
    /**
     * @description:查询最新消息列表,默认为20条(分页的参数)
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryNewMessageDetail")
    @ResponseBody
    public ModelAndView queryNewMessageDetail(HttpServletRequest request,HttpServletResponse response, SysMessageDTO dto, @ModelAttribute DataMsg dataMsg,String systemFlag) throws Exception {
    	String id = this.getParameterString("id");
    	ModelAndView model = this.queryOneDTO(id);
    	model.setViewName("platform/sysmessage/groabalMesDetail");
        return model;
    }
    /**
     * @description:查询最新消息,默认为20条(分页的参数)
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryNewMessage")
    @ResponseBody
    public void queryNewMessage(HttpServletRequest request,HttpServletResponse response, SysMessageDTO dto, @ModelAttribute DataMsg dataMsg,String systemFlag,@RequestParam String callback) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	dto.setType("0");//全局消息
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
        PrintWriter out=null;
		try {
			out=response.getWriter();
			out.write(callback+"("+JSONArray.toJSONString(dataMsg)+")");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null!=out){
				out.close();
			}
		}
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
        	
        	//对于管理平台,查看消息内容,不需要更新消息的可读状态,并且不需要同步redis
        	SysMessageDTO dto = service.querySysMessageByPrimaryKey(id);
        	dto.removeNullRelationData();//判断是否移除空的关联数据
        	
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
