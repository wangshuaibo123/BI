package com.jy.modules.platform.sysarea.controller;

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

import com.jy.modules.platform.sysarea.dto.SysAreaDTO;
import com.jy.modules.platform.sysarea.service.SysAreaService;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysAreaController
 * @description: 定义  行政区域 控制层
 * @author:  lin
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysArea")
public class SysAreaController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysAreaController.class);
    
    //城市列表
    private static List<SysAreaDTO> cityList = null;

    @Autowired
    @Qualifier("com.jy.modules.platform.sysarea.service.SysAreaService")
    private SysAreaService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysarea/querySysArea");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysarea/addSysArea");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysarea/updateSysArea");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysarea/viewSysArea");
        }else if("toTest".equals(operate)){//跳转至 查看页面
        	model.setViewName("platform/sysarea/testSysArea");
        }
        
        return model;
    }
    
    /**
     * @author lin
     * @description:查询分页列表
     * @date 2014-10-23 09:53:30
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysArea")
    @ResponseBody
    public DataMsg queryListSysArea(HttpServletRequest request, SysAreaDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysAreaDTO> list = service.searchSysAreaByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author lin
     * @description:新增
     * @date 2014-10-23 09:53:30
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysArea")
    @ResponseBody
    public DataMsg insertSysArea(HttpServletRequest request, SysAreaDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAreaDTO)super.initDto(dto);

            service.insertSysArea(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author lin
     * @description:编辑
     * @date 2014-10-23 09:53:30
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysArea")
    @ResponseBody
    public DataMsg updateSysArea(HttpServletRequest request, SysAreaDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysAreaDTO)super.initDto(dto);
           
            service.updateSysArea(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author lin
     * @description:删除
     * @date 2014-10-23 09:53:30
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysArea")
    @ResponseBody
    public DataMsg deleteSysArea(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysAreaByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author lin
     * @description:通过主键查询 其明细信息
     * @date 2014-10-23 09:53:30
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysAreaDTO dto = service.querySysAreaByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    
    /**
     * @author lin
     * @description:查询分页列表
     * @date 2014-10-23 09:53:30
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAreaByParentId")
    @ResponseBody
    public DataMsg queryListSysAreaByParentId(HttpServletRequest request, SysAreaDTO dto,long pId,int level, @ModelAttribute DataMsg dataMsg) throws Exception {
    	
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		List<SysAreaDTO> result = new ArrayList<SysAreaDTO>();
    	if(cityList == null){//取所有，分页参数没用
    		cityList = service.searchSysArea(params.getSearchParams());
    	}
    	boolean flag = isMunicipality(pId);
    	//municipalityList 暂存直辖市下的二级数据
    	List<SysAreaDTO> municipalityList = new ArrayList<SysAreaDTO>();
    	for(int i=0,len = cityList.size();i<len;i++){
    		SysAreaDTO sysAreaDTO = (SysAreaDTO)cityList.get(i);
    		//展示2级列表时，数据结构不支持，需特殊处理直辖市
    		if(level == 2&&flag){
    			if(sysAreaDTO.getParentId()==pId){
    				municipalityList.add(sysAreaDTO);
    			}
    		}else{
    			if(sysAreaDTO.getParentId()==pId){
        			result.add(sysAreaDTO);
        		}
    		}
		}
    	//2级展示，直辖市需要再次循环，直接查询区县数据返回
    	if(municipalityList!=null&&level==2){
    		for (int i = 0; i < municipalityList.size(); i++) {
    			SysAreaDTO sysAreaDTO = (SysAreaDTO)municipalityList.get(i);
    			for(int j=0,len = cityList.size();j<len;j++){
    				SysAreaDTO all = (SysAreaDTO)cityList.get(j);
    				if(sysAreaDTO.getId()==all.getParentId()){
    	    			result.add(all);
    	    		}
    			}
			}
    	}
        dataMsg.setData(result);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    /**
     * 是否是直辖市
     * @author lin
     * @param parentId
     * @return
     * @throws Exception
     */
    private boolean isMunicipality(long parentId) throws Exception{
    	boolean result = false;
    	String municipality = "北京市,天津市,上海市,重庆市";
    	if(parentId>0){
    		SysAreaDTO city = service.querySysAreaByPrimaryKey(String.valueOf(parentId));
        	if(city!=null&&municipality.indexOf(city.getAreaName())!=-1){
        		result = true;
        	}
    	}
    	return result;
    	
    }
    
    /**
     * @author bieshuangping
     * @description:根据areaCode查询下级区域信息
     * @date 2014-10-29 09:53:30
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryAreaByCode/{areaCode}")
    @ResponseBody
    public DataMsg queryAreaByCode(HttpServletRequest request, @PathVariable String areaCode, @ModelAttribute DataMsg dataMsg) throws Exception {
    	List<Map> list = service.queryChildAreaByCode(areaCode);
        dataMsg.setData(list);
        return dataMsg;
    }
    
    
}
