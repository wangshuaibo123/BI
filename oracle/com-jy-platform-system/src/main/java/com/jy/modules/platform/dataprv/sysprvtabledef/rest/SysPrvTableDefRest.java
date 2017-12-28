package com.jy.modules.platform.dataprv.sysprvtabledef.rest;

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

import com.jy.modules.platform.dataprv.sysprvtabledef.dto.SysPrvTableDefDTO;
import com.jy.modules.platform.dataprv.sysprvtabledef.service.SysPrvTableDefService;
import com.jy.platform.core.message.QueryReqBean;
import com.jy.platform.core.message.QueryRespBean;
import com.jy.platform.core.message.ResponseMsg;
import com.jy.platform.restservice.web.base.BaseRest;
/**
 * @classname: SysPrvTableDefRest
 * @description:定义 数据权限表定义 rest服务
 * @author wangxz
 * @date:2014年10月11日下午2:39:22
 */
@Controller
@RequestMapping(value = "/api/platform/dataprv/sysPrvTableDef")
public class SysPrvTableDefRest extends BaseRest{

	private static Logger logger = LoggerFactory.getLogger(SysPrvTableDefRest.class);

	@Autowired
    @Qualifier("com.jy.modules.platform.dataprv.sysprvtabledef.service.SysPrvTableDefService")
    private SysPrvTableDefService service;

	@Autowired
	private Validator validator;

	/**
	 * 取得单个业务对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/v1/{ID}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public ResponseEntity<ResponseMsg<SysPrvTableDefDTO>> get(@PathVariable("ID") String ID) throws Exception{
		ResponseMsg<SysPrvTableDefDTO> responseMsg = new ResponseMsg<SysPrvTableDefDTO>();
		SysPrvTableDefDTO entity = service.querySysPrvTableDefByPrimaryKey(ID);
		responseMsg.setResponseBody(entity);
		ResponseEntity<ResponseMsg<SysPrvTableDefDTO>> responseEntity = new ResponseEntity<ResponseMsg<SysPrvTableDefDTO>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	

	/**
	 * 插入一个业务对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/create/v1",method=RequestMethod.POST)
	public ResponseEntity<ResponseMsg<Void>> create(@RequestBody SysPrvTableDefDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.insertSysPrvTableDef(obj);
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
	public ResponseEntity<ResponseMsg<Void>> update(@RequestBody SysPrvTableDefDTO obj) throws Exception{
		ResponseMsg<Void> responseMsg = new ResponseMsg<Void>();
		BeanValidators.validateWithException(validator, obj);
		service.updateSysPrvTableDef(obj);
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
		service.deleteSysPrvTableDefByID(null,ID);
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
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>> searchByPage(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPrvTableDefDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>();
	
		List<SysPrvTableDefDTO> list = service.searchSysPrvTableDefByPaging(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPrvTableDefDTO>(params.getPageParameter(),list));
		ResponseEntity<ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search/v1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>> search(@RequestBody QueryReqBean params) throws Exception{
		
		ResponseMsg<QueryRespBean<SysPrvTableDefDTO>> responseMsg = new ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>();
		List<SysPrvTableDefDTO> list = service.searchSysPrvTableDef(params.getSearchParams());
		responseMsg.setResponseBody(new QueryRespBean<SysPrvTableDefDTO>(params.getPageParameter(),list));
		
		ResponseEntity<ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>> responseEntity=new ResponseEntity<ResponseMsg<QueryRespBean<SysPrvTableDefDTO>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}
	/**
	 * 按条件查询不分页
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/tables/v1", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public ResponseEntity<ResponseMsg<List<String>>> tables() throws Exception{
		
		ResponseMsg<List<String>> responseMsg = new ResponseMsg<List<String>>();
		List<String> list = service.queryTableList();
		responseMsg.setResponseBody(list);
		
		ResponseEntity<ResponseMsg<List<String>>> responseEntity=new ResponseEntity<ResponseMsg<List<String>>>(responseMsg, HttpStatus.OK);
		return responseEntity;
	}


}
