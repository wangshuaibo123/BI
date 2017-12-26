package com.fintech.modules.platform.sysorg.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysorg.dto.SysOrgUserDTO;
import com.fintech.modules.platform.sysorg.dto.SysPositionDTO;
import com.fintech.modules.platform.sysorg.dto.SysUserDTO;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.core.message.ResponseStatus;
import com.fintech.platform.restclient.http.RestClient;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;
/**
 * @classname: SysUserController
 * @description: 定义  系统用户表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysUser")
@SuppressWarnings("all")
public class SysUserController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysUserController.class);
	private String jyptAppId = "ptpt"; //rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);//rest服务地址
	
    @Autowired
    private SysUserService service;
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
	 @RequestMapping(value = "/prepareExecute/{operate}") 
     public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException  {
		ModelAndView model = new ModelAndView();
		//String operate = this.getParameterString("operateData");
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/sysorg/sysUser/querySysUser");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/sysorg/sysUser/addSysUser");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysUser/updateSysUser");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysUser/viewSysUser");
        } else if("toManagePage".equals(operate)){//跳转至 管理页面
        	model.setViewName("platform/sysorg/sysUser/manageSysUser");
        } /*else if("toViewRoles".equals(operate)){
        //调整至 system包中
        	String id = this.getParameterString("id");
        	model = this.queryUserRoles(id);
        	model.setViewName("platform/sysorg/sysUser/viewSysUserRoles");
        }*/else if("toSynLdapData".equals(operate)){ //跳转至 同步密码页面
        	model.setViewName("platform/sysorg/sysUser/toSynLdapData");
        }else if("selectSysUserDialog".equals(operate)){ //跳转至 选择框
        	model.addObject("orgId", this.getParameterString("orgId"));
        	model.addObject("roleCode", this.getParameterString("roleCode"));
        	model.setViewName("platform/sysorg/sysUser/selectSysUserDialog");
        }else if("toChangeBaseResource".equals(operate)){
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/sysorg/sysUser/changeBaseResource");
        }
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-15 10:25:49
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysUser")
    @ResponseBody
    public DataMsg queryListSysUser(HttpServletRequest request, SysUserDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	//orgId
        String orgId = this.getParameterString("orgId");
        String roleCode = this.getParameterString("roleCode"); 
        searchParams.put( "orgId",orgId);
        searchParams.put("roleCode", roleCode);
        //是否包含 orgId 下级的user 
        searchParams.put("showLowerUser", this.getParameterString("showLowerUser"));
        
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
		try {
			String url= jyptURL+"/api/platform/SysUserRest/searchByPage/v1";
			ResponseMsg<QueryRespBean<SysUserDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params, new TypeReference<ResponseMsg<QueryRespBean<SysUserDTO>>>(){});
			List<SysUserDTO> list  = responseMsg.getResponseBody().getResult();
			dataMsg.setData(list);
			dataMsg.setTotalRows(responseMsg.getResponseBody().getPageParameter().getTotalCount());
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
        	logger.error("执行方法queryListSysUser异常：", e);
		}
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2014-10-15 10:25:49
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysUser")
    @ResponseBody
    public DataMsg insertSysUser(HttpServletRequest request, SysUserDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	
        	//获取主机构和岗位信息
        	String mainOrgId = request.getParameter("mainOrgId");
        	String positionId = request.getParameter("positionId");
        	SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
        	sysOrgUserDTO.setIsMainOrg("1");
        	sysOrgUserDTO.setOrgId(Long.parseLong(mainOrgId));
        	sysOrgUserDTO.setPositionId(Long.parseLong(positionId));
        	sysOrgUserDTO.setVersion(SysOrgUserDTO.generatorVersion());
        	dto.getSysOrgUserDTOs().add(sysOrgUserDTO);
        	//获取兼职机构，兼职岗位的数组，不为空的兼职机构，岗位添加到“机构用户DTO”中。
        	String [] ptOrgIds = request.getParameterValues("partOrgId");
        	String [] ptPositionIds = request.getParameterValues("partPositionId");
        	// 兼职机构不为空时才添加到“机构用户DTO”中。
            if(ptOrgIds!=null && ptPositionIds!=null){
            	for(int i=0;i<ptOrgIds.length;i++){
        			SysOrgUserDTO partSysOrgUserDTO = new SysOrgUserDTO();
                	partSysOrgUserDTO.setIsMainOrg("0");//兼职
                	partSysOrgUserDTO.setOrgId(Long.parseLong(ptOrgIds[i]));
                	partSysOrgUserDTO.setPositionId(Long.parseLong(ptPositionIds[i]));
                	partSysOrgUserDTO.setVersion(SysOrgUserDTO.generatorVersion());
                	dto.getSysOrgUserDTOs().add(partSysOrgUserDTO);
        	    }
            }
        	dto = (SysUserDTO)super.initDto(dto);
        	//设置版本号
        	dto.setVersion(SysUserDTO.generatorVersion());//TODO
        	//默认有效
        	dto.setValidateState("1");
        	dto.setCreateDate(new Date());
			String url= jyptURL+"/api/platform/SysUserRest/create/v1";
			ResponseMsg<Void> responseMsg = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<Void>>(){});
			dataMsg = super.initDataMsg(dataMsg);
			if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("新增成功");
				dataMsg.setData(this.makeJSONData(dto.getId()));//TODO
			}else{
				dataMsg.setMsg("新增失败");
			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	dataMsg.setMsg("新增失败," + e);
        	logger.error("执行方法insertSysUser异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2014-10-15 10:25:49
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysUser")
    @ResponseBody
    public DataMsg updateSysUser(HttpServletRequest request, SysUserDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	
        	//获取主机构和岗位信息
        	String sysOrgUserId = request.getParameter("sysOrgUserId");
        	String mainOrgId = request.getParameter("mainOrgId");
        	String positionId = request.getParameter("positionId");
        	SysOrgUserDTO sysOrgUserDTO = new SysOrgUserDTO();
        	if(StringUtils.isNotBlank(sysOrgUserId)){
        		sysOrgUserDTO.setId(Long.parseLong(sysOrgUserId));
        	}
        	sysOrgUserDTO.setIsMainOrg("1");;
        	sysOrgUserDTO.setOrgId(Long.parseLong(mainOrgId));
        	sysOrgUserDTO.setPositionId(Long.parseLong(positionId));
        	sysOrgUserDTO.setVersion(SysOrgUserDTO.generatorVersion());
        	dto.getSysOrgUserDTOs().add(sysOrgUserDTO);
        	//获取兼职机构，兼职岗位,ID的数组，不为空的兼职机构，岗位添加到“机构用户DTO”中。
        	String [] ptOrgIds = request.getParameterValues("partOrgId");
        	String [] ptPositionIds = request.getParameterValues("partPositionId");
        	String [] sysOrgUserIds  = request.getParameterValues("orgUserId");
        	// 兼职机构不为空时才添加到“机构用户DTO”中。
            if(ptOrgIds!=null && ptPositionIds!=null){
            	for(int i=0;i<ptOrgIds.length;i++){
        			SysOrgUserDTO partSysOrgUserDTO = new SysOrgUserDTO();
            		if(StringUtils.isNotBlank(sysOrgUserIds[i])){
            			partSysOrgUserDTO.setId(Long.parseLong(sysOrgUserIds[i]));
            		}
                	partSysOrgUserDTO.setIsMainOrg("0");//兼职
                	partSysOrgUserDTO.setOrgId(Long.parseLong(ptOrgIds[i]));
                	partSysOrgUserDTO.setPositionId(Long.parseLong(ptPositionIds[i]));
                	partSysOrgUserDTO.setVersion(SysOrgUserDTO.generatorVersion());
                	dto.getSysOrgUserDTOs().add(partSysOrgUserDTO);
        	    }
            }
        	dto = (SysUserDTO)super.initDto(dto);
        	//设置版本号
        	dto.setVersion(SysPositionDTO.generatorVersion());
			String url= jyptURL+"/api/platform/SysUserRest/update/v1";
			ResponseMsg<Void> responseMsg = RestClient.doPost(jyptAppId, url, dto, new TypeReference<ResponseMsg<Void>>(){});
			if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("保存成功");
			}else{
				dataMsg.setMsg("保存失败");
			}
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysUser异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2014-10-15 10:25:49
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysUser")
    @ResponseBody
    public DataMsg deleteSysUser(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			String url= jyptURL+"/api/platform/SysUserRest/delete/v1/"+ids;
			ResponseMsg<Void> responseMsg = RestClient.doGet(jyptAppId, url, new TypeReference<ResponseMsg<Void>>(){});
			if(ResponseStatus.HTTP_OK.equals(responseMsg.getRetCode())){
				dataMsg.setMsg("删除成功");
			}else{
				dataMsg.setMsg("删除失败");
			}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-10-15 10:25:49
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
			String url= jyptURL+"/api/platform/SysUserRest/getUser/v1/"+id;
			ResponseMsg<SysUserDTO> responseMsg = RestClient.doGet(jyptAppId, url, new TypeReference<ResponseMsg<SysUserDTO>>(){});
			SysUserDTO dto = responseMsg.getResponseBody();
			List<Map<String,Object>> result = service.getUserRoleByTargetId(id);
			StringBuffer sb = new StringBuffer();
			for(Map<String,Object> map :result){
				sb.append(map.get("ID")).append(",");
			}
			String roles = "-1";
	        if(sb.length() >0){
	        	roles = sb.substring(0,sb.length()-1);
	        }
			//将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
            model.addObject("roleIds", roles);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    /**
     * @author
     * @description:通过主键查询 其角色明细信息
     * @date 2014-10-15 10:25:49
     * @param id
     * @return
     * @throws AbaboonException 
     */
    /*private ModelAndView queryUserRoles(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("userId", id);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        try{
			SysUserDTO dto = service.querySysUserByPrimaryKey(id);
            model.addObject("dto", dto);
        	
			String url = jyptURL + "/api/sysRole/getRoleByUserId/v1";
			ResponseMsg<QueryRespBean<com.pt.modules.platform.sysauth.dto.SysRoleDTO>> responseMsg = RestClient
					.doPost(jyptAppId,url,params,new TypeReference<ResponseMsg<QueryRespBean<com.pt.modules.platform.sysauth.dto.SysRoleDTO>>>() {});
			List<com.pt.modules.platform.sysauth.dto.SysRoleDTO> roles = responseMsg.getResponseBody().getResult();
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("roles", roles);
        }catch(Exception e){
        	throw new AbaboonException("执行queryUserRoles异常：",e);
        }
        return model;
    }*/
}
