package com.jy.modules.platform.bizauth.vmdatapriv.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.jy.modules.platform.bizauth.vmdatapriv.dto.VmdataPrivDTO;
import com.jy.modules.platform.bizauth.vmdatapriv.service.VmdataPrivService;
import com.jy.platform.api.org.OrgAPI;
import com.jy.platform.api.org.UserInfo;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: VmdataPrivController
 * @description: 定义  映射表 控制层
 * @author:  chen_gang
 */
@Controller
@Scope("prototype")
@RequestMapping("/vmdataPriv")
public class VmdataPrivController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(VmdataPrivController.class);

    @Autowired
    @Qualifier("com.jy.modules.platform.bizauth.vmdatapriv.service.VmdataPrivService")
    private VmdataPrivService service;
    /*@Autowired
    private SessionAPI sessionAPI;*/
    @Autowired
    private OrgAPI orgAPI;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/bizauth/vmdatapriv/queryVmdataPriv");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/bizauth/vmdatapriv/addVmdataPriv");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/bizauth/vmdatapriv/updateVmdataPriv");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/bizauth/vmdatapriv/viewVmdataPriv");
        }
        
        return model;
    }
    
    /**
     * @author chen_gang
     * @description:查询分页列表
     * @date 2015-01-16 17:14:46
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListVmdataPriv")
    @ResponseBody
    public DataMsg queryListVmdataPriv(HttpServletRequest request, VmdataPrivDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        String vmTable = request.getParameter("vmTabName");//查询
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
    	searchParams.put("vmtableName", vmTable);//"CS"+ "_" + "VMDATA_PRIV"
    	//searchParams.put("vmtableName", "LC"+ "_" + "VMDATA_PRIV");
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<VmdataPrivDTO> list = service.searchVmdataPrivByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author chen_gang
     * @description:新增
     * @date 2015-01-16 17:14:46
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertVmdataPriv")
    @ResponseBody
    public DataMsg insertVmdataPriv(HttpServletRequest request, VmdataPrivDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (VmdataPrivDTO)super.initDto(dto);

            service.insertVmdataPriv(dto);
            
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
     * @author chen_gang
     * @description:编辑
     * @date 2015-01-16 17:14:46
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateVmdataPriv")
    @ResponseBody
    public DataMsg updateVmdataPriv(HttpServletRequest request, VmdataPrivDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (VmdataPrivDTO)super.initDto(dto);
           
            service.updateVmdataPriv(dto);
            
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
     * @author chen_gang
     * @description:删除
     * @date 2015-01-16 17:14:46
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteVmdataPriv")
    @ResponseBody
    public DataMsg deleteVmdataPriv(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteVmdataPrivByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author chen_gang
     * @description: 业务虚拟树快速调岗
     * @date 2015年11月9日 下午5:10:59
     * @param request
     * @param dataMsg
     * @return
     */
    @RequestMapping(value = "/fastChangeOrg")
    @ResponseBody
    public DataMsg fastChangeOrg(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	dataMsg = super.initDataMsg(dataMsg);
    	try {
	   	 	String orgType = this.getParameterString("orgType");//虚拟树代码如LOAN
	   	 	String username = this.getParameterString("username");
	   	 	String neworgId = this.getParameterString("neworgId");
	   	 	String oldorgId = this.getParameterString("oldorgId");//暂时不使用
	   	 	//校验 username是否真实存在
	   	 	UserInfo userInfo = orgAPI.getUserInfoByLoginName(username);
	   	 	if(userInfo == null || !StringUtils.isNotEmpty(userInfo.getLoginName())){
	   	 		throw new Exception("用户不存在，请核实！");
	   	 	}else{
	   	 		oldorgId = userInfo.getOrgId().toString();//用户当前归属机构
	   	 	}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("orgType", orgType);
			param.put("username", username);
			param.put("neworgId", neworgId);
			param.put("oldorgId", oldorgId);
			
			service.fastChangeOrg(param);
			
			dataMsg.setMsg("操作成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author chen_gang
     * @description:通过主键查询 其明细信息
     * @date 2015-01-16 17:14:46
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	VmdataPrivDTO dto = service.queryVmdataPrivByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
}
