package com.fintech.modules.platform.sysconfig.rest;

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

import com.fintech.modules.platform.sysconfig.dto.SysConfigDTO;
import com.fintech.modules.platform.sysconfig.service.SysConfigService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysConfigRest
 * @description:定义 系统配置表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysconfig/SysConfigRest")
public class SysConfigRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysConfigRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.sysconfig.service.SysConfigService")
    private SysConfigService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysConfigDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysConfigDTO> responseMsg = new ResponseMsg<SysConfigDTO>();
		SysConfigDTO entity = service.querySysConfigByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysConfigDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysConfigDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	
	/**
	 * 唯一性验证
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryConfigCodeIsOk/v1/{code}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<String>> queryConfigCodeIsOk(@PathVariable("code") String code) throws Exception{
		ResponseMsg<String> responseMsg = new ResponseMsg<String>();
		String entity = service.queryConfigCodeIsOk(code);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<String>> responseEntity = new ResponseEntity<ResponseMsg<String>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysConfigDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysConfig(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysConfigDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysConfig(obj);
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
		service.deleteSysConfigByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysConfigDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysConfigDTO>>();
	
		List<SysConfigDTO> list = service.searchSysConfigByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysConfigDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 按条件查询并分页(不记录缓存)
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchByPageNoCache/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>> searchByPageNoCache(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysConfigDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysConfigDTO>>();
	
		List<SysConfigDTO> list = service.sysConfigByPagingNoCache(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysConfigDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	
	
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysConfigDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysConfigDTO>>();
		List<SysConfigDTO> list = service.searchSysConfig(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysConfigDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysConfigDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
