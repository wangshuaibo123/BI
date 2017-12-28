package com.jy.modules.platform.sysauth.rest;

import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.jy.modules.platform.sysauth.dto.SysRoleGroupRoleDTO;
import com.jy.modules.platform.sysauth.service.SysRoleGroupRoleService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysRoleGroupRoleRest
 * @description:定义 角色组角色中间表 rest服务
 * @author yuchengyang-pc
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysRoleGroupRole")
public class SysRoleGroupRoleRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysRoleGroupRoleRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.sysrolegrouprole.service.SysRoleGroupRoleService")
    private SysRoleGroupRoleService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysRoleGroupRoleDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysRoleGroupRoleDTO> responseMsg = new ResponseMsg<SysRoleGroupRoleDTO>();
		SysRoleGroupRoleDTO entity = service.querySysRoleGroupRoleByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysRoleGroupRoleDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysRoleGroupRoleDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysRoleGroupRoleDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysRoleGroupRole(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	

	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysRoleGroupRoleDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysRoleGroupRole(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 删除一个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> delete(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		service.deleteSysRoleGroupRoleByPrimaryKey(null,ID);
		ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 按条件查询并分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchByPage/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>();
	
		List<SysRoleGroupRoleDTO> list = service.searchSysRoleGroupRoleByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysRoleGroupRoleDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>();
		List<SysRoleGroupRoleDTO> list = service.searchSysRoleGroupRole(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysRoleGroupRoleDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysRoleGroupRoleDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
