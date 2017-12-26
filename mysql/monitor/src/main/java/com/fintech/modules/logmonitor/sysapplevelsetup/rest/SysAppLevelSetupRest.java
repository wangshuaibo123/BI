package com.fintech.modules.logmonitor.sysapplevelsetup.rest;

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

import com.fintech.modules.logmonitor.sysapplevelsetup.dto.SysAppLevelSetupDTO;
import com.fintech.modules.logmonitor.sysapplevelsetup.service.SysAppLevelSetupService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
/**
 * @classname: SysAppLevelSetupRest
 * @description:定义 错误级别设定表 rest服务
 * @author lei
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysAppLevelSetup")
public class SysAppLevelSetupRest {

	private static Logger logger = LoggerFactory.getLogger(SysAppLevelSetupRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.logmonitor.sysapplevelsetup.service.SysAppLevelSetupService")
    private SysAppLevelSetupService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysAppLevelSetupDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysAppLevelSetupDTO> responseMsg = new ResponseMsg<SysAppLevelSetupDTO>();
		SysAppLevelSetupDTO entity = service.querySysAppLevelSetupByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysAppLevelSetupDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysAppLevelSetupDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysAppLevelSetupDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysAppLevelSetup(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysAppLevelSetupDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysAppLevelSetup(obj);
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
		service.deleteSysAppLevelSetupByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>();
	
		List<SysAppLevelSetupDTO> list = service.searchSysAppLevelSetupByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppLevelSetupDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>();
		List<SysAppLevelSetupDTO> list = service.searchSysAppLevelSetup(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppLevelSetupDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppLevelSetupDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
