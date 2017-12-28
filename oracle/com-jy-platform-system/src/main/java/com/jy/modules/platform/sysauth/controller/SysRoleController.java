package com.jy.modules.platform.sysauth.controller;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.jy.modules.platform.sysauth.dto.SysRoleDTO;
import com.jy.modules.platform.sysauth.dto.SysRoleGroupRoleDTO;
import com.jy.modules.platform.sysauth.dto.SysRoleUserDTO;
import com.jy.modules.platform.sysauth.service.SysRoleGroupRoleService;
import com.jy.modules.platform.sysauth.service.SysRoleService;
import com.jy.modules.platform.sysauth.service.SysRoleUserService;
import com.jy.modules.platform.sysorg.dto.SysUserDTO;
import com.jy.modules.platform.sysorg.service.SysUserService;
import com.jy.platform.api.sysauth.SysRoleAPI;
import com.jy.platform.core.common.BaseDTO;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.core.message.PageParameter;
import com.jy.platform.core.message.PageUtil;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restclient.http.RestClient;
import com.jy.platform.restclient.http.RestService;
import com.jy.platform.restservice.exception.AbaboonException;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: SysRoleController
 * @description: 定义 角色表 控制层
 * @author: chen_gang
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);
	private String jyptAppId = "jypt"; // rest服务appId
	private String jyptURL = RestService.getServiceUrl(jyptAppId);// rest服务地址
	@Autowired
	@Qualifier("com.jy.modules.platform.sysauth.service.SysRoleService")
	private SysRoleService service;

	@Autowired
	@Qualifier("com.jy.modules.platform.sysauth.service.SysRoleUserService")
	private SysRoleUserService sysRoleUserService;
	
    @Autowired
    @Qualifier("com.jy.modules.platform.sysrolegrouprole.service.SysRoleGroupRoleService")
    SysRoleGroupRoleService sysRoleGroupRoleService;
	
	@Autowired
	private SysRoleAPI sysRoleAPI;
	
	@Autowired
	@Qualifier("com.jy.modules.platform.sysorg.service.SysUserService")
    private SysUserService userBiz;
	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		// String operate = this.getParameterString("operateData");

		if ("toQueryMain".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/sysauth/sysRole/sysRoleMain");
		} else if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/sysauth/sysRole/querySysRole");
		} else if ("toAdd".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/sysauth/sysRole/addSysRole");
		} else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysauth/sysRole/updateSysRole");
		} else if ("toView".equals(operate)) {// 跳转至 查看页面
			String id = this.getParameterString("id");
			model = this.queryOneDTO(id);
			model.setViewName("platform/sysauth/sysRole/viewSysRole");
		} else if ("treeRole".equals(operate)) {
			model.setViewName("platform/sysauth/sysRole/treeSysRole");
		} else if ("toAddRole".equals(operate)) { // 跳转至 新增页面
			model.setViewName("platform/sysauth/sysRoleUser/addSysRoleUser");
		}else if("toViewRoles".equals(operate)){
        	String id = this.getParameterString("id");
        	model = this.queryUserRoles(id);
        	model.setViewName("platform/sysorg/sysUser/viewSysUserRoles");
        }
		
		return model;
	}

	/**
	 * @author chen_gang
	 * @description:查询分页列表
	 * @date 2014-10-15 10:24:59
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryListSysRole")
	@ResponseBody
	public DataMsg queryListSysRole(HttpServletRequest request, SysRoleDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {

		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
		List<SysRoleDTO> list = service.searchSysRoleByPaging(params.getSearchParams());
		dataMsg.setData(list);
		dataMsg.setTotalRows(pageInfo.getTotalCount());
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:新增
	 * @date 2014-10-15 10:24:59
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/insertSysRole")
	@ResponseBody
	public DataMsg insertSysRole(HttpServletRequest request, SysRoleDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysRoleDTO) super.initDto(dto);
			Map<String, Object> searchParams = new HashMap<String, Object>();
			SysRoleDTO paramsDto = new SysRoleDTO();
			paramsDto.setRoleCode(dto.getRoleCode());
			searchParams.put("dto", paramsDto);
			List<SysRoleDTO> searchSysRole = service.searchSysRole(searchParams);
			if (searchSysRole.size() <= 0) {
				long roleId = service.insertSysRole(dto);
				
				String groupId = request.getParameter("groupId");
				SysRoleGroupRoleDTO sysRoleGroupRoleDTO = new SysRoleGroupRoleDTO();
				sysRoleGroupRoleDTO.setAppId(1l);
				sysRoleGroupRoleDTO.setValidateState("1");
				sysRoleGroupRoleDTO.setRoleId(roleId);
				//添加新的 
				if(groupId!=null && !"".equals(groupId)){//改成了什么
					sysRoleGroupRoleDTO.setRoleGroupId(Long.parseLong(groupId));
					sysRoleGroupRoleService.insertSysRoleGroupRole(sysRoleGroupRoleDTO);
				}
				
				dataMsg = super.initDataMsg(dataMsg);
				dataMsg.setMsg("新增成功");
				dataMsg.setData(this.makeJSONData(dto.getId()));
			} else {
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
				dataMsg.setMsg("角色编码已存在");
			}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法insertSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:编辑
	 * @date 2014-10-15 10:24:59
	 * @param request
	 * @param dto
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/updateSysRole")
	@ResponseBody
	public DataMsg updateSysRole(HttpServletRequest request, SysRoleDTO dto, @ModelAttribute DataMsg dataMsg) {
		try {
			dto = (SysRoleDTO) super.initDto(dto);
			Map<String, Object> searchParams = new HashMap<String, Object>();
			SysRoleDTO sysRoleDTO = new SysRoleDTO();
			sysRoleDTO.setRoleCode(dto.getRoleCode());
			searchParams.put("dto", sysRoleDTO);
			List<SysRoleDTO> searchSysRole = service.searchSysRole(searchParams);
			Long oldId = 0L;
			if(searchSysRole.size()>0){
				oldId = searchSysRole.get(0).getId();
			}
			if (searchSysRole.size() <= 0 ||oldId.equals(dto.getId())) {
				
				String groupId = request.getParameter("groupId");
				
				Map<String, Object> params = new HashMap<String, Object>();
				SysRoleGroupRoleDTO sysRoleGroupRoleDTO = new SysRoleGroupRoleDTO();
				sysRoleGroupRoleDTO.setRoleId(dto.getId());
				params.put("dto", sysRoleGroupRoleDTO);
				List<SysRoleGroupRoleDTO> sysRoleGroupRoleDTOList = sysRoleGroupRoleService.searchSysRoleGroupRole(params);
				boolean hasGroup = sysRoleGroupRoleDTOList!=null && sysRoleGroupRoleDTOList.size() >0 ;
				boolean changegroup = false;
				String oldgroupId = null;
				SysRoleGroupRoleDTO oldsysRoleGroupRoleDTO = new SysRoleGroupRoleDTO();
				oldsysRoleGroupRoleDTO.setAppId(1l);
				oldsysRoleGroupRoleDTO.setValidateState("1");
				oldsysRoleGroupRoleDTO.setRoleId(dto.getId());
				
				if( groupId!=null && hasGroup ){
					oldsysRoleGroupRoleDTO =sysRoleGroupRoleDTOList.get(0);
					oldgroupId = oldsysRoleGroupRoleDTO.getRoleGroupId().toString();
				}
				changegroup = !groupId.equals(oldgroupId);
				if(changegroup){//如果group发生了变化
					//先删除旧的
					if(oldgroupId!=null){//以前有group
						sysRoleGroupRoleService.deleteSysRoleGroupRoleByID(oldsysRoleGroupRoleDTO.getId().toString());
					}
					//再添加新的 TODO  可以只改不加
					if(groupId!=null && !"".equals(groupId)){//改成了什么
						oldsysRoleGroupRoleDTO.setRoleGroupId(Long.parseLong(groupId));
						sysRoleGroupRoleService.insertSysRoleGroupRole(oldsysRoleGroupRoleDTO);
					}
				}
				service.updateSysRole(dto);
				dataMsg = super.initDataMsg(dataMsg);
				dataMsg.setMsg("修改成功");
				dataMsg.setData(this.makeJSONData(dto.getId()));
			}
			else{
				dataMsg.setMsg("编码已存在");
				dataMsg.setStatus(DataMsg.STATUS_FAILED);
				dataMsg.setData(this.makeJSONData(dto.getId()));
			}
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法updateSysResource异常：", e);
		}
		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:删除
	 * @date 2014-10-15 10:24:59
	 * @param request
	 * @param pageData
	 * @return
	 * @throws
	 */
	@RequestMapping(value = "/deleteSysRole")
	@ResponseBody
	public DataMsg deleteSysRole(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

		BaseDTO dto = super.initDto(null);
		String ids = (String) this.getParameter("ids");// 格式: 1,2,3
		dataMsg = super.initDataMsg(dataMsg);
		try {
			// 删除权限用户关系表
			SysRoleUserDTO roleUserDTO = new SysRoleUserDTO();
			String[] arrIds = ids.split(",");
			roleUserDTO.setRoleId(Long.valueOf(arrIds[0]));
			sysRoleUserService.deleteSysRoleUserByRoleId(roleUserDTO);
			service.deleteSysRoleByPrimaryKey(dto, ids);
			dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}

		return dataMsg;
	}

	/**
	 * @author chen_gang
	 * @description:通过主键查询 其明细信息
	 * @date 2014-10-15 10:24:59
	 * @param id
	 * @return
	 */
	private ModelAndView queryOneDTO(String id) throws AbaboonException {
		ModelAndView model = new ModelAndView();
		try {
			SysRoleDTO dto = service.querySysRoleByPrimaryKey(id);
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("dto", dto);
		} catch (Exception e) {
			throw new AbaboonException("执行queryOneDTO异常：", e);
		}
		return model;
	}

	/**
	 * 
	 * @param request
	 * @param dto
	 * @param dataMsg
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryTreeSysRole")
	@ResponseBody
	public List<Map<String, String>> queryTreeSysRole(HttpServletRequest request, SysRoleDTO dto, @ModelAttribute DataMsg dataMsg)
			throws Exception {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String url = jyptURL + "/api/sysRole/search/v1";
		ResponseMsg<QueryRespBean<SysRoleDTO>> responseMsg = RestClient.doPost(jyptAppId, url, params,
				new TypeReference<ResponseMsg<QueryRespBean<SysRoleDTO>>>() {
				});
		List<SysRoleDTO> list = responseMsg.getResponseBody().getResult();
		return treeData(list);// 组织树的数据
	}

	private List<Map<String, String>> treeData(List<SysRoleDTO> data) {
		if (data != null && data.size() > 0) {
			List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
			HashMap<String, String> root = new HashMap<String, String>();
			root.put("ID", "0");
			root.put("NAME", "根节点");
			maps.add(root);
			for (SysRoleDTO sysRole : data) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("ID", sysRole.getId().toString());
				map.put("NAME", sysRole.getRoleName());
				map.put("PID", "0");
				maps.add(map);
			}
			return maps;
		} else {
			return null;
		}
	}

	private Map<String, String> treeNode(SysRoleDTO sysRole) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ID", sysRole.getId().toString());
		map.put("NAME", sysRole.getRoleName());
		map.put("PID", "0");
		return map;
	}
	/**
	 * @description:通过用户主键ID 获取其拥有的角色信息
	 * @author chengang
	 * @date: 2016年7月6日 上午9:43:52
	 * @param id
	 * @return
	 * @throws AbaboonException
	 */
	private ModelAndView queryUserRoles(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("userId", id);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        try{
			SysUserDTO dto = userBiz.querySysUserByPrimaryKey(id);
            model.addObject("dto", dto);
        	
			String url = jyptURL + "/api/sysRole/getRoleByUserId/v1";
			ResponseMsg<QueryRespBean<com.jy.modules.platform.sysauth.dto.SysRoleDTO>> responseMsg = RestClient
					.doPost(jyptAppId,url,params,new TypeReference<ResponseMsg<QueryRespBean<com.jy.modules.platform.sysauth.dto.SysRoleDTO>>>() {});
			List<com.jy.modules.platform.sysauth.dto.SysRoleDTO> roles = responseMsg.getResponseBody().getResult();
			// 将信息放入 model 以便于传至页面 使用
			model.addObject("roles", roles);
        }catch(Exception e){
        	throw new AbaboonException("执行queryUserRoles异常：",e);
        }
        return model;
    }

}
