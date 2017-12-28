package com.jy.modules.logmonitor.sysappftpinfo.rest;

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

import com.jy.modules.logmonitor.sysappftpinfo.dto.SysAppFtpInfoDTO;
import com.jy.modules.logmonitor.sysappftpinfo.service.SysAppFtpInfoService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
/**
 * @classname: SysAppFtpInfoRest
 * @description:定义 业务系统节点FTP配置表 rest服务
 * @author lei
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysAppFtpInfo")
public class SysAppFtpInfoRest {

	private static Logger logger = LoggerFactory.getLogger(SysAppFtpInfoRest.class);

	@Autowired
    @Qualifier("com.jy.modules.logmonitor.sysappftpinfo.service.SysAppFtpInfoService")
    private SysAppFtpInfoService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysAppFtpInfoDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysAppFtpInfoDTO> responseMsg = new ResponseMsg<SysAppFtpInfoDTO>();
		SysAppFtpInfoDTO entity = service.querySysAppFtpInfoByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysAppFtpInfoDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysAppFtpInfoDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysAppFtpInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysAppFtpInfo(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysAppFtpInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysAppFtpInfo(obj);
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
		service.deleteSysAppFtpInfoByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>();
	
		List<SysAppFtpInfoDTO> list = service.searchSysAppFtpInfoByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppFtpInfoDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>();
		List<SysAppFtpInfoDTO> list = service.searchSysAppFtpInfo(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppFtpInfoDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppFtpInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
