package com.fintech.modules.logmonitor.sysapperrorinfo.rest;

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

import com.fintech.modules.logmonitor.sysapperrorinfo.dto.SysAppErrorInfoDTO;
import com.fintech.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
/**
 * @classname: SysAppErrorInfoRest
 * @description:定义 业务系统节点错误日志 rest服务
 * @author lei
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysAppErrorInfo")
public class SysAppErrorInfoRest {

	private static Logger logger = LoggerFactory.getLogger(SysAppErrorInfoRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.logmonitor.sysapperrorinfo.service.SysAppErrorInfoService")
    private SysAppErrorInfoService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysAppErrorInfoDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysAppErrorInfoDTO> responseMsg = new ResponseMsg<SysAppErrorInfoDTO>();
		SysAppErrorInfoDTO entity = service.querySysAppErrorInfoByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysAppErrorInfoDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysAppErrorInfoDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	


	/**
	 * 修改一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/update/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysAppErrorInfoDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysAppErrorInfo(obj);
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
		service.deleteSysAppErrorInfoByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>();
	
		List<SysAppErrorInfoDTO> list = service.searchSysAppErrorInfoByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppErrorInfoDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>();
		List<SysAppErrorInfoDTO> list = service.searchSysAppErrorInfo(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysAppErrorInfoDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysAppErrorInfoDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
