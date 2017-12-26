package com.fintech.modules.platform.testtable1.rest;

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

import com.fintech.modules.platform.testtable1.dto.TestTable1DTO;
import com.fintech.modules.platform.testtable1.service.TestTable1Service;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restservice.web.base.BaseRest;
/**
 * @classname: TestTable1Rest
 * @description:定义 测试表 rest服务
 * @author
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/testTable1")
public class TestTable1Rest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(TestTable1Rest.class);

	@Autowired
    @Qualifier("com.fintech.modules.platform.testtable1.service.TestTable1Service")
    private TestTable1Service service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<TestTable1DTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<TestTable1DTO> responseMsg = new ResponseMsg<TestTable1DTO>();
		TestTable1DTO entity = service.queryTestTable1ByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<TestTable1DTO>> responseEntity = new ResponseEntity<ResponseMsg<TestTable1DTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody TestTable1DTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertTestTable1(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody TestTable1DTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateTestTable1(obj);
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
		service.deleteTestTable1ByPrimaryKey(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<TestTable1DTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<TestTable1DTO>> responseMsg = new ResponseMsg<QueryRespBean<TestTable1DTO>>();
	
		List<TestTable1DTO> list = service.searchTestTable1ByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<TestTable1DTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<TestTable1DTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<TestTable1DTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<TestTable1DTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<TestTable1DTO>> responseMsg = new ResponseMsg<QueryRespBean<TestTable1DTO>>();
		List<TestTable1DTO> list = service.searchTestTable1(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<TestTable1DTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<TestTable1DTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<TestTable1DTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
