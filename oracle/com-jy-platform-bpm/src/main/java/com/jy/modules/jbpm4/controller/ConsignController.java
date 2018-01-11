package com.jy.modules.jbpm4.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jy.platform.api.org.SessionAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.jbpm4.dto.Consign;
import com.jy.platform.jbpm4.dto.ConsignModel;
import com.jy.platform.jbpm4.service.ConsignService;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * 
 * @Description 个人任务委托
 * @author chen_gng
 * @date 2014年9月18日 上午9:23:10
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping("/consign")
public class ConsignController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ConsignController.class);
    @Autowired
    private SessionAPI sessionAPI;
    @Autowired
    @Qualifier("com.jy.platform.jbpm4.service.impl.ConsignServiceImpl")
    private ConsignService consignService;

    @RequestMapping("/toConsignList")
    public String toConsignList(HttpServletRequest request, ModelMap model) {
    	String isAdmin = request.getParameter("isAdmin");
        return "forward:/component/jbpm/consign/consignList.jsp?isAdmin="+isAdmin;
    }

    @RequestMapping("/toAddConsign")
    public String toAddConsign(HttpServletRequest request, ModelMap model) {

        return "forward:/component/jbpm/consign/addConsign.jsp";
    }
    
    @RequestMapping("/toAddAgencyConsign")
    public String toAddAgencyConsign(HttpServletRequest request, ModelMap model) {

        return "forward:/component/jbpm/consign/addAgencyConsign.jsp";
    }

    /**
     * 工作委托条件查询
     * 
     * @author cxt
     * @param requ
     * @param response
     * @param consign
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findAllConsign")
    @ResponseBody
    public DataMsg findAllConsign(HttpServletRequest request, @ModelAttribute DataMsg pageData) {
        if (pageData == null) {
            pageData = new DataMsg();
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            // 查询条件
            String fromUserId = request.getParameter("fromUserId");
            String toUserId = request.getParameter("toUserId");
            String reason = request.getParameter("reason");
            String proDefKey = request.getParameter("proDefKey");
            String createdBy = request.getParameter("createdBy");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotEmpty(startTime)) {
                startTime += " 00:00:00";
            }
            if (StringUtils.isNotEmpty(endTime)) {
                endTime += " 23:59:59";
            }
            String remark = request.getParameter("remark");
            String isAdmin = request.getParameter("isAdmin");
            param.put("fromUserId", fromUserId);
            param.put("toUserId", toUserId);
            param.put("reason", reason);
            param.put("proDefKey", proDefKey);
            param.put("createdBy", createdBy);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            param.put("remark", remark);
            param.put("isAdmin", isAdmin);
            UserInfo userInfo = sessionAPI.getCurrentUserInfo();
            String loginUserId ="-1";
            if(userInfo != null){
                loginUserId = userInfo.getUserId()+"";
            }
            param.put("loginUserId", loginUserId);
            param.put("startIndex", (pageData.getPageIndex() - 1) * pageData.getPageSize() + 1);
            param.put("endIndex", (pageData.getPageIndex() - 1) * pageData.getPageSize() + 1 + pageData.getPageSize());

            List<Map> quartzList = consignService.getConsignByPage(param);

            int count = consignService.findAllConsignCount(param);
            pageData.setData(quartzList);
            pageData.setTotalRows(count);
        }catch (Exception ex) {
        	logger.error("执行方法 findAllConsign 失败 error:", ex);
        	ex.printStackTrace();
        }

        return pageData;
    }

    /**
     * 新增或修改委托任务
     * 
     * @author cxt
     * @param requ
     * @param response
     * @param consign
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addOrUpdateConsign")
    @ResponseBody
    public DataMsg addOrUpdateConsign(ConsignModel model, HttpServletRequest request,
            @ModelAttribute DataMsg pageData) {
        if (pageData == null) {
            pageData = new DataMsg();
        }
        String msg = "";
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            String orgId = "";
            String userId = "";
            UserInfo userInfo = sessionAPI.getCurrentUserInfo();
            if(userInfo != null){
            	 orgId = userInfo.getOrgId()+"";
            	 userId = userInfo.getUserId()+"";
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotEmpty(model.getFromUserId())) {
                model.setFromUserId(model.getFromUserId());
            }
            if (model.getId() != null && model.getId() >0 ) {// 修改
            	//不允许修改
              /*  if (userInfo != null) {
                    model.setLastUpdBy(userId);
                }
                model.setLastUpdStr(sdf.format(new Date()));
                param.put("model", model);
                consignService.updateConsign(param);
                msg = "修改成功！";*/
            } else {// 新增
                model.setCreatedBy(userId);

                model.setCreatedStr(sdf.format(new Date()));
                param.put("model", model);
                consignService.addConsign(param);
                msg = "新增成功！";
            }
        }catch (Exception ex) {
            msg = "操作失败！";
            logger.error("执行方法 addOrUpdateConsign 失败 error:", ex);
        }finally {
            pageData.setMsg(msg);
        }
        return pageData;
    }

    /**
     * 删除委托任务
     * 
     * @author cxt
     * @param requ
     * @param response
     * @param consign
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delById")
    @ResponseBody
    public DataMsg delById(ConsignModel model, HttpServletRequest request,
            @ModelAttribute DataMsg pageData) {
        if (pageData == null) {
            pageData = new DataMsg();
        }
        String msg = "删除成功！";
        try {
            String id = request.getParameter("id");
            UserInfo userInfo = sessionAPI.getCurrentUserInfo();
            String userId ="-1";
            if(userInfo != null){
            	 userId = userInfo.getUserId()+"";
            }
            
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", Integer.parseInt(id));
            param.put("curUserId", userId);//当前登录人的USERID
            
            consignService.delById(param);

        }catch (Exception ex) {
        	logger.error("执行方法 delById 失败 error:", ex);
            msg = "删除失败！";
        }finally {
            pageData.setMsg(msg);
        }
        return pageData;
    }

    /**
     * 打开更新界面
     * 
     * @author cxt
     * @param requ
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpdateConsign")
    @ResponseBody
    public ModelAndView toUpdateConsign(HttpServletRequest request, ModelMap model) {
        ModelAndView mav = new ModelAndView("forward:/component/jbpm/consign/addConsign.jsp"); // 返回的路径
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String id = request.getParameter("id");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("id", Integer.parseInt(id));
            Consign consign = consignService.getById(param);
            ConsignModel consignModel = new ConsignModel();
            BeanUtils.copyProperties(consign, consignModel);
            if (consign.getStartTime() != null) {
                consignModel.setStartTimeStr(sdf.format(consign.getStartTime()));
            }
            if (consign.getEndTime() != null) {
                consignModel.setEndTimeStr(sdf.format(consign.getEndTime()));
            }
            if (consign.getCreated() != null) {
                consignModel.setCreatedStr(sdf.format(consign.getCreated()));
            }
            if (consign.getLastUpd() != null) {
                consignModel.setLastUpdStr(sdf.format(consign.getLastUpd()));
            }
            mav.addObject("consign", consignModel);
            mav.addObject("type", "edit");
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return mav;
    }

    /**
     * 校验工作委托（办理人是否有委托）
     * 
     * @author cxt
     * @param requ
     * @param response
     * @param consign
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/consignToOther")
    @ResponseBody
    public DataMsg consignToOther(HttpServletRequest request, @ModelAttribute DataMsg pageData) {
        if (pageData == null) {
            pageData = new DataMsg();
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            // 查询条件
            String id = request.getParameter("id");
            String toUserId = request.getParameter("toUserId");
            String reason = request.getParameter("reason");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            param.put("toUserId", toUserId);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            param.put("id", id);

            int consignList = consignService.consignToOther(param);
            System.out.println("consignToOther:" + consignList);
            pageData.setData(consignList);
        }catch (Exception ex) {
        	ex.printStackTrace();
        }

        return pageData;
    }

    /**
     * 校验工作委托（委托人是否已委托）
     * 
     * @author cxt
     * @param requ
     * @param response
     * @param consign
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/repeatConsign")
    @ResponseBody
    public DataMsg repeatConsign(HttpServletRequest request, @ModelAttribute DataMsg pageData) {
        if (pageData == null) {
            pageData = new DataMsg();
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            // 查询条件
            String id = request.getParameter("id");
            String fromUserId = request.getParameter("fromUserId");
            String reason = request.getParameter("reason");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            param.put("fromUserId", fromUserId);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            param.put("id", id);

            int consignList = consignService.repeatConsign(param);
            System.out.println("repeatConsign:" + consignList);
            pageData.setData(consignList);
        }catch (Exception ex) {
        	logger.error("执行方法 repeatConsign 失败 error:", ex);
        	ex.printStackTrace();
        }

        return pageData;
    }


}
