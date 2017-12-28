package com.jy.modules.platform.sysindustry.rest;

import java.util.List;
import java.util.Map;

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

import com.jy.modules.platform.sysindustry.dto.SysIndustryDTO;
import com.jy.modules.platform.sysindustry.service.SysIndustryService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysIndustryRest
 * @description:定义 sys_industry rest服务
 * @author lin
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysIndustry")
public class SysIndustryRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysIndustryRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.sysindustry.service.SysIndustryService")
    private SysIndustryService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysIndustryDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysIndustryDTO> responseMsg = new ResponseMsg<SysIndustryDTO>();
		SysIndustryDTO entity = service.querySysIndustryByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysIndustryDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysIndustryDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysIndustryDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysIndustry(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysIndustryDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysIndustry(obj);
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
		service.deleteSysIndustryByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysIndustryDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysIndustryDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysIndustryDTO>>();
	
		List<SysIndustryDTO> list = service.searchSysIndustryByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysIndustryDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysIndustryDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysIndustryDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysIndustryDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysIndustryDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysIndustryDTO>>();
		List<SysIndustryDTO> list = service.searchSysIndustry(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysIndustryDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysIndustryDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysIndustryDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	
	/**
	 * 查询行业数据
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchIndustries/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<Map>>> searchIndustries(@RequestBody QueryReqBean params) throws Exception{
		ResponseMsg<QueryRespBean<Map>> responseMsg = new ResponseMsg<QueryRespBean<Map>>();
		List<Map> list = service.searchIndustries(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<Map>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<Map>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<Map>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping(value = "/getPositionsByIndustry/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<Map>>> getPositionsByIndustry(@RequestBody QueryReqBean params) throws Exception{
		ResponseMsg<QueryRespBean<Map>> responseMsg = new ResponseMsg<QueryRespBean<Map>>();
		List<Map> list = service.getPositionsByIndustry(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<Map>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<Map>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<Map>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	
	
	
}
