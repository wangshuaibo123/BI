package com.fintech.modules.platform.sysorg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.platform.sysorg.dto.SysSynDTO;
import com.fintech.modules.platform.sysorg.service.SynClientService;
import com.fintech.modules.platform.sysorg.service.SysOrgService;
import com.fintech.modules.platform.sysorg.service.SysOrgSynService;
import com.fintech.modules.platform.sysorg.service.SysOrgUserService;
import com.fintech.modules.platform.sysorg.service.SysOrgUserSynService;
import com.fintech.modules.platform.sysorg.service.SysPositionService;
import com.fintech.modules.platform.sysorg.service.SysPositionSynService;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.modules.platform.sysorg.service.SysUserSynService;
import com.fintech.platform.api.org.OrgAPI;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

@Controller
@Scope("prototype")
@RequestMapping("/synClient")
public class SysClientController extends BaseController{
	
    private static final Logger logger =  LoggerFactory.getLogger(SysClientController.class);
    
    @Autowired
    SynClientService synClientService;
    
    @Autowired 
    SysUserService sysUserService;
    
    @Autowired
    SysUserSynService sysUserSynService;
    
    @Autowired
    SysOrgSynService sysOrgSynService;
    
    @Autowired
    SysOrgService sysOrgService;
    
    @Autowired
    SysPositionService sysPositionService;
    
    @Autowired
    SysPositionSynService sysPositionSynService;
    
    @Autowired
    SysOrgUserService  sysOrgUserService;
    
    @Autowired
    SysOrgUserSynService sysOrgUserSynService;
    
    @Autowired
    OrgAPI orgAPI;
    
    
	/**Description: 跳转控制方法
	 * Create Date: 2015年1月26日下午7:27:23<br/>
	 * Author     : cyy <br/>
	 * Modify Date: <br/>
	 * Modify By  : <br/>
	 * @param operate
	 * @return
	 * @throws AbaboonException
	 */
	@RequestMapping(value = "/prepareExecute/{operate}") 
	public ModelAndView execute(@PathVariable("operate") String operate) throws Exception  {
	ModelAndView model = new ModelAndView();
		if("toSynManage".equals(operate)){//跳转至 查询页面
			model.setViewName("platform/sysorg/sysclient/clientManage");
		} else if("toSynDetail".equals(operate)){//跳转至 查询页面
			//批次号
			String version  = this.getParameterString("version");
			//用户
			model.addObject("sysUserChangeDTOs", sysUserSynService.getChangeData(version));
			//机构
			model.addObject("sysOrgChangeDTO", sysOrgSynService.getChangeData(version));
			//岗位
			model.addObject("sysPositionChangeDTO", sysPositionSynService.getChangeData(version));
			//归属机构以及任职
			model.addObject( "sysOrgUserChangeDTOs",sysOrgUserSynService.getChangeData(version));

			model.setViewName("platform/sysorg/sysclient/clientDetail");
		}
		return model;
	}
	
    /**Description: 查询分页接口
     * Create Date: 2015年1月26日下午7:27:19<br/>
     * Author     : cyy <br/>
     * Modify Date: <br/>
     * Modify By  : <br/>
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysSyn")
    @ResponseBody
    public DataMsg queryListSysSyn(HttpServletRequest request, SysSynDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysSynDTO> list = synClientService.searchSysSynByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    
    @RequestMapping(value = "/udpateSysDataSyn")
    @ResponseBody
    public DataMsg udpateSysDataSyn(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
       	 	String versions = (String) this.getParameter("versions");//格式: 1,2,3
       	 	dataMsg = super.initDataMsg(dataMsg);
    		try {
    			synClientService.updateSysDataSynAndCheck(versions);
    			dataMsg.setMsg("更新成功");
    		} catch (Exception e) {
    			dataMsg.failed(e.getMessage());
    			logger.error("执行方法udpateSysDataSyn异常：", e);
    		}
            return dataMsg;
    }
}
