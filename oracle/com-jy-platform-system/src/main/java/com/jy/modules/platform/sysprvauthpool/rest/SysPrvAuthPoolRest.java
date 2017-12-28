package com.jy.modules.platform.sysprvauthpool.rest;

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

import com.jy.modules.platform.sysprvauthpool.dto.SysPrvAuthPoolDTO;
import com.jy.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysPrvAuthPoolRest
 * @description:定义 SYS_PRV_AUTH_POOL rest服务
 * @author lei
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysPrvAuthPool")
public class SysPrvAuthPoolRest extends BaseRest {

	private static Logger logger = LoggerFactory.getLogger(SysPrvAuthPoolRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.sysprvauthpool.service.SysPrvAuthPoolService")
    private SysPrvAuthPoolService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysPrvAuthPoolDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysPrvAuthPoolDTO> responseMsg = new ResponseMsg<SysPrvAuthPoolDTO>();
		SysPrvAuthPoolDTO entity = service.querySysPrvAuthPoolByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysPrvAuthPoolDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysPrvAuthPoolDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysPrvAuthPoolDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysPrvAuthPool(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysPrvAuthPoolDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysPrvAuthPool(obj);
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
		service.deleteSysPrvAuthPoolByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>();
	
		List<SysPrvAuthPoolDTO> list = service.searchSysPrvAuthPoolByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPrvAuthPoolDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>();
		List<SysPrvAuthPoolDTO> list = service.searchSysPrvAuthPool(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPrvAuthPoolDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPrvAuthPoolDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
