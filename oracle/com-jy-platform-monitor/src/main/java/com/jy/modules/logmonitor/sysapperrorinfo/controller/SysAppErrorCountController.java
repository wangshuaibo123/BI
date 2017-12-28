package com.jy.modules.logmonitor.sysapperrorinfo.controller;

import java.util.ArrayList;
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

import com.jy.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorInfoDTO;
import com.jy.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysAppErrorInfoController
 * @description: 定义  业务系统节点错误日志 控制层
 * @author:  lei
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysAppErrorCount")
public class SysAppErrorCountController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysAppErrorCountController.class);

    @Autowired
    @Qualifier("com.jy.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService")
    private SysAppErrorInfoService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toCountPage".equals(operate)){//跳转至统计页面
        	//String id = this.getParameterString("id");
        	//model = this.queryOneDTO(id);
        	model.setViewName("platform/sysapperrorinfo/countSysAppErrorInfo");
        }else if("toPercentPage".equals(operate)){ //跳转至 占比页面
        	model.setViewName("platform/sysapperrorinfo/percentSysAppErrorInfo");
        }
        
        return model;
    }
    
    /**
     * @author luoyr
     * @description:统计错误日志
     * @date 2015-04-03 10:07:07
     * @param request
     * @param dto
     * @param ctimeType 统计类型
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAppErrorCount")
    @ResponseBody
    public DataMsg queryListSysAppErrorCount(HttpServletRequest request, SysAppErrorInfoDTO dto,int ctimeType, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        List<SysAppErrorInfoDTO> list = service.countSysAppErrorInfo(searchParams,ctimeType);
        //根据系统标识拆分集合
        Map<String,List<SysAppErrorInfoDTO>> map = new HashMap<String,List<SysAppErrorInfoDTO>>();
        List<SysAppErrorInfoDTO> sublist = null;
        for (SysAppErrorInfoDTO sysAppErrorInfoDTO : list) {
        	if(map.get(sysAppErrorInfoDTO.getAppFlag()) == null){//map中无此元素
        		sublist = new ArrayList<SysAppErrorInfoDTO>();
        		sublist.add(sysAppErrorInfoDTO);
            	map.put(sysAppErrorInfoDTO.getAppFlag(),sublist);
        	}else{//map中有此元素
        		sublist = map.get(sysAppErrorInfoDTO.getAppFlag());
        		sublist.add(sysAppErrorInfoDTO);
        	}
        	
		}
        dataMsg.setData(map);
        return dataMsg;
    }
    
    /**
     * @author luoyr
     * @description:错误日志占比
     * @date 2015-04-03 10:07:07
     * @param request
     * @param dto
     * @param ctimeType 统计类型
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAppErrorPercent")
    @ResponseBody
    public DataMsg queryListSysAppErrorPercent(HttpServletRequest request, SysAppErrorInfoDTO dto,int ctimeType, @ModelAttribute DataMsg dataMsg) throws Exception {
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        List<SysAppErrorInfoDTO> list = service.percentSysAppErrorInfo(searchParams,ctimeType);
        //根据系统标识拆分集合
        Map<String,List<SysAppErrorInfoDTO>> map = new HashMap<String,List<SysAppErrorInfoDTO>>();
        List<SysAppErrorInfoDTO> sublist = null;
        for (SysAppErrorInfoDTO sysAppErrorInfoDTO : list) {
        	if(map.get(sysAppErrorInfoDTO.getAppFlag()) == null){//map中无此元素
        		sublist = new ArrayList<SysAppErrorInfoDTO>();
        		sublist.add(sysAppErrorInfoDTO);
            	map.put(sysAppErrorInfoDTO.getAppFlag(),sublist);
        	}else{//map中有此元素
        		sublist = map.get(sysAppErrorInfoDTO.getAppFlag());
        		sublist.add(sysAppErrorInfoDTO);
        	}
        	
		}
        dataMsg.setData(map);
        return dataMsg;
    }
    
}
