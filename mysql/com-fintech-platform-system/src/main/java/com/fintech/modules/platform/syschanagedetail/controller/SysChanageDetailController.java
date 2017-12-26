package com.fintech.modules.platform.syschanagedetail.controller;

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

import com.fintech.modules.platform.syschanagedetail.dto.SysChanageDetailDTO;
import com.fintech.modules.platform.syschanagedetail.service.SysChanageDetailService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysChanageDetailController
 * @description: 定义  变更信息明细表 控制层
 * @author:  DELL
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysChanageDetail")
public class SysChanageDetailController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysChanageDetailController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.syschanagedetail.service.SysChanageDetailService")
    private SysChanageDetailService service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/syschanagedetail/querySysChanageDetail");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/syschanagedetail/addSysChanageDetail");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/syschanagedetail/updateSysChanageDetail");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/syschanagedetail/viewSysChanageDetail");
        }
        
        return model;
    }
    
    /**
     * @author DELL
     * @description:查询分页列表
     * @date 2016-09-09 13:31:52
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysChanageDetail")
    @ResponseBody
    public DataMsg queryListSysChanageDetail(HttpServletRequest request, SysChanageDetailDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysChanageDetailDTO> list = service.searchSysChanageDetailByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author DELL
     * @description:新增
     * @date 2016-09-09 13:31:52
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysChanageDetail")
    @ResponseBody
    public DataMsg insertSysChanageDetail(HttpServletRequest request, SysChanageDetailDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysChanageDetailDTO)super.initDto(dto);

            service.insertSysChanageDetail(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysChanageDetail异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author DELL
     * @description:编辑
     * @date 2016-09-09 13:31:52
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysChanageDetail")
    @ResponseBody
    public DataMsg updateSysChanageDetail(HttpServletRequest request, SysChanageDetailDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (SysChanageDetailDTO)super.initDto(dto);
           
            service.updateSysChanageDetail(dto);
            
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysChanageDetail异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author DELL
     * @description:删除
     * @date 2016-09-09 13:31:52
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysChanageDetail")
    @ResponseBody
    public DataMsg deleteSysChanageDetail(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysChanageDetailByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysChanageDetail异常：", e);

		}
        
        return dataMsg;
    }
    
    /**
     * 获取修改历史
     * @param request
     * @return
     */
    @RequestMapping(value = "/getChangeList")
    @ResponseBody
    public DataMsg getChangeList(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	//DataMsg dataMsg = new DataMsg();
    	try {
    		String bizTabId = this.getParameterString("bizTabId");
    		Map<String,Object> searchParams = new HashMap<String,Object>();
        	SysChanageDetailDTO queryChanageDetailDTO = new SysChanageDetailDTO();        	
        	queryChanageDetailDTO.setBatNo(bizTabId);
        	searchParams.put("dto", queryChanageDetailDTO);
			List<SysChanageDetailDTO> changeList = service.searchSysChanageDetail(searchParams);
			if(changeList != null && changeList.size() >0){
				for(SysChanageDetailDTO sysChanageDetailDTO:changeList){
					String bizTableColum = sysChanageDetailDTO.getBizTableColum();
					StringBuffer  bizTableColumNew = new StringBuffer();
					boolean isUpperCase = false;
					for(int i=0;i<bizTableColum.length();i++){
						char c = bizTableColum.charAt(i);
						if(isUpperCase){
							bizTableColumNew.append(Character.toUpperCase(c));
							isUpperCase = false;
						}else{
							if(c=='_'){
								isUpperCase = true;
							}else{
								bizTableColumNew.append(Character.toLowerCase(c));
							}
						}
						
					}
					sysChanageDetailDTO.setBizTableColum(bizTableColumNew.toString());
				}
				/*JSONArray jsonArray = JSONArray.fromObject(changeList);
				String jsonStr = jsonArray.toString();				
				System.out.println("==========="+jsonStr);*/
				dataMsg.setData(changeList);
			}
		} catch (Exception e) {
			dataMsg.failed("获取修改历史信息失败，"+e.getMessage());
            logger.error("获取修改历史信息失败：", e);
		}
    	return dataMsg;
    }
    /**
     * @author DELL
     * @description:通过主键查询 其明细信息
     * @date 2016-09-09 13:31:52
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	SysChanageDetailDTO dto = service.querySysChanageDetailByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
