package com.fintech.modules.platform.sysflumeconfigzk.rest;

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

import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;

import com.fintech.modules.platform.sysflumeconfigzk.dto.SysFlumeConfigZkDTO;
import com.fintech.modules.platform.sysflumeconfigzk.service.SysFlumeConfigZkService;
/**
 * @classname: SysFlumeConfigZkRest
 * @description:定义 Flume配置表 rest服务
 * @author Trust_FreeDom
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/sysFlumeConfigZk")
public class SysFlumeConfigZkRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysFlumeConfigZkRest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.sysflumeconfigzk.service.SysFlumeConfigZkService")
    private SysFlumeConfigZkService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysFlumeConfigZkDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysFlumeConfigZkDTO> responseMsg = new ResponseMsg<SysFlumeConfigZkDTO>();
		SysFlumeConfigZkDTO entity = service.querySysFlumeConfigZkByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysFlumeConfigZkDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysFlumeConfigZkDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysFlumeConfigZkDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysFlumeConfigZk(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysFlumeConfigZkDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysFlumeConfigZk(obj);
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
		service.deleteSysFlumeConfigZkByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>();
	
		List<SysFlumeConfigZkDTO> list = service.searchSysFlumeConfigZkByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysFlumeConfigZkDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>();
		List<SysFlumeConfigZkDTO> list = service.searchSysFlumeConfigZk(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysFlumeConfigZkDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysFlumeConfigZkDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
