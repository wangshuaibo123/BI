package com.jy.modules.platform.sysdict.rest;

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

import com.jy.modules.platform.sysdict.dto.SysDictDetailDTO;
import com.jy.modules.platform.sysdict.service.SysDictDetailService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;

/**
 * @classname: SysDictDetailRest
 * @description:定义 数据字典明细表 rest服务
 * @author chen_gang
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysdict/SysDictDetailRest")
public class SysDictDetailRest extends BaseRest{

	private static Logger logger = LoggerFactory
			.getLogger(SysDictDetailRest.class);

	@Autowired
	@Qualifier("com.jy.modules.platform.sysdict.service.SysDictDetailService")
	private SysDictDetailService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysDictDetailDTO>> get(
			@PathVariable("ID") String ID) throws Exception {
		ResponseMsg<SysDictDetailDTO> responseMsg = new ResponseMsg<SysDictDetailDTO>();
		SysDictDetailDTO entity = service.querySysDictDetailByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysDictDetailDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysDictDetailDTO>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 插入一个业务对象
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create/v1", method = RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(
			@RequestBody SysDictDetailDTO obj) throws Exception {
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysDictDetail(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity = new ResponseEntity<ResponseMsg<Void>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 修改一个业务对象
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update/v1", method = RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> update(
			@RequestBody SysDictDetailDTO obj) throws Exception {
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysDictDetail(obj);
		ResponseEntity<ResponseMsg<Void>> responseEntity = new ResponseEntity<ResponseMsg<Void>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 删除一个业务对象
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<Void>> delete(
			@PathVariable("ID") String ID) throws Exception {
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		service.deleteSysDictDetailByPrimaryKey(null, ID);
		ResponseEntity<ResponseMsg<Void>> responseEntity = new ResponseEntity<ResponseMsg<Void>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 按条件查询并分页
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/searchByPage/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysDictDetailDTO>>> searchByPage(
			@RequestBody QueryReqBean params) throws Exception {

		ResponseMsg<QueryRespBean<SysDictDetailDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysDictDetailDTO>>();

		List<SysDictDetailDTO> list = service
				.searchSysDictDetailByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysDictDetailDTO>(params
				.getPageParameter(), list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysDictDetailDTO>>> responseEntity = new ResponseEntity<ResponseMsg<QueryRespBean<SysDictDetailDTO>>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 按条件查询不分页
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysDictDetailDTO>>> search(
			@RequestBody QueryReqBean params) throws Exception {

		ResponseMsg<QueryRespBean<SysDictDetailDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysDictDetailDTO>>();
		List<SysDictDetailDTO> list = service.searchSysDictDetail(params
				.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysDictDetailDTO>(params
				.getPageParameter(), list));

		ResponseEntity<ResponseMsg<QueryRespBean<SysDictDetailDTO>>> responseEntity = new ResponseEntity<ResponseMsg<QueryRespBean<SysDictDetailDTO>>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 查找最大的排序值
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/findMaxOrderBy/v1/{dictId}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<String>> findMaxOrderBy(
			@PathVariable("dictId") String dictId) throws Exception {
		ResponseMsg<String> responseMsg = new ResponseMsg<String>();
		String maxOrderBy = service.findSysDictMaxOrderBy(dictId);
		responseMsg.setResponseBody(maxOrderBy);
		ResponseEntity<ResponseMsg<String>> responseEntity = new ResponseEntity<ResponseMsg<String>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}

	/**
	 * 验证code值是否存在
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/queryDetailCodeIsOk/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<String>> queryDetailCodeIsOk(
			@RequestBody QueryReqBean params) throws Exception {
		ResponseMsg<String> responseMsg = new ResponseMsg<String>();
		String codeNum = service.queryDetailCodeIsOk(params.getSearchParams());
		responseMsg.setResponseBody(codeNum);
		ResponseEntity<ResponseMsg<String>> responseEntity = new ResponseEntity<ResponseMsg<String>>(
				responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	
 /**
  * 插入一个集合业务对象
  * @param user
  * @return
  */
 @ResponseBody
 @RequestMapping(value="/createList/v1",method=RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
 public ResponseEntity<ResponseMsg<Void>> createList(@RequestBody SysDictDetailDTO[] objs) throws Exception{
  ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
  BeanValidators.validateWithException(validator, objs);
  
// 接收到数据对象，并进行循环取值
  for (SysDictDetailDTO sysDictDTO : objs) {
//      插入数据库
      logger.info(sysDictDTO.getId().toString());
      logger.info(sysDictDTO.getDictDetailName());
}
  
  
  ResponseEntity<ResponseMsg<Void>> responseEntity=new ResponseEntity<ResponseMsg<Void>>(responseMsg, HttpStatus.OK);
  return responseEntity;
 }


}
