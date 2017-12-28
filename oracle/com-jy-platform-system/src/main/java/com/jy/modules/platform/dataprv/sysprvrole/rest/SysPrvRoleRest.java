package com.jy.modules.platform.dataprv.sysprvrole.rest;

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

import com.jy.modules.platform.dataprv.sysprvrole.dto.SysPrvRoleDTO;
import com.jy.modules.platform.dataprv.sysprvrole.service.SysPrvRoleService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysPrvRoleRest
 * @description:定义 数据权限角色定义 rest服务
 * @author wangxz
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/dataprv/sysPrvRole")
public class SysPrvRoleRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysPrvRoleRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.dataprv.sysprvrole.service.SysPrvRoleService")
    private SysPrvRoleService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysPrvRoleDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysPrvRoleDTO> responseMsg = new ResponseMsg<SysPrvRoleDTO>();
		SysPrvRoleDTO entity = service.querySysPrvRoleByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysPrvRoleDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysPrvRoleDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysPrvRoleDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysPrvRole(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysPrvRoleDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysPrvRole(obj);
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
		service.deleteSysPrvRoleByID(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPrvRoleDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPrvRoleDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPrvRoleDTO>>();
	
		List<SysPrvRoleDTO> list = service.searchSysPrvRoleByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPrvRoleDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysPrvRoleDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPrvRoleDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPrvRoleDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPrvRoleDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPrvRoleDTO>>();
		List<SysPrvRoleDTO> list = service.searchSysPrvRole(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPrvRoleDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysPrvRoleDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPrvRoleDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
