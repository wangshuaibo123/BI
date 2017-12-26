package com.fintech.modules.platform.sysdict.rest;

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

import com.fintech.modules.platform.sysdict.dto.SysDictDTO;
import com.fintech.modules.platform.sysdict.service.SysDictService;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysDictRest
 * @description:定义 数据字典 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysdict/SysDictRest")
public class SysDictRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysDictRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.sysdict.service.SysDictService")
    private SysDictService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysDictDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysDictDTO> responseMsg = new ResponseMsg<SysDictDTO>();
		SysDictDTO entity = service.querySysDictByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysDictDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysDictDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * test 测试方法
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v2/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysDictDTO>> getv2(@PathVariable("ID") Long ID) throws Exception{
		ResponseMsg<SysDictDTO> responseMsg = new ResponseMsg<SysDictDTO>();
		SysDictDTO entity = service.querySysDictByPrimaryKey(ID.toString());
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysDictDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysDictDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDictCodeIsOk/v1/{code}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<String>> queryDictCodeIsOk(@PathVariable("code") String code) throws Exception{
		ResponseMsg<String> responseMsg = new ResponseMsg<String>();
		String entity = service.queryDictCodeIsOk(code);
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
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysDictDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysDict(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysDictDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysDict(obj);
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
		service.deleteSysDictByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysDictDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysDictDTO>>();
	
		List<SysDictDTO> list = service.searchSysDictByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysDictDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	/**
	 * 按条件查询并分页(不记录缓存)
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchByPageNoCache/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>> searchByPageNoCache(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysDictDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysDictDTO>>();
	
		List<SysDictDTO> list = service.sysDictByPagingNoCache(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysDictDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
	
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysDictDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysDictDTO>>();
		List<SysDictDTO> list = service.searchSysDict(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysDictDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysDictDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
