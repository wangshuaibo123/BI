package com.jy.modules.logmonitor.sysapplogpriv.rest;

import java.util.List;

import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;

import com.jy.modules.logmonitor.sysapplogpriv.dto.SysAppLogPrivDTO;
import com.jy.modules.logmonitor.sysapplogpriv.service.SysAppLogPrivService;
/**
 * @classname: SysAppLogPrivRest
 * @description:定义 日志访问权限表 rest服务
 * @author Trust_FreeDom
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysAppLogPriv")
public class SysAppLogPrivRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysAppLogPrivRest.class);

	@Autowired
    @Qualifier("com.jy.modules.logmonitor.sysapplogpriv.service.SysAppLogPrivService")
    private SysAppLogPrivService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysAppLogPrivDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysAppLogPrivDTO> responseMsg = new ResponseMsg<SysAppLogPrivDTO>();
		SysAppLogPrivDTO entity = service.querySysAppLogPrivByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysAppLogPrivDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysAppLogPrivDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysAppLogPrivDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysAppLogPriv(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysAppLogPrivDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysAppLogPriv(obj);
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
		service.deleteSysAppLogPrivByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppLogPrivDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>();
	
		List<SysAppLogPrivDTO> list = service.searchSysAppLogPrivByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppLogPrivDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppLogPrivDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>();
		List<SysAppLogPrivDTO> list = service.searchSysAppLogPriv(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppLogPrivDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppLogPrivDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
