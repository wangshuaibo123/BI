package com.fintech.modules.common.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.platform.sysmessage.dto.SysMessageDTO;
import com.fintech.modules.platform.sysmessage.dto.UserSysMessageDTO;
import com.fintech.modules.platform.sysmessage.service.SysMessageService;
import com.fintech.modules.platform.sysversion.dto.SysVersionDTO;
import com.fintech.modules.platform.sysversion.service.SysVersionService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

@Controller
@Scope("prototype")
@RequestMapping("/home")
public class HomeController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(HomeController.class);
    
    
    @Autowired
    private SessionAPI sessionAPI;
    
    @Autowired
    @Qualifier("com.fintech.modules.platform.sysversion.service.SysVersionService")
    private SysVersionService serviceVer;
    
    @Autowired
    @Qualifier("com.fintech.modules.platform.sysmessage.service.SysMessageService")
    private SysMessageService serviceMsg;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        if("welcome".equals(operate)){//跳转至 查询页面
            //设置默认的记录条数
            DataMsg dataMsg = new DataMsg();
            dataMsg.setPageIndex(1);
            dataMsg.setPageSize(10);
            //查询最新消息、通知通告和系统版本
            List<UserSysMessageDTO> myMsgList = queryMyMessageList(dataMsg);
            List<SysMessageDTO> grobalMsgList = queryGrobalMessageList(dataMsg);
            List<SysVersionDTO> sysVersionList = querySysVersionList(dataMsg);
            
            model.addObject("myMsgList",myMsgList);
            model.addObject("grobalMsgList",grobalMsgList);
            model.addObject("sysVersionList",sysVersionList);
            
        	model.setViewName("welcome");
        }
        
        return model;
    }
    
    /**
     * 查询最新信息
    	* @title: queryMyMessageList
    	* ljw
    	* @description:
    	* @date 2015年5月10日 上午10:09:41
    	* @param userId
    	* @param dataMsg
    	* @return
    	* @throws Exception
    	* @throws
     */
    public  List<UserSysMessageDTO> queryMyMessageList(DataMsg dataMsg) {
        //收件人
        UserInfo currentUserInfo = sessionAPI.getCurrentUserInfo();
        Long currentUserId = currentUserInfo.getUserId();
        
        UserSysMessageDTO dto = new UserSysMessageDTO();
        Map<String, Object> searchParams = new HashMap<String, Object>();
        dto.setUserId(currentUserId.toString());
        searchParams.put("dto", dto);
        
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);
        
        List<UserSysMessageDTO> list = null;
        try {
            list = serviceMsg.searchMySysMessageByPaging(params.getSearchParams());
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.debug("首页：查询最新信息失败");
        }
        return list;
    }
    /**
     * 查询通知通告
    	* @title: queryGrobalMessageList
    	* ljw
    	* @description:
    	* @date 2015年5月10日 上午10:43:15
    	* @param dataMsg
    	* @return
    	* @throws
     */
    public List<SysMessageDTO> queryGrobalMessageList(DataMsg dataMsg){
        Map<String, Object> searchParams = new HashMap<String, Object>();
        UserSysMessageDTO dto = new UserSysMessageDTO();
        dto.setType("0");//全局消息
        searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);
        List<SysMessageDTO> list = null;
        try {
            list = serviceMsg.searchSysMessageByPaging(params.getSearchParams());
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.debug("首页：查询通知通告失败");
        }
        return list;
    }
    /**
     * 查询系统版本
    	* @title: querySysVersionList
    	* ljw
    	* @description:
    	* @date 2015年5月10日 上午11:12:21
    	* @param dataMsg
    	* @return
    	* @throws
     */
    public List<SysVersionDTO> querySysVersionList(DataMsg dataMsg){
        List<SysVersionDTO> list = null;
        try {
            SysVersionDTO dto = new SysVersionDTO();
            dto.setSystemState(queryUserAppCode());
            Map<String, Object> searchParams = new HashMap<String, Object>();
            searchParams.put("dto", dto);
            QueryReqBean params = new QueryReqBean();
            params.setSearchParams(searchParams);
            PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
            params.setPageParameter(pageInfo);
            list = serviceVer.searchSysVersionByPaging(params.getSearchParams());
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.debug("首页：查询系统版本失败");
        }
        return list;
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
    private String queryUserAppCode() {
        String appCode="SSSS";
        try{
            Properties properties = new Properties();   
            ClassPathResource cp = new ClassPathResource("biz_app.properties");
            properties.load(cp.getInputStream());
            appCode = properties.getProperty("app.code").trim(); 
        } catch(IOException e) {
            logger.debug("load biz_app.properties error, default value , appCode=SSSS");
            return appCode;
            //throw new RuntimeException("load biz_app.properties error!");
        }
        return appCode;
    }
    
}
