package com.jy.modules.logmonitor.sysappmanagecontactway.rest;

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

import com.jy.modules.logmonitor.sysappmanagecontactway.dto.SysAppManageContactWayDTO;
import com.jy.modules.logmonitor.sysappmanagecontactway.service.SysAppManageContactWayService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
/**
 * @classname: SysAppManageContactWayRest
 * @description:定义 系统管理者联系方式 rest服务
 * @author lei
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysAppManageContactWay")
public class SysAppManageContactWayRest {

	private static Logger logger = LoggerFactory.getLogger(SysAppManageContactWayRest.class);

	@Autowired
    @Qualifier("com.jy.modules.logmonitor.sysappmanagecontactway.service.SysAppManageContactWayService")
    private SysAppManageContactWayService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysAppManageContactWayDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysAppManageContactWayDTO> responseMsg = new ResponseMsg<SysAppManageContactWayDTO>();
		SysAppManageContactWayDTO entity = service.querySysAppManageContactWayByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysAppManageContactWayDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysAppManageContactWayDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysAppManageContactWayDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysAppManageContactWay(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysAppManageContactWayDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysAppManageContactWay(obj);
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
		service.deleteSysAppManageContactWayByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>();
	
		List<SysAppManageContactWayDTO> list = service.searchSysAppManageContactWayByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppManageContactWayDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>();
		List<SysAppManageContactWayDTO> list = service.searchSysAppManageContactWay(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppManageContactWayDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppManageContactWayDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
